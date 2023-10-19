package com.example.agenceimmo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Gestion {
    private ArrayList<Logement> lesLogements = new ArrayList<Logement>();
    private ArrayList<Piece> lesPieces = new ArrayList<Piece>();
    private ArrayList<Equipement> lesEquipement = new ArrayList<Equipement>();
    private ArrayList<Photo> lesPhotos = new ArrayList<Photo>();



    public Gestion(){
        this.recupererLogements();
        this.recupererPieces();
        this.recupererEquipement();
        this.recupererPhotos();
        System.out.println("Le nombre de Logements : " + this.getLesLogements().size());
        System.out.println("Le nombre de Pieces : " + this.getLesPieces().size());
        System.out.println("Le nombre de Equipements : " + this.getLesEquipement().size());
        System.out.println("Le nombre de Photos : " + this.getLesPhotos().size());
    }



    public ArrayList<Logement> getLesLogements() {
        return lesLogements;
    }

    public void setLesLogements(ArrayList<Logement> lesLogements) {
        this.lesLogements = lesLogements;
    }

    public ArrayList<Piece> getLesPieces() {
        return lesPieces;
    }

    public void setLesPieces(ArrayList<Piece> lesPieces) {
        this.lesPieces = lesPieces;
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

    public void recupererLogements(){
        ArrayList<Logement> lesLogements = new ArrayList<Logement>();

        try{
            Connection coBaseImmobilier = Connexion.getConnexion();
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, rue, codePostal, ville, description FROM Logement";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {
                // Récupération du champ "nom"
                int id = Integer.parseInt(res.getString("logement.id"));
                String rue = res.getString("logement.rue");
                String codePostal = res.getString("logement.codePostal");
                String ville = res.getString("logement.ville");
                String description = res.getString("logement.description");
                Logement unLogement = new Logement(id, rue, codePostal, ville, description);
                lesLogements.add(unLogement);
            }
            res.close();
            stmt.close();
            coBaseImmobilier.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.setLesLogements(lesLogements);
    }



    public void recupererPieces(){
        ArrayList<Piece> lesPieces = new ArrayList<Piece>();
        try{
            Connection coBaseImmobilier = Connexion.getConnexion();
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, surface, type, idLogement FROM Piece";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {
                int id = res.getInt("piece.id");
                int surface = res.getInt("piece.surface");
                String type = res.getString("piece.type");
                int idlogement = res.getInt("piece.idLogement");
                Piece uneP = new Piece(id, surface, type);
                for(Logement l : this.getLesLogements()){
                    if(idlogement == l.getId()){
                        uneP.setLeLogement(l);
                        l.ajouterPiece(uneP);
                        break;
                    }
                }
                lesPieces.add(uneP);
            }
            res.close();
            stmt.close();
            coBaseImmobilier.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.setLesPieces(lesPieces);
    }

    public void recupererEquipement(){
        ArrayList<Equipement> lesEquipements = new ArrayList<Equipement>();
        try{
            Connection coBaseImmobilier = Connexion.getConnexion();            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, libelle, idPiece FROM Equipement";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {
                int id = res.getInt("equipement.id");
                String libelle = res.getString("equipement.libelle");
                int idPiece = res.getInt("equipement.idPiece");
                Equipement unEquipement = new Equipement(id, libelle);
                for(Piece p : this.getLesPieces()){
                    if(idPiece == p.getId()){
                        unEquipement.setLaPiece(p);
                        p.ajouterEquipement(unEquipement);
                        break;
                    }
                }
                lesEquipements.add(unEquipement);
            }
            res.close();
            stmt.close();
            coBaseImmobilier.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.setLesEquipement(lesEquipements);
    }

    public void recupererPhotos(){
        ArrayList<Photo> lesPhotos = new ArrayList<Photo>();
        try{
            Connection coBaseImmobilier = Connexion.getConnexion();
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, taille, type, lien, idLogement, idPiece, idEquipement FROM Photo";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {
                int id = res.getInt("photo.id");
                int taille = res.getInt("photo.taille");
                String type = res.getString("photo.type");
                String lien = res.getString("photo.lien");
                Object idLogementO = res.getObject("photo.idLogement");
                Object idPieceO = res.getObject("photo.idPiece");
                Object idEquipementO = res.getObject("photo.idEquipement");
                Photo unePhoto = new Photo(id, lien, taille, type);
                try{
                    /*
                    ImageView unContenu = new ImageView();
                    File file = new File("sftp://agenceimmo:0550002D@172.19.0.44/var/www/html/uploads/" + lien);
                    String localUrl = file.toURI().toURL().toString();
                    Image image = new Image(localUrl);
                    unContenu.setImage(image);
                    unePhoto.setContenu(unContenu);
                     */
                }catch(Exception e){
                    e.printStackTrace();
                }

                if(idLogementO != null){
                    int idLogement = res.getInt("photo.idLogement");
                    for(Logement l : this.getLesLogements()){
                        if(idLogement == l.getId()){
                            unePhoto.setLeLogement(l);
                            l.ajouterPhoto(unePhoto);
                            break;
                        }
                    }
                }else if(idPieceO != null){
                    int idPiece = res.getInt("photo.idPiece");
                    for(Piece p : this.getLesPieces()){
                        if(idPiece == p.getId()){
                            unePhoto.setLaPiece(p);
                            p.ajouterPhoto(unePhoto);
                            break;
                        }
                    }
                }else if(idEquipementO != null){
                    int idEquipement = res.getInt("photo.idEquipement");
                    for(Equipement e : this.getLesEquipement()){
                        if(idEquipement == e.getId()){
                            unePhoto.setlEquipement(e);
                            e.ajouterPhoto(unePhoto);
                            break;
                        }
                    }
                }
                lesPhotos.add(unePhoto);
            }
            res.close();
            stmt.close();
            coBaseImmobilier.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
}
