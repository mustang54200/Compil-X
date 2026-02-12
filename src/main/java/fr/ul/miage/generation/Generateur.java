package fr.ul.miage.generation;

import fr.ul.miage.arbre.*;
import fr.ul.miage.tds.Tds;

import java.util.List;

/**
 * Générateur de code pour un arbre d'affectation
 */
public class Generateur {
    /**
     * Générer le code pour une affectation
     * @param aff : noeud d'affectation
     * @return code généré
     */
    public String genererProgramme(Noeud pgr, Tds tableDesSymboles) {
        StringBuffer code = new StringBuffer();

        code.append(".include beta.uasm\n");
        code.append(".include intio.uasm\n");

        // genererData(tds) -> String (mets les variables du tds dans le code assembly)

        code.append("CMOVE(pile, SP\n");
        code.append("BR(debut)\n");
        code.append("debut: ");
        code.append("CALL(main)\nHALT\n");

        for (Noeud func: pgr.getFils()) {
            if (func instanceof Fonction) {
                code.append(genererFonction((Fonction) func, tableDesSymboles));
            }
        }
        code.append("pile:\n");

        return code.toString();
    }

    public String genererInstruction(Noeud n, Tds tableDesSymboles) {
        StringBuffer code = new StringBuffer();

        switch (n.getCat()) {
            case AFF: code.append(genererAffectation((Affectation) n.getFils().get(0)));
            case SI: code.append(genererSi((Si) n.getFils().get(0), tableDesSymboles));
            case TQ: code.append(genererTantQue(n.getFils().get(0), tableDesSymboles));
            case ECR: code.append(genererEcriture(n.getFils().get(0)));
            case APPEL: code.append(genererAppel(n.getFils().get(0)));
        }

        return code.toString();
    }

