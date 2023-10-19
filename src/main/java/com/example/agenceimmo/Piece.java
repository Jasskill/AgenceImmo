package com.example.agenceimmo;

import java.util.ArrayList;

public class Piece {
    private int id;
    private int surface;

    private String type;

    private Logement leLogement;

    private ArrayList<Equipement> lesEquipement;




    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getSurface() {
        return this.surface;
    }
    public void setSurface(int surface) {
        this.surface = surface;
    }


    public Piece(int unId, int uneSurface, String unType){
        this.id = unId;
        this.surface = uneSurface;
        this.type = unType;
        this.lesEquipement= new ArrayList<Equipement>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Logement getLeLogement() {
        return leLogement;
    }

    public void setLeLogement(Logement leLogement) {
        this.leLogement = leLogement;
    }

    public ArrayList<Equipement> getLesEquipement() {
        return lesEquipement;
    }

    public void setLesEquipement(ArrayList<Equipement> lesEquipement) {
        this.lesEquipement = lesEquipement;
    }
}
