package com.example.agenceimmo;

public class Logement {
    private int id;
    private String rue;
    private String codePostale;
    private String ville;


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

    public Logement(int unId, String uneRue, String unCodePostale, String uneVille){
        this.id = unId;
        this.rue = uneRue;
        this.codePostale = unCodePostale;
        this.ville = uneVille;
    }
}
