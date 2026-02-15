package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple5 {

    public static Noeud initialiserCompilateur(Tds tds){

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.global, 100);
        Symbole varB = new Symbole("b", Symbole.Type.entier, Symbole.Categorie.global, 170);

        tds.insertTable(fonctionMain);
        tds.insertTable(varA);
        tds.insertTable(varB);

        // noeuds

        Fonction main = new Fonction(fonctionMain);
        Ecrire ecrire = new Ecrire();
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Division div = new Division();
        Moins moins = new Moins();

        // arbre
        prog.ajouterUnFils(main);
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(plus);
        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);
        mul.setFilsGauche(new Idf(varA));
        mul.setFilsDroit(new Const(2));
        div.setFilsGauche(moins);
        div.setFilsDroit(new Const(3));
        moins.setFilsGauche(new Idf(varB));
        moins.setFilsDroit(new Const(5));

        return prog;
    }

}
