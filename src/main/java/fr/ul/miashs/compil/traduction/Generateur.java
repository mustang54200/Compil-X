/**
 * Exercice d'introduction à la génération de code Beta
 * @author Azim Roussanaly
 * Created at 25 févr. 2026
 */
package fr.ul.miashs.compil.traduction;
import fr.ul.miashs.compil.arbre.Affectation;
import fr.ul.miashs.compil.arbre.Const;
import fr.ul.miashs.compil.arbre.Idf;
import fr.ul.miashs.compil.arbre.Moins;
import fr.ul.miashs.compil.arbre.Multiplication;
import fr.ul.miashs.compil.arbre.Division;
import fr.ul.miashs.compil.arbre.Noeud;
import fr.ul.miashs.compil.arbre.Plus;
/**
 * Générateur de code pour un arbre d'affectation
 */
public class Generateur {
    /**
     * Générer le code pour une affectation
     * @param aff : noeud d'affectation
     * @return code généré
     */
    public String genererFonction(Noeud fonct, Tds tds) {
        StringBuffer code = new StringButter();
        code.append(fonct.toString());

        code.append("\tPUSH(LP)\n");
        code.append("\tPUSH(BP)\n");
        code.append("\tMOVE(SP,BP)\n");
        code.append("\tALLOCATE(" + tds.getNbVar() + "\n"); // MARCHE PAS CAR IL CONNAIT PAS LE TDS

        for(Noeud f: fonct.getFils()) {
            code.append(genererInstruction(f)); // MARCHE PAS CAR IL CONNAIT PAS GENERER INSTRUCTION
        }

        code.append("\tDEALLOCATE(" + tds.getNbVar() + "\n"); // MARCHE PAS CAR IL CONNAIT PAS LE TDS (PARTIE 2)
        code.append("\tPOP(BP)\n");
        code.append("\tPOP(LP)\n");
        code.append("\tRTN()\n");

        return code.toString();
    }

    genererSi(Noeud

              )}


    if (cc) { ee }
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
}
