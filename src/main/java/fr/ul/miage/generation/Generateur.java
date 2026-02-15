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
        StringBuilder code = new StringBuilder();

        code.append(".include beta.uasm\n")
            .append(".include intio.uasm\n");

        code.append("\ninit:\n")
            .append("\tCMOVE(pile, SP)\n")
            .append("\tBR(debut)\n");

        code.append(genererData());

        code.append("\ndebut:\n")
            .append("\tCALL(main)\n")
            .append("\tHALT()\n");

        for (Noeud func: pgr.getFils()) {
            if (func instanceof Fonction) {
                code.append(genererFonction(func));
            }
        }
        code.append("\npile:\n");

        return code.toString();
    }

    public String genererData(){
        StringBuilder code = new StringBuilder();
        code.append("\nvar:\n");

        for (Symbole e : this.tableDesSymboles.getTable()){
            if(e.getType().equals(Symbole.Type.entier)
                    && e.getCat().equals(Symbole.Categorie.global)) {

                code.append("\t").append(e.getNom()).append(":")
                        .append("LONG(").append(e.getVal()).append(")\n");
            }
        }

        return code.toString();
    }

    public String genererFonction(Noeud fo) {

        StringBuilder code = new StringBuilder();


        if (fo instanceof Fonction fonct) {

            if (fonct.getValeur() instanceof Symbole fonctionSymbole) {

                code.append("\n").append(fonctionSymbole.getNom()).append(":\n");

                code.append("\tPUSH(LP)\n");
                code.append("\tPUSH(BP)\n");
                code.append("\tMOVE(SP,BP)\n");

                code.append("\tALLOCATE(").append(fonctionSymbole.getNbLoc()).append(")\n");

                for(Noeud f: fonct.getFils()) {
                    code.append(genererInstruction(f));
                }

                code.append("ret_").append(fonctionSymbole.getNom()).append(":\n");
                code.append("\tDEALLOCATE(").append(fonctionSymbole.getNbLoc()).append(")\n")
                        .append("\tPOP(BP)\n")
                        .append("\tPOP(LP)\n")
                        .append("\tRTN()\n");


            }


        }
        return code.toString();
    }

    public String genererRetour(Retour arbre) {
        StringBuilder code = new StringBuilder();
        code.append(genererExpression(arbre.getLeFils()));

        if (arbre.getValeur() instanceof Symbole symboleRetourArbre) {
            int offset = 2 + symboleRetourArbre.getNbParam();

            code.append("\tPOP(R0)\n")
                    .append("\tPUTFRAME(R0, ").append(offset * 4).append(")\n")
                    .append("\t").append("BR(ret_").append(symboleRetourArbre.getNom()).append(")\n");

        }

        code.append("\n");
        return code.toString();
    }

    public String genererInstruction(Noeud n) {
        StringBuilder code = new StringBuilder();

        switch (n.getCat()) {

            case AFF:
                code.append(genererAffectation((Affectation) n));
                break;

            case SI:
                code.append(genererSi((Si) n));
                break;

            case TQ:
                code.append(genererTantQue((TantQue) n));
                break;

            case ECR:
                code.append(genererEcriture((Ecrire) n));
                break;

            case APPEL:
                code.append(genererAppel((Fonction) n));
                break;

            case RET:
                code.append(genererRetour((Retour) n));
                break;

            default:
                break;
        }

        return code.toString();
    }


    public String genererAffectation(Affectation aff) {
        StringBuilder code = new StringBuilder();

        code.append(genererExpression(aff.getFilsDroit()))
                .append("\tPOP(R0)\n");

        Idf var = (Idf) aff.getFilsGauche();

        if (var.getValeur() instanceof Symbole variable) {

            code.append("\tST(R0, ").append(variable.getNom()).append(")\n");

        }

        return code.toString();
    }

    public String genererExpression(Noeud expr) {
        StringBuilder code = new StringBuilder();

        switch (expr.getCat()) {
            case CONST:
                Const c = (Const) expr;

                code.append("\tCMOVE(R0, ").append(c.getValeur()).append(")\n")
                        .append("\tPUSH(R0)\n\n");
                break;

            case IDF:
                Idf i = (Idf) expr;

                if (i.getValeur() instanceof Symbole idf) {

                    code.append("\tLD(").append(idf.getNom()).append(", R0)\n")
                            .append("\tPUSH(R0)\n\n");

                }

                break;

            case LIRE:

                code.append("\tRDINT()\n\tPUSH(R0)\n");

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
                        .append("\tPUSH(R3)\n\n");
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
                        .append("\tPUSH(R3)\n\n");
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
                        .append("\tPUSH(R3)\n\n");
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
                        .append("\tPUSH(R3)\n\n");
                break;

            default:
                break;
        }
        return code.toString();
    }

    public String genererSi(Si si) {
        StringBuilder code = new StringBuilder();
        List<Noeud> enfantsArbre = si.getFils();
        String strSi = si.toString().toLowerCase().replace('/', '_');

        code.append("\n").append(strSi).append(":\n") // "si_valeur"
                .append(genererCondition(enfantsArbre.get(0))); // condition si

        code.append("\tPOP(R0)\n") // récupérer le résultat de la condition
                .append("\tBF(RO, sinon_").append(strSi).append(")\n"); // aller à "sinon_si_valeur" ou rester sur "alors_si/valeur"

        code.append("\nalors_").append(strSi).append(":\n") // "alors_si_valeur"
                .append(genererBloc(enfantsArbre.get(1))) // bloc alors
                .append("\tBR(fsi_").append(strSi).append(")\n");  // aller à "fsi_si_valeur"

        code.append("\nsinon_").append(strSi).append(":\n") // "sinon_si_valeur:"
                .append(genererBloc(enfantsArbre.get(2))); // bloc sinon

        code.append("fsi_").append(strSi).append(":\n"); // "fsi_si_valeur:"

        code.append("\n");
        return code.toString();
    }


    public String genererTantQue(TantQue tq) {
        StringBuilder code = new StringBuilder();
        String strTq = tq.toString().toLowerCase().replace('/', '_');

        code.append("\n").append(strTq).append(":\n") // "tq_valeur"
                .append(genererCondition(tq.getFils().get(0))); // condition

        code.append("\tBF(R0, ftq_").append(strTq).append(")\n") // aller à "ftq_tq_valeur" ou exécuter le bloc suivant
                .append(genererBloc(tq.getFils().get(1))); // bloc

        code.append("\nftq_").append(strTq).append(":\n"); // "ftq_tq_valeur"

        code.append("\n");
        return code.toString();
    }

    public String genererBloc(Noeud arbre) {
        StringBuilder code = new StringBuilder();

        for(Noeud f: arbre.getFils()) {
            code.append(genererInstruction(f));
        }

        return code.toString();
    }

    public String genererCondition(Noeud arbre) {
        StringBuilder code = new StringBuilder();

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


    public String genererEcriture(Ecrire ecrire){
        StringBuilder code;
        code = new StringBuilder();

        code.append(genererExpression(ecrire.getLeFils()))
                .append("\tPOP(R0)\n")
                .append("\tWRINT()\n");

        return code.toString();
    }


    public String genererAppel(Fonction a){
        StringBuilder code = new StringBuilder();

        if(a.getValeur() != null){
            code.append("ALLOCATE(1)");
        }

        for(Noeud f: a.getFils()){
            code.append(genererExpression(f));
        }

        code.append("CALL(").append(a.getValeur()).append(")\n")
                .append("DEALLOCATE(").append(this.tableDesSymboles.getSymboleFromName(a.toString()).getNbParam()).append(")");

        return code.toString();
    }
}
