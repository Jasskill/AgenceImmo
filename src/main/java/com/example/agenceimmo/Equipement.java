package com.example.agenceimmo;

import java.util.ArrayList;

public class Equipement {
    private int id;
    private String libelle;
    private Piece laPiece;
    private ArrayList<Photo> lesPhotos;
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
    public Equipement(int unId, String unLibelle){
        this.id = unId;
        this.libelle = unLibelle;
    }
    public Equipement(String unLibelle){
        this.libelle = unLibelle;
    }
    public Piece getLaPiece() {
        return laPiece;
    }
    public void setLaPiece(Piece laPiece) {
        this.laPiece = laPiece;
    }
    public ArrayList<Photo> getLesPhotos() {
        return lesPhotos;
    }
    public void setLesPhotos(ArrayList<Photo> lesPhotos) {
        this.lesPhotos = lesPhotos;
    }
    public void ajouterPhoto(Photo p){
        this.lesPhotos.add(p);
    }
    public String toString(){
        System.out.println("Equipement : " + this.getId() + " ; " + this.getLibelle() + " ; " + this.getLaPiece().getId() + " ; " + this.getLaPiece().getLeLogement().getId());
        return null;
    }
}
