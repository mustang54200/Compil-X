package fr.ul.miage.tests;

import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Noeud;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.TxtAfficheur;
import fr.ul.miage.generation.Generateur;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple2 {

    public static Noeud initialiserCompilateur(Tds tds) {

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        tds.insertTable(fonctionMain);
        Symbole varI = new Symbole("i", Symbole.Type.entier, Symbole.Categorie.global, 10);
        tds.insertTable(varI);
        Symbole varJ = new Symbole("j", Symbole.Type.entier, Symbole.Categorie.global, 20);
        tds.insertTable(varJ);
        Symbole varK = new Symbole("k", Symbole.Type.entier, Symbole.Categorie.global);
        tds.insertTable(varK);
        Symbole varL = new Symbole("l", Symbole.Type.entier, Symbole.Categorie.global);
        tds.insertTable(varL);

        // arbre
        Fonction main = new Fonction(fonctionMain);
        prog.ajouterUnFils(main);

        return prog;
    }

}