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

    Gestion g = new Gestion();


    @FXML
    protected void onBoutonAjouterClick() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AgenceImmo.class.getResource("ajout.fxml"));
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




}
