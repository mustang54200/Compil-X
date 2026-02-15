package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.generation.Generateur;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

import java.util.ArrayList;

public class Exemple3 {

    public static Noeud initialiserCompilateur(Tds tds){

        Prog prog = new Prog();


        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        Symbole varX = new Symbole("x", Symbole.Type.entier, Symbole.Categorie.global);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.global, 100);
        Symbole varB = new Symbole("b", Symbole.Type.entier, Symbole.Categorie.global, 170);

        tds.insertTable(fonctionMain);
        tds.insertTable(varX);
        tds.insertTable(varA);
        tds.insertTable(varB);


        // arbre
        Fonction main = new Fonction(fonctionMain);
        Affectation aff = new Affectation();
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Division div = new Division();
        Moins moins = new Moins();

        prog.ajouterUnFils(main);
        main.ajouterUnFils(aff);
        aff.setFilsGauche(new Idf(varX));
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mul);
        mul.setFilsGauche(new Idf(varA));
        mul.setFilsDroit(new Const(2));
        plus.setFilsDroit(div);
        div.setFilsGauche(moins);
        moins.setFilsGauche(new Idf(varB));
        moins.setFilsDroit(new Const(5));
        div.setFilsDroit(new Const(3));


        return prog;
    }

}
