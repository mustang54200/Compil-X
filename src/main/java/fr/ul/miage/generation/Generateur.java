package fr.ul.miage.generation;

import fr.ul.miage.arbre.*;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

import java.util.List;

public class Generateur {
    private Tds tableDesSymboles;

    public Generateur(Tds tds) {
        this.tableDesSymboles = tds;
    }

    public String genererProgramme(Noeud pgr) {
        StringBuffer code = new StringBuffer();

        code.append(".include beta.uasm\n")
            .append(".include intio.uasm\n");

        code.append("\tCMOVE(pile, SP\n")
            .append("\tBR(debut)\n")
            .append("debut: ");

        code.append(genererData());

        code.append("\TCALL(main)\n")
            .append("\tHALT\n");

        for (Noeud func: pgr.getFils()) {
            if (func instanceof Fonction) {
                code.append(genererFonction((Fonction) func));
            }
        }
        code.append("pile:\n");

        return code.toString();
    }

    public String genererInstruction(Noeud n) {
        StringBuffer code = new StringBuffer();

        switch (n.getCat()) {
            case AFF: code.append(genererAffectation((Affectation) n.getFils().get(0)));
            case SI: code.append(genererSi((Si) n.getFils().get(0)));
            case TQ: code.append(genererTantQue((TantQue) n.getFils().get(0)));
            case ECR: code.append(genererEcriture((Ecrire) n.getFils().get(0)));
            case APPEL: code.append(genererAppel((Fonction) n.getFils()));
            case RET: code.append(genererRetour(Retour) n.getFils().get(0));
        }

        return code.toString();
    }

    public String genererFonction(Noeud fonct) {
        StringBuffer code = new StringBuffer();
        code.append(fonct.toString());

        code.append("\tPUSH(LP)\n");
        code.append("\tPUSH(BP)\n");
        code.append("\tMOVE(SP,BP)\n");
        code.append("\tALLOCATE(").append(this.tableDesSymboles.getSymboleFromName(fonct.toString()).getNbVar()).append("\n"); // MARCHE PAS CAR IL CONNAIT PAS LE TDS

        for(Noeud f: fonct.getFils()) {
            code.append(genererInstruction((Fonction) f)); // MARCHE PAS CAR IL CONNAIT PAS GENERER INSTRUCTION
        }

        code.append("\tDEALLOCATE(").append(this.tableDesSymboles.getSymboleFromName(fonct.toString()).getNbVar()).append("\n") // MARCHE PAS CAR IL CONNAIT PAS LE TDS (PARTIE 2)
            .append("\tPOP(BP)\n")
            .append("\tPOP(LP)\n")
            .append("\tRTN()\n");

        return code.toString();
    }

    public String genererRetour(Retour arbre) {
        StringBuffer code = new StringBuffer();
        code.append(genererExpression(arbre.getLeFils()));

        Symbole symboleRetourArbre = this.tableDesSymboles.getSymboleFromName(arbre.toString());
        int offset = 2 + symboleRetourArbre.getNbParam();

        code.append("\tPOP(R0)\n")
            .append("\tPUTFRAME(R0").append(offset * 4).append("\n")
            .append("\t").append("ret_").append(arbre.toString()).append("\n");

        return code.toString();
    }

    public String genererSi(Noeud arbre) {
        StringBuffer code = new StringBuffer();
        List<Noeud> enfantsArbre = arbre.getFils();

        code.append(arbre.toString())
            .append(genererCondition(enfantsArbre.get(0)));

        code.append("\tPOP(R0)\n")
            .append("\tBF(RO,").append(arbre.toString()).append("\n"); // "sinon_a.valeur"

        code.append(genererBloc(enfantsArbre.get(1)));

        code.append("\tBR(fsi,").append(arbre.toString()).append("\n")  //
            .append(arbre.toString()).append(":\n") // "sinon_a.valeur:"
            .append(genererBloc(enfantsArbre.get(2)));

        code.append(arbre.toString()).append(":\n"); // "fsi_a.valeur:"

        return code.toString();
    }

    public String genererTantQue(Noeud arbre) {
        StringBuffer code = new StringBuffer();

        code.append(arbre.toString()).append(":\n");
        code.append(genererCondition(arbre.getFils().get(0)));

        code.append("\tBF(R0").append(arbre.toString()).append(")\n"); // "ftq_a.valeur"

        code.append(genererBloc(arbre.getFils().get(1)));

    }

    public String genererBloc(Noeud arbre) {
        StringBuffer code = new StringBuffer();

        for(Noeud f: arbre.getFils()) {
            code.append(genererInstruction((Fonction) f));
        }

        return code.toString();
    }

