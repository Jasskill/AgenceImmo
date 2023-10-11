package com.example.agenceimmo;

public class Logement {
    private int id;
    private String rue;
    private String codePostale;
    private String ville;
    private String description;


    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRue(){
        return this.rue;
    }
    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostale(){
        return this.codePostale;
    }
    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    public void setDescription(String description){ this.description = description; }
    public String getDescription(){ return description; }

    public Logement(int unId, String uneRue, String unCodePostale, String uneVille,String uneDescription ){
        this.id = unId;
        this.rue = uneRue;
        this.codePostale = unCodePostale;
        this.ville = uneVille;
        this.description = uneDescription;
    }

    public String toString(){
        String leLogement = "";

        leLogement = this.id + "\t ; " + this.rue + "\t ; " + this.ville + "\t ; " + this.codePostale + "\t ; " + this.description;

        return leLogement;
    }
}
