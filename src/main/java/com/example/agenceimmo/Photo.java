package com.example.agenceimmo;

public class Photo {
    private int id;
    private String nom;
    private int size;
    private String type;
    private byte bin;

    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom) {
        this.id = id;
    }
    public int getSize(){
        return this.size;
    }
    public void setsize(int size){
        this.size = size;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setBin(byte bin){
        this.bin = bin;
    }
    public byte getBin(){
        return this.bin;
    }
    public Photo(int unId, String unNom, int uneTaille, String unType, byte unBin){
        this.id = unId;
        this.nom = unNom;
        this.size = uneTaille;
        this.type = unType;
        this.bin = unBin;
    }
}