    public String genererAffectation(Affectation aff) {
        StringBuffer code = new StringBuffer();

        code.append(genererExpression(aff.getFilsDroit()))
            .append("\tPOP(R0)\n");

        Idf var = (Idf) aff.getFilsGauche();

        code.append("\tST(R0, ").append(var.getValeur()).append(")\n");
        return code.toString();
    }

    public String genererCondition(Noeud arbre) {
        StringBuffer code = new StringBuffer();

        switch(arbre.getCat()) {
            case SUP:
                code.append(genererExpression(arbre.getFils().get(0)))
                    .append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n")
                    .append("\tPOP(R2)\n")
                    .append("\tCMPLT(R2,R1,R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case SUPE:
                code.append(genererExpression(arbre.getFils().get(0)))
                    .append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n")
                    .append("\tPOP(R2)\n")
                    .append("\tCMPLE(R2,R1,R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case INF:
                code.append(genererExpression(arbre.getFils().get(0)))
                    .append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n")
                    .append("\tPOP(R2)\n")
                    .append("\tCMPL(R1,R2,R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case INFE:
                code.append(genererExpression(arbre.getFils().get(0)))
                    .append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n")
                    .append("\tPOP(R2)\n")
                    .append("\tCMPLT(R1,R2,R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case EG:
                code.append(genererExpression(arbre.getFils().get(0)))
                    .append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n")
                    .append("\tPOP(R2)\n")
                    .append("\tCMPEQ(R1,R2,R3)\n")
                    .append("\tPUSH(R3)\n");

        }
        return code.toString();
    }

    public String genererExpression(Noeud expr) {
        StringBuffer code = new StringBuffer();

        switch (expr.getCat()) {
            case CONST:
                Const c = (Const) expr;

                code.append("\tCMOVE(R0, ").append(c.getValeur()).append(")\n")
                    .append("\tPUSH(R0)\n");
                break;

            case IDF:
                Idf i = (Idf) expr;

                code.append("\tCMOVE(R0, ").append(i.getValeur()).append(")\n")
                    .append("\tPUSH(R0)\n");
                break;

            case PLUS:
                Plus p = (Plus) expr;
                Noeud gaucheP = p.getFilsGauche();
                Noeud droitP = p.getFilsDroit();

                code.append(genererExpression(gaucheP))
                    .append(genererExpression(droitP));

                code.append("\tPOP(R2)\n")
                    .append("\tPOP(R1)\n")
                    .append("\tADD(R1, R2, R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case MOINS:
                Moins m = (Moins) expr;
                Noeud gaucheM = m.getFilsGauche();
                Noeud droitM = m.getFilsDroit();

                code.append(genererExpression(gaucheM))
                    .append(genererExpression(droitM));

                code.append("\tPOP(R2)\n")
                    .append("\tPOP(R1)\n")
                    .append("\tSUB(R1, R2, R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case MUL:
                Multiplication mul = (Multiplication) expr;
                Noeud gaucheMul = mul.getFilsGauche();
                Noeud droitMul = mul.getFilsDroit();

                code.append(genererExpression(gaucheMul))
                    .append(genererExpression(droitMul));

                code.append("\tPOP(R2)\n")
                    .append("\tPOP(R1)\n")
                    .append("\tMUL(R1, R2, R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            case DIV:
                Division div = (Division) expr;
                Noeud gaucheD = div.getFilsGauche();
                Noeud droitD = div.getFilsDroit();

                code.append(genererExpression(gaucheD))
                    .append(genererExpression(droitD));

                code.append("\tPOP(R2)\n")
                    .append("\tPOP(R1)\n")
                    .append("\tDIV(R1, R2, R3)\n")
                    .append("\tPUSH(R3)\n");
                break;

            default:
                break;
        }
        return code.toString();
    }

    public String genererData(){
        StringBuffer code = new StringBuffer();

        for (Symbole e : this.tableDesSymboles.getTable()){
            if(e.getType().equals(Symbole.Type.entier)
                    && e.getCat().equals(Symbole.Categorie.global)){

                code.append(e.getNom()).append(":")
                    .append("LONG(").append(e.getVal()).append(")\n");
            }
        }
        return code.toString();
    }

    public String genererEcriture(Ecrire arbre){
        StringBuffer code = new StringBuffer();

        code.append(genererExpression(arbre.getLeFils()))
            .append("\tPOP(R0)\nWRINT()");

        return code.toString();
    }

    public String genererAppel(Fonction a){
        StringBuffer code = new StringBuffer();

        if(a.getValeur() != null){
            code.append("ALLOCATE(1)");
        }

        for(Noeud f: a.getFils()){
            code.append(genererExpression(f));
        }

        code.append("CALL(").append(a.getValeur()).append(")")
            .append("DEALLOCATE("+ this.tableDesSymboles.getSymboleFromName(a.toString()).getNbParam()+")");
        return code.toString();
    }
}
