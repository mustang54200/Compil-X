package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple4 {

    public static Noeud initialiserCompilateur(Tds tds){

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        Symbole varRes = new Symbole("res", Symbole.Type.entier, Symbole.Categorie.global);

        tds.insertTable(fonctionMain);
        tds.insertTable(varRes);

        // noeuds

        Fonction main = new Fonction(fonctionMain);
        Affectation aff =  new Affectation();
        Plus plus = new Plus();
        Multiplication mult = new Multiplication();
        Lire lire = new Lire();
        Division div = new Division();
        Moins moins = new Moins();
        Lire lire2 = new Lire();
        Ecrire  ecrire = new Ecrire();
        Idf idfRes = new Idf(varRes);

        // arbre
        prog.ajouterUnFils(main);
        main.ajouterUnFils(aff);
        main.ajouterUnFils(ecrire);
        aff.setFilsGauche(idfRes);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mult);
        plus.setFilsDroit(div);
        mult.setFilsGauche(lire);
        mult.setFilsDroit(new Const(2));
        div.setFilsGauche(moins);
        div.setFilsDroit(new Const(3));
        moins.setFilsGauche(lire2);
        moins.setFilsDroit(new Const(5));
        ecrire.ajouterUnFils(idfRes);



        return prog;
    }

}
