package com.example.agenceimmo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Gestion {
    private ArrayList<Logement> lesLogements;

    public Gestion(){
        this.lesLogements = new ArrayList<Logement>();
    }

    public ArrayList<Logement> recupererLogements(){
        ArrayList<Logement> lesLogements = new ArrayList<Logement>();

        try{
            Connection coBaseImmobilier = DriverManager.getConnection("jdbc:mysql://172.19.0.103/Immobilier", "dev", "0550002D");
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

        return lesLogements;
    }
}
