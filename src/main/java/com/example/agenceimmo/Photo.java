package com.example.agenceimmo;

public class Photo {
    private int id;
    private String lien;

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLien(){
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }


    public Photo(int unId, String unLien){
        this.id = unId;
        this.lien = unLien;
    }
}
