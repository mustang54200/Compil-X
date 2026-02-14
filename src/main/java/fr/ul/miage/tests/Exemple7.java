package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.generation.Generateur;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple7 {

    public static Noeud initialiserCompilateur(Tds tds) {

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.global, 1);
        Symbole varB = new Symbole("c", Symbole.Type.entier, Symbole.Categorie.global, 2);
        Symbole varX = new Symbole("c", Symbole.Type.entier, Symbole.Categorie.global);

        tds.insertTable(fonctionMain);
        tds.insertTable(varA);
        tds.insertTable(varB);
        tds.insertTable(varX);


        // arbre
        Fonction main = new Fonction(fonctionMain);
        Si si = new Si();
        Superieur sup = new Superieur();
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        Affectation aff1 = new Affectation();
        Affectation aff2 = new Affectation();
        Idf idfX = new Idf(varX);


        prog.ajouterUnFils(main);
        main.ajouterUnFils(si);

        si.setCondition(sup);
        sup.setFilsGauche(new Idf(varA));
        sup.setFilsDroit(new Idf(varB));

        si.setBlocAlors(bloc1);
        bloc1.ajouterUnFils(aff1);
        aff1.setFilsGauche(idfX);
        aff1.setFilsDroit(new Const(1000));

        si.setBlocSinon(bloc2);
        bloc2.ajouterUnFils(aff2);
        aff2.setFilsGauche(idfX);
        aff2.setFilsDroit(new Const(2000));


        return prog;
    }




}
