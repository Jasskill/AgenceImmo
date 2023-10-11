package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PrincipalController {
    @FXML
    private Label labelUtilisateur;

    @FXML
    private Button boutonAjouter;

    @FXML
    private Button boutonDeco;

    @FXML
    private TableView<Logement> tableLogements;

    //@FXML
    //private TableColumn<Logement, Image> colonneImage;

    @FXML
    private TableColumn<Logement, String> colonneCodePostal;

    @FXML
    private TableColumn<Logement, String> colonneVille;

    @FXML
    private TableColumn<Logement, String> colonneRue;

    @FXML
    private TableColumn<Logement, Integer> colonnePieces;

    @FXML
    protected void onBoutonAjouterClick() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ajout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        newWindow.setScene(scene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.show();
    }

    @FXML
    protected void onBoutonDecoClick() throws IOException {
        //Ferme la page principal
    }

    public ArrayList<Logement> recupererLogements(){
        ArrayList<Logement> lesLogements = new ArrayList<Logement>();

        try{
            Connection coBaseImmobilier = DriverManager.getConnection("jdbc:mysql://172.19.0.103/Immobilier", "dev", "0550002D");
            Statement stmt = coBaseImmobilier.createStatement();
            String requete = "SELECT id, rue, codePostal, ville FROM Logement ORDER BY id ASC";
            ResultSet res = stmt.executeQuery(requete);
            while (res.next())
            {
                // Récupération du champ "nom"
                int id = Integer.parseInt(res.getString("logement.id"));
                String rue = res.getString("logement.rue");
                String codePostal = res.getString("logement.codePostal");
                String ville = res.getString("logement.ville");
                Logement unLogement = new Logement(id, rue, codePostal, ville);
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
