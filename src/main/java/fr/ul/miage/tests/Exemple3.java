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
        tds.insertTable(fonctionMain);
        Symbole varX = new Symbole("x", Symbole.Type.entier, Symbole.Categorie.global);
        tds.insertTable(varX);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.global, 100);
        tds.insertTable(varA);
        Symbole varB = new Symbole("b", Symbole.Type.entier, Symbole.Categorie.global, 170);
        tds.insertTable(varB);

        // arbre
        Fonction main = new Fonction(fonctionMain);
        prog.ajouterUnFils(main);

        Affectation aff = new Affectation();
        main.ajouterUnFils(aff);

        Idf idfX = new Idf(varX);
        aff.setFilsGauche(idfX);

        Plus plus = new Plus();
        aff.setFilsDroit(plus);

        Multiplication mul = new Multiplication();
        plus.setFilsGauche(mul);
        Idf idfA = new Idf(varA);
        mul.setFilsGauche(idfA);
        Const const2 = new Const(2);
        mul.setFilsDroit(const2);

        Division div = new Division();
        plus.setFilsDroit(div);

        Moins moins = new Moins();
        div.setFilsGauche(moins);
        Idf idfB = new Idf(varB);
        moins.setFilsGauche(idfB);
        Const const5 = new Const(5);
        moins.setFilsDroit(const5);

        Const const3 = new Const(3);
        div.setFilsDroit(const3);


        return prog;
    }


}
