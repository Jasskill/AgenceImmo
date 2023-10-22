package com.example.agenceimmo;

import javafx.scene.image.ImageView;

public class Photo {
    private int id;
    private String lien;
    private long size;
    private String type;
    private Logement leLogement;

    private Piece laPiece;

    private Equipement lEquipement;

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
    public long getSize(){
        return this.size;
    }
    public void setsize(long size){
        this.size = size;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
    public Photo(int unId, String unLien, long uneTaille, String unType){
        this.id = unId;
        this.lien = unLien;
        this.size = uneTaille;
        this.type = unType;
    }

    public Photo(String unLien, long uneTaille, String unType){
        this.lien = unLien;
        this.size = uneTaille;
        this.type = unType;
    }

    public Logement getLeLogement() {
        return leLogement;
    }

    public void setLeLogement(Logement leLogement) {
        this.leLogement = leLogement;
    }

    public Piece getLaPiece() {
        return laPiece;
    }

    public void setLaPiece(Piece laPiece) {
        this.laPiece = laPiece;
    }

    public Equipement getlEquipement() {
        return lEquipement;
    }

    public void setlEquipement(Equipement lEquipement) {
        this.lEquipement = lEquipement;
    }
}