    public String genererFonction(Noeud fonct, Tds tableDesSymboles) {
        StringBuffer code = new StringButter();
        code.append(fonct.toString());

        code.append("\tPUSH(LP)\n");
        code.append("\tPUSH(BP)\n");
        code.append("\tMOVE(SP,BP)\n");
        code.append("\tALLOCATE(" + tableDesSymboles.getNbVar() + "\n"); // MARCHE PAS CAR IL CONNAIT PAS LE TDS

        for(Noeud f: fonct.getFils()) {
            code.append(genererInstruction(((Fonction) f, tableDesSymboles)); // MARCHE PAS CAR IL CONNAIT PAS GENERER INSTRUCTION
        }

        code.append("\tDEALLOCATE(" + tableDesSymboles.getNbVar() + "\n"); // MARCHE PAS CAR IL CONNAIT PAS LE TDS (PARTIE 2)
        code.append("\tPOP(BP)\n");
        code.append("\tPOP(LP)\n");
        code.append("\tRTN()\n");

        return code.toString();
    }

    public String genererRetour(Noeud arbre, Tds tableDesSymboles) {
        StringBuffer code = new StringBuffer();

        code.append(genererExpression(arbre.getFils().get(0)));

        int offset = 2 + tableDesSymboles. // A FINIR
    }

    public String genererSi(Noeud arbre, Tds tableDesSymboles) {
        StringBuffer code = new StringBuffer();
        List<Noeud> enfantsArbre = arbre.getFils();

        code.append(arbre.toString());
        code.append(genererCondition(enfantsArbre.get(0)));

        code.append("\tPOP(R0)\n");
        code.append("\tBF(RO,").append(arbre.toString()).append("\n"); // "sinon_a.valeur"

        code.append(genererBloc(enfantsArbre.get(1), tableDesSymboles) + "\n");

        code.append("\tBR(fsi," + arbre.toString() + "\n");  //
        code.append(arbre.toString()).append(":\n"); // "sinon_a.valeur:"
        code.append(genererBloc(enfantsArbre.get(2), tableDesSymboles));

        code.append(arbre.toString()).append(":\n"); // "fsi_a.valeur:"
    }

    public String genererTantQue(Noeud arbre, Tds tableDesSymboles) {
        StringBuffer code = new StringBuffer();

        code.append(arbre.toString()).append(":\n");
        code.append(genererCondition(arbre.getFils().get(0)));

        code.append("\tBF(R0").append(arbre.toString()).append(")\n"); // "ftq_a.valeur"

        code.append(genererBloc(arbre.getFils().get(1), tableDesSymboles));

    }

    public String genererBloc(Noeud arbre, Tds tableDesSymboles) {
        StringBuffer code = new StringBuffer();

        for(Noeud f: arbre.getFils()) {
            code.append(genererInstruction((Fonction) f, tableDesSymboles));
        }

        return code.toString();
    }

    public String genererAffectation(Affectation aff) {
        StringBuffer code = new StringBuffer();
        code.append(genererExpression(aff.getFilsDroit()));
        code.append("\tPOP(R0)\n");
        Idf var = (Idf) aff.getFilsGauche();
        code.append("\tST(R0, " + var.getValeur() + ")\n");
        return code.toString();
    }
    /**
     * Générer le code pour une expression
     * @param expr : noeud d'expression
     * @return code généré
     */

    public String genererCondition(Noeud arbre) {
        StringBuffer code = new StringBuffer();

        switch(arbre.getCat()) {
            case SUP:
                code.append(genererExpression(arbre.getFils().get(0)));
                code.append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n");
                code.append("\tPOP(R2)\n");
                code.append("\tCMPLT(R2,R1,R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case SUPE:
                code.append(genererExpression(arbre.getFils().get(0)));
                code.append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n");
                code.append("\tPOP(R2)\n");
                code.append("\tCMPLE(R2,R1,R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case INF:
                code.append(genererExpression(arbre.getFils().get(0)));
                code.append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n");
                code.append("\tPOP(R2)\n");
                code.append("\tCMPL(R1,R2,R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case INFE:
                code.append(genererExpression(arbre.getFils().get(0)));
                code.append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n");
                code.append("\tPOP(R2)\n");
                code.append("\tCMPLT(R1,R2,R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case EG:
                code.append(genererExpression(arbre.getFils().get(0)));
                code.append(genererExpression(arbre.getFils().get(1)));

                code.append("\tPOP(R1)\n");
                code.append("\tPOP(R2)\n");
                code.append("\tCMPEQ(R1,R2,R3)\n");
                code.append("\tPUSH(R3)\n");

        }

        return code.toString();
    }

    public String genererExpression(Noeud expr) {
        StringBuffer code = new StringBuffer();

        switch (expr.getCat()) {
            case CONST:
                Const c = (Const) expr;

                code.append("\tCMOVE(R0, " + c.getValeur() + ")\n");
                code.append("\tPUSH(R0)\n");

                break;

            case IDF:
                Idf i = (Idf) expr;

                code.append("\tCMOVE(R0, " + i.getValeur() + ")\n");
                code.append("\tPUSH(R0)\n");

                break;

            case PLUS:
                Plus p = (Plus) expr;
                Noeud gaucheP = p.getFilsGauche();
                Noeud droitP = p.getFilsDroit();

                code.append(genererExpression(gaucheP));
                code.append(genererExpression(droitP));

                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tADD(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case MOINS:
                Moins m = (Moins) expr;
                Noeud gaucheM = m.getFilsGauche();
                Noeud droitM = m.getFilsDroit();

                code.append(genererExpression(gaucheM));
                code.append(genererExpression(droitM));

                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tSUB(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case MUL:
                Multiplication mul = (Multiplication) expr;
                Noeud gaucheMul = mul.getFilsGauche();
                Noeud droitMul = mul.getFilsDroit();

                code.append(genererExpression(gaucheMul));
                code.append(genererExpression(droitMul));

                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tMUL(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            case DIV:
                Division div = (Division) expr;
                Noeud gaucheD = div.getFilsGauche();
                Noeud droitD = div.getFilsDroit();

                code.append(genererExpression(gaucheD));
                code.append(genererExpression(droitD));

                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tDIV(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;

            default:
                break;
        }
        return code.toString();
    }

    public String genererData(Tds[] tds){
        StringBuffer code=new StringBuffer();
        code.append("");
        for (Tds e : tds){
            if(e.getType()== Tds.Type.entier && e.getCat()== Tds.Categorie.global){
                code.append(e.getNom());
                code.append(":LONG()"+e.getVal());
            }
        }
        return code.toString();
    }
    public String genererEcriture(Noeud a){
        StringBuffer code=new StringBuffer();
        code.append(genererExpression(a.getFils(0)));
        code.append("\tPOP(R0)\nWRINT()");
        return code.toString();
    }
}
