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
        Symbole fonctionF = new Symbole("f", Symbole.Type.entier, Symbole.Categorie.fonction, 2, 1);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.global, 100);
        Symbole varC = new Symbole("c", Symbole.Type.entier, Symbole.Categorie.global, 170);
        Symbole varA_f = new Symbole("a_f", Symbole.Type.entier, Symbole.Categorie.param, 0, fonctionF);
        Symbole varB_f = new Symbole("b_f", Symbole.Type.entier, Symbole.Categorie.param, 1, fonctionF);
        Symbole varRes_f = new Symbole("Res_f", Symbole.Type.entier, Symbole.Categorie.local, 0, fonctionF);

        tds.insertTable(fonctionMain);
        tds.insertTable(fonctionF);
        tds.insertTable(varA);
        tds.insertTable(varC);
        tds.insertTable(varA_f);
        tds.insertTable(varB_f);
        tds.insertTable(varRes_f);


        // arbre
        Fonction f = new Fonction(fonctionF);
        Affectation aff = new Affectation();
        Idf res_f = new Idf(varRes_f);
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Division div = new Division();
        Moins moins = new Moins();
        Retour ret = new Retour(fonctionF);
        Fonction main = new Fonction(fonctionMain);
        Ecrire ecrire = new Ecrire();
        Appel appel = new Appel(fonctionF);

        prog.ajouterUnFils(f);
        f.ajouterUnFils(aff);
        aff.setFilsGauche(res_f);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mul);
        mul.setFilsGauche(new Idf(varA_f));
        mul.setFilsDroit(new Const(2));
        plus.setFilsDroit(div);
        div.setFilsDroit(new Const(3));
        div.setFilsGauche(moins);
        moins.setFilsGauche(new Idf(varB_f));
        moins.setFilsDroit(new Const(5));
        f.ajouterUnFils(ret);
        ret.setLeFils(res_f);

        prog.ajouterUnFils(main);
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(appel);
        appel.ajouterUnFils(new Idf(varA));
        appel.ajouterUnFils(new Idf(varC));


        return prog;
    }

}
