package fr.ul.miage.tests;

import fr.ul.miage.arbre.Noeud;
import fr.ul.miage.arbre.TxtAfficheur;
import fr.ul.miage.generation.Generateur;
import fr.ul.miage.tds.Tds;

public class MainTest {
    public static void main(String[] args) {

        Tds tds = new Tds();
        Noeud prog = Exemple8.initialiserCompilateur(tds);

        System.out.println("Arbre source :\n");
        TxtAfficheur.afficher(prog);
        System.out.println("Code généré :\n");

        Generateur gen = new Generateur(tds);
        String code = gen.genererProgramme(prog);

        System.out.println(code);
    }
}
