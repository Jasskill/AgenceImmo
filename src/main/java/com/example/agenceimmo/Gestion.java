package com.example.agenceimmo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class Gestion {
    private ArrayList<Logement> lesLogements;

    public Gestion(){
        this.recupererLogements();
        this.recupererPhotos();
        this.recupererPieces();
    }

    public ArrayList<Logement> recupererLogements(){
        ArrayList<Logement> lesLogements = new ArrayList<Logement>();

        try{
            Connection coBaseImmobilier = DriverManager.getConnection("jdbc:mysql://172.19.0.44/Immobilier", "agentimmobilier", "0550002D");
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, rue, codePostal, ville, description FROM Logement ORDER BY id ASC";
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
        this.lesLogements = lesLogements;
        return lesLogements;
    }

    public void recupererPhotos(){
        try{
            Connection coBaseImmobilier = DriverManager.getConnection("jdbc:mysql://172.19.0.44/Immobilier", "agentimmobilier", "0550002D");
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, taille, type, lien, idLogement FROM Photo ORDER BY id ASC";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {
                int id = res.getInt("photo.id");
                int taille = res.getInt("photo.taille");
                String type = res.getString("photo.type");
                String lien = res.getString("photo.lien");
                Object idLogementO = res.getObject("photo.idLogement");
                Photo unePhoto = new Photo(id, lien, taille, type);
                if(idLogementO != null){
                    int idLogement = res.getInt("photo.idLogement");
                    for(Logement l : this.lesLogements){
                        if(idLogement == l.getId()){
                            unePhoto.setLeLogement(l);
                            l.ajouterPhoto(unePhoto);
                            break;
                        }
                    }
                }
            }

            res.close();
            stmt.close();
            coBaseImmobilier.close();
        }catch(Exception e){

        }

    }

    public void recupererPieces(){
        try{
            Connection coBaseImmobilier = DriverManager.getConnection("jdbc:mysql://172.19.0.44/Immobilier", "agentimmobilier", "0550002D");
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, surface, type, idLogement FROM Piece ORDER BY id ASC";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {

                int id = res.getInt("piece.id");

                int surface = res.getInt("piece.surface");
                String type = res.getString("piece.type");
                int idlogement = res.getInt("piece.idLogement");
                Piece uneP = new Piece(id, surface, type);

                for(Logement l : this.lesLogements){
                    if(idlogement == l.getId()){

                        uneP.setLeLogement(l);

                        l.ajouterPiece(uneP);
                        break;
                    }

                }

            }

            res.close();
            stmt.close();
            coBaseImmobilier.close();
        }catch(Exception e){

        }
    }
}
