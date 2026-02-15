package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple9 {

    public static Noeud initialiserCompilateur(Tds tds) {

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        Symbole fonctionF = new Symbole("f", Symbole.Type.entier, Symbole.Categorie.fonction, 1, 0);
        Symbole varA = new Symbole("a", Symbole.Type.entier, Symbole.Categorie.param, 0, "f");

        tds.insertTable(fonctionMain);
        tds.insertTable(fonctionF);
        tds.insertTable(varA);


        // arbre
        Fonction f = new Fonction(fonctionF);
        Idf idfA = new Idf(varA);

        Si si = new Si();
        InferieurEgal infEq = new InferieurEgal();
        Bloc bloc = new Bloc();
        Retour retSi = new Retour(fonctionF);

        Retour ret = new Retour(fonctionMain);
        Plus plus = new Plus();
        Appel appelF = new Appel(fonctionF);
        Moins moins = new Moins();

        Fonction main = new Fonction(fonctionMain);
        Ecrire ecrire = new Ecrire();
        Appel appelMain = new Appel(fonctionF);


        prog.ajouterUnFils(f);
        f.ajouterUnFils(si);

        si.setCondition(infEq);
        infEq.setFilsGauche(idfA);
        infEq.setFilsDroit(new Const(0));

        si.setBlocAlors(bloc);
        si.setBlocSinon(bloc); // ???
        bloc.ajouterUnFils(retSi);
        retSi.setLeFils(new Const(0));

        f.ajouterUnFils(ret);
        ret.setLeFils(plus);
        plus.setFilsGauche(idfA);
        plus.setFilsDroit(appelF);
        appelF.ajouterUnFils(moins);
        moins.setFilsGauche(idfA);
        moins.setFilsDroit(new Const(1));

        prog.ajouterUnFils(main);
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(appelMain);
        appelMain.ajouterUnFils(new Const(6));


        return prog;
    }

}


