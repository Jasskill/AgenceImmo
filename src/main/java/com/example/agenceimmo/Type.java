package com.example.agenceimmo;

public class Type {
    private int id;
    private String libelle;

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getLibelle() {
        return this.libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    private Type(int unId, String unLibelle){
        this.id = unId;
        this.libelle = unLibelle;
    }
}
