package fr.ul.miage.generation;

import fr.ul.miage.arbre.*;

public class Exemple1 {
    public static void main(String[] args) {
        //on crée les noeuds
        Affectation aff = new Affectation();
        Idf x = new Idf("x");
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Idf a = new Idf("a");
        Const c2 = new Const(2);
        Division div = new Division();
        Moins moins = new Moins();
        Idf b = new Idf("b");
        Const c5 = new Const(5);
        Const c3 = new Const(3);
        //on relie les noeuds
        aff.setFilsGauche(x);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);
        mul.setFilsGauche(a);
        mul.setFilsDroit(c2);
        div.setFilsGauche(moins);
        div.setFilsDroit(c3);
        moins.setFilsGauche(a);
        moins.setFilsDroit(c5);
        //afficher
        System.out.println("Arbre source :");
        TxtAfficheur.afficher(aff);
        System.out.println("\nCode généré :");
        Generateur gen = new Generateur();
        String code = gen.genererAffectation(aff);
        System.out.println(code);
    }
}
