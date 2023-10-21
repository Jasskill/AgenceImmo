package com.example.agenceimmo;

import java.util.ArrayList;

public class Piece {
    private int id;
    private int surface;

    private String type;

    private Logement leLogement;

    private ArrayList<Equipement> lesEquipement;
    private ArrayList<Photo> lesPhotos;




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

    public Piece(int uneSurface, String unType){
        this.surface = uneSurface;
        this.type = unType;
        this.lesEquipement= new ArrayList<Equipement>();
    }

    public Piece(){
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

    public ArrayList<Photo> getLesPhotos() {
        return lesPhotos;
    }

    public void setLesPhotos(ArrayList<Photo> lesPhotos) {
        this.lesPhotos = lesPhotos;
    }

    public void ajouterEquipement(Equipement e){
        this.lesEquipement.add(e);
    }

    public void ajouterPhoto(Photo p){
        this.lesPhotos.add(p);
    }

    public String toString(){
        System.out.println("Piece : " + this.getId() + " ; " + this.getSurface() + " ; " + this.getType() + " ; " + this.getLeLogement().getId());
        return null;
    }
}
