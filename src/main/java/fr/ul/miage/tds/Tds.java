package fr.ul.miage.tds;


import java.util.ArrayList;

public class Tds {
    private final ArrayList<Symbole> table = new ArrayList<>();

    public ArrayList<Symbole> getTable(){
        return  this.table;
    }

    public Symbole getSymboleFromName(String name){
        /*
        A partir du nom du noeud, on peut récuperer le symbole associé via son nom
         */
        for(Symbole S: this.getTable()) {
            if(S.getNom().equals(name)) {
                return S;
            }
        }
        return null;
    }
}
