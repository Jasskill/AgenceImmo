package com.example.agenceimmo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.ComboBox;


import com.jcraft.jsch.*;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public class AjoutController {
    @FXML
    private Label msgajoutphoto;
    public void onAjoutPhotoclick() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir une photo");
        File file = chooser.showOpenDialog(new Stage());

        // Ajouter un type d'extension sur mon Chooser
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png"));
        if (file != null) {
            msgajoutphoto.setText("Fichier sélectionné : " + file.getName());
            msgajoutphoto.setVisible(true);
        } else {
            msgajoutphoto.setVisible(false);
        }
        if(file != null){
            String fileAsString = file.toString();
            System.out.println("On va uploader : " + fileAsString);
            FileSystemManager manager = null;
            try{
                manager = VFS.getManager();

                FileSystemOptions fsOptions = new FileSystemOptions();
                SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fsOptions, "no");
                SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fsOptions, false);
                SftpFileSystemConfigBuilder.getInstance().setTimeout(fsOptions, 10000);
                FileSystemManager fsManager = VFS.getManager();
                // découpe du fichier

                String uri = "sftp://agenceimmo:0550002D@172.19.0.44/var/www/html/uploads/"+file.getName();

                FileObject fo = fsManager.resolveFile(uri, fsOptions);
                FileObject local = manager.resolveFile(fileAsString);

                fo.copyFrom(local, new AllFileSelector());

                fo.close();
                local.close();

            }catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            try{
                Connection co = DriverManager.getConnection("jdbc:mysql://172.19.0.103/Immobilier", "dev", "0550002D");
                Statement stmt = co.createStatement();
                String requete = "INSERT INTO Photo ()";
                // à finir
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("On va rien faire, comme tu veux !");
        }
    }
    private void updateUI(int choixuti, int i) {

        for (int j = 1; j <= choixuti; j++) {
            Label labelNomEquip = new Label("NOM DE LEQUIPEMENT n° " + j + " DE LA PIECE : " + i);
            TextField nomEquip = new TextField();
            piecesContainer.getChildren().addAll(labelNomEquip, nomEquip);
        }
    }

    @FXML
    private ComboBox<String> combonbpieces;
    @FXML
    private VBox piecesContainer;
    @FXML
    private ComboBox<String> selectTypePiece;
    @FXML
    private ComboBox<Integer> selectnbequipements;
    public int choixuti;
    public void onSelectNbPieces() {
        // Récupérer le nombre de pièces sélectionné
        int nombreDePieces = Integer.parseInt(combonbpieces.getValue());
        // Sinon c le bordel et j'en génère 1500000
        piecesContainer.getChildren().clear();

        for (int i = 1; i <= nombreDePieces; i++) {
            int finalI = i;
            Label labelTypePiece = new Label("TYPE DE PIECE : " + i);
            selectTypePiece = new ComboBox<>();
            selectTypePiece.getItems().addAll("Salon","Salle-de-bain","Chambre","Garage","Cuisine");

            Label labelSurfacePiece = new Label("SURFACE DE LA PIECE : " + i);
            TextField surfacePiece = new TextField();

            Label labelEquipementPiece = new Label("EQUIPEMENT DE LA PIECE : " + i);
            TextField equipementPiece = new TextField();

            Label labelNbEquipement = new Label("Sélectionner nombre équipement de la pièce");
            ObservableList<Integer> equipementOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            selectnbequipements = new ComboBox<>(equipementOptions);
            ComboBox<Integer> nombreEquipements = selectnbequipements;
            piecesContainer.getChildren().addAll(labelTypePiece,selectTypePiece,labelSurfacePiece,surfacePiece,labelEquipementPiece,equipementPiece,labelNbEquipement,selectnbequipements);
            selectnbequipements.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                choixuti = newValue;
                System.out.println("Nouvelle valeur de choixuti : " + choixuti);
                updateUI(choixuti, finalI); // Mettez à jour l'UI en fonction de la nouvelle valeur de choixuti
            });
        }
    }
}