package fr.ul.miage.tests;

import fr.ul.miage.arbre.*;
import fr.ul.miage.tds.Symbole;
import fr.ul.miage.tds.Tds;

public class Exemple8 {

    public static Noeud initialiserCompilateur(Tds tds) {

        Prog prog = new Prog();

        // tds
        Symbole fonctionMain = new Symbole("main", Symbole.Type.vide, Symbole.Categorie.fonction, 0, 0);
        Symbole varI = new Symbole("i", Symbole.Type.entier, Symbole.Categorie.global);

        tds.insertTable(fonctionMain);
        tds.insertTable(varI);


        // arbre
        Fonction main = new Fonction(fonctionMain);
        Affectation aff1 = new Affectation();
        Idf idfI = new Idf(varI);
        TantQue tq = new TantQue();
        Inferieur inf = new Inferieur();
        Bloc bloc = new Bloc();
        Ecrire ecrire = new Ecrire();
        Affectation aff2 = new Affectation();
        Plus plus = new Plus();

        prog.ajouterUnFils(main);

        main.ajouterUnFils(aff1);
        aff1.setFilsGauche(idfI);
        aff1.setFilsDroit(new Const(0));

        main.ajouterUnFils(tq);
        tq.setCondition(inf);
        inf.setFilsGauche(idfI);
        inf.setFilsDroit(new Const(6));

        tq.setBloc(bloc);
        bloc.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(idfI);

        bloc.ajouterUnFils(aff2);
        aff2.setFilsGauche(idfI);

        aff2.setFilsDroit(plus);
        plus.setFilsGauche(idfI);
        plus.setFilsDroit(new Const(1));


        return prog;
    }

}


