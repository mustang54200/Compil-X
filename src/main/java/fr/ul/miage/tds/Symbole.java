package fr.ul.miage.tds;

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
    private String scope;
    private Integer nbParam;
    private Integer nbVar;

    public Symbole(String nom, Type type, Categorie cat, Integer val) {
        this.nom = nom;
        this.type = type;
        this.cat = cat;
        this.val = val;
    }

    public Symbole(String nom, Type type, Categorie cat) {
        this(nom, type, cat, 0);
    }

    public Symbole(String nom, Type type, Categorie cat, Integer rang, String scope) {
        this.nom = nom;
        this.type = type;
        this.cat = Categorie.param;
        this.rang = rang;
        this.scope = scope;
    }

    public Symbole(String nom, Type type, Categorie cat, Integer val, Integer nbParam, Integer nbvar) {
        this.nom = nom;
        this.type = type;
        this.cat = cat;
        this.val = val;
        this.nbParam = nbParam;
        this.nbVar = nbvar;
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
    public String getScope() {
        return scope;
    }
    public Integer getNbParam() {
        return nbParam;
    }
    public Integer getNbVar() {
        return nbVar;
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
    public void setScope(String scope) {
        this.scope = scope;
    }
    public void setNbParam(Integer nbParam) {
        this.nbParam = nbParam;
    }
    public void setNbVar(Integer nbvar) {
        this.nbVar = nbvar;
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
