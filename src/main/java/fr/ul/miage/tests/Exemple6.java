package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.generation.Generateur;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple6 {

    public static Noeud initialiserCompilateur(Tds tds) {

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        tds.insertTable(fonctionMain);
        Symbole fonctionF = new Symbole("f", Symbole.Type.entier, Symbole.Categorie.fonction, 2, 1);
        tds.insertTable(fonctionF);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.global, 100);
        tds.insertTable(varA);
        Symbole varC = new Symbole("c", Symbole.Type.entier, Symbole.Categorie.global, 170);
        tds.insertTable(varC);
        Symbole varA_f = new Symbole("a_f", Symbole.Type.entier, Symbole.Categorie.param, 0, "f");
        tds.insertTable(varA_f);
        Symbole varB_f = new Symbole("b_f", Symbole.Type.entier, Symbole.Categorie.param, 1, "f");
        tds.insertTable(varB_f);
        Symbole varRes_f = new Symbole("Res_f", Symbole.Type.entier, Symbole.Categorie.local, 0, "f");
        tds.insertTable(varRes_f);

        // arbre
        Fonction f = new Fonction(fonctionF);
        prog.ajouterUnFils(f);

        Affectation aff = new Affectation();
        f.ajouterUnFils(aff);
        Idf res_f = new Idf(varRes_f);
        aff.setFilsGauche(res_f);

        Plus plus = new Plus();
        aff.setFilsDroit(plus);

        Multiplication mul = new Multiplication();
        plus.setFilsGauche(mul);
        mul.setFilsGauche(new Idf(varA_f));
        mul.setFilsDroit(new Const(2));

        Division div = new Division();
        plus.setFilsDroit(div);
        div.setFilsDroit(new Const(3));

        Moins moins = new Moins();
        div.setFilsGauche(moins);
        moins.setFilsGauche(new Idf(varB_f));
        moins.setFilsDroit(new Const(5));

        Retour ret = new Retour(res_f); // object retour (TDS)
        f.ajouterUnFils(ret);
        ret.setLeFils(res_f);

        Fonction main = new Fonction(fonctionMain);
        prog.ajouterUnFils(main);
        Ecrire ecrire = new Ecrire();
        main.ajouterUnFils(ecrire);
        Appel appel = new Appel(fonctionF);
        appel.ajouterUnFils(new Idf(varA));
        appel.ajouterUnFils(new Idf(varC));


        return prog;
    }




}
