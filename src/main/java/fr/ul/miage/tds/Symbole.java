package fr.ul.miage.tds;

import fr.ul.miage.arbre.Fonction;

public class Symbole {
    public enum Categorie {
        fonction, global, param, local
    }

    public enum Type {
        vide, entier
    }

    private String nom;
    private Type type;
    private Categorie cat;
    private Integer val;
    private Integer rang;
    private Symbole scope;
    private Integer nbParam;
    private Integer nbLoc;

    public Symbole(String nom, Type type, Categorie cat) {
        this(nom, type, cat, 0);
    }

    public Symbole(String nom, Type type, Categorie cat, Integer val) {
        this.nom = nom;
        this.type = type;
        this.cat = cat;
        this.val = val;
    }



    public Symbole(String nom, Type type, Categorie cat, Integer rang, Symbole scope) {
        this.nom = nom;
        this.type = type;
        this.cat = cat;
        this.rang = rang;
        this.scope = scope;
    }

    public Symbole(String nom, Type type, Categorie cat, Integer nbParam, Integer nbLoc) {
        this.nom = nom;
        this.type = type;
        this.cat = cat;
        this.nbParam = nbParam;
        this.nbLoc = nbLoc;
    }

    public String getNom() {
        return nom;
    }
    public Type getType() {
        return type;
    }
    public Categorie getCat() {
        return cat;
    }
    public Integer getVal() {
        return val;
    }
    public Integer getRang() {
        return rang;
    }
    public Symbole getScope() {
        return scope;
    }
    public Integer getNbParam() {
        return nbParam;
    }
    public Integer getNbLoc() {
        return nbLoc;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void setCat(Categorie cat) {
        this.cat = cat;
    }
    public void setVal(Integer val) {
        this.val = val;
    }
    public void setRang(Integer rang) {
        this.rang = rang;
    }
    public void setScope(Symbole scope) {
        this.scope = scope;
    }
    public void setNbParam(Integer nbParam) {
        this.nbParam = nbParam;
    }
    public void setNbLoc(Integer nbLoc) {
        this.nbLoc = nbLoc;
    }

    @Override
    public String toString() {
        StringBuilder chaine = new StringBuilder();
        chaine.append("TDS {")
                .append("nom='").append(nom).append('\'')
                .append(", type=").append(type)
                .append(", cat=").append(cat);

        if (val != null) {
            chaine.append(", val=").append(val);
        }

        chaine.append('}');
        return chaine.toString();
    }
}
