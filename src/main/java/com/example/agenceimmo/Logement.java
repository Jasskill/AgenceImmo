package com.example.agenceimmo;

import java.util.ArrayList;

public class Logement {
    private int id;
    private String rue;
    private String codePostale;
    private String ville;
    private String description;

    private ArrayList<Piece> lesPieces;
    private ArrayList<Photo> lesPhotos;

    private int nbPiece;




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
        this.lesPhotos = new ArrayList<Photo>();
        this.lesPieces = new ArrayList<Piece>();
    }

    public String toString(){
        String leLogement = "";

        leLogement = this.id + "\t ; " + this.rue + "\t ; " + this.ville + "\t ; " + this.codePostale + "\t ; " + this.description;

        return leLogement;
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

    public int getNbPiece() {
        return nbPiece;
    }

    public void setNbPiece(int nbPiece) {
        this.nbPiece = nbPiece;
    }

    public ArrayList<Piece> getLesPieces() {
        return lesPieces;
    }

    public void setLesPieces(ArrayList<Piece> lesPieces) {
        this.lesPieces = lesPieces;
    }

    public void ajouterPiece(Piece p){
        this.lesPieces.add(p);
        this.nbPiece = lesPieces.size();
    }
}
