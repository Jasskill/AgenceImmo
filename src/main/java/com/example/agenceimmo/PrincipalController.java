package com.example.agenceimmo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrincipalController {
    @FXML
    private Label labelUtilisateur;

    @FXML
    private Button boutonAjouter;
    @FXML
    private Button boutonDeco;

    @FXML
    private ImageView uneImage;

    @FXML
    private TableView<Logement> tableLogements;

    @FXML
    private TableColumn<Photo, ImageView> colonneImage;

    @FXML
    private TableColumn<Logement, String> colonneCodePostal;

    @FXML
    private TableColumn<Logement, String> colonneVille;

    @FXML
    private TableColumn<Logement, String> colonneRue;

    @FXML
    private TableColumn<Logement, Integer> colonnePieces;

    public ObservableList<Logement> list = FXCollections.observableArrayList();

    public Gestion g = new Gestion();



    public void initialize() {
        colonneCodePostal.setCellValueFactory(new PropertyValueFactory<Logement, String>("codePostale"));
        colonneVille.setCellValueFactory(new PropertyValueFactory<Logement, String>("ville"));
        colonneRue.setCellValueFactory(new PropertyValueFactory<Logement, String>("rue"));
        colonnePieces.setCellValueFactory(new PropertyValueFactory<Logement, Integer>("nbPiece"));
        try {
            for(Logement l : g.getLesLogements()){
                list.add(l);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tableLogements.setItems(list);

        // An image file on the hard drive.


    }

    @FXML
    protected void onBoutonAjouterClick() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AgenceImmo.class.getResource("ajout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 720);
        newWindow.setScene(scene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.show();
    }

    @FXML
    protected void onBoutonVisualiserClick() throws IOException {
        if(!tableLogements.getSelectionModel().isEmpty()){
            Stage newWindow = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(AgenceImmo.class.getResource("visualiser.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 320);
            newWindow.setScene(scene);
            // Specifies the modality for new window.
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.show();
            VisualiserController controller = (VisualiserController) fxmlLoader.getController();
            controller.setAll(tableLogements.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    protected void onBoutonDecoClick() throws IOException {
        //Ferme la page principal
    }




}
