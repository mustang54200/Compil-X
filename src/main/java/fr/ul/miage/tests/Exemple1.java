package fr.ul.miage.tests;

import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Noeud;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.TxtAfficheur;
import fr.ul.miage.generation.Generateur;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple1 {

    public static void main(String[] args) {

        Tds tds = new Tds();
        Noeud prog = initialiserCompilateur(tds);

        System.out.println("Arbre source :\n");
        TxtAfficheur.afficher(prog);
        System.out.println("Code généré :\n");


        Generateur gen = new Generateur(tds);
        String code = gen.genererProgramme(prog);

        System.out.println(code);



    }

    private static Noeud initialiserCompilateur(Tds tds){

        Prog prog = new Prog();


        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        tds.insertTable(fonctionMain);

        Fonction main = new Fonction(fonctionMain);
        prog.ajouterUnFils(main);

        return prog;
    }




}
