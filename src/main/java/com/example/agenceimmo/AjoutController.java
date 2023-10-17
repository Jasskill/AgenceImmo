package com.example.agenceimmo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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


    public void onSelectNbPieces() {
        // Récupérer le nombre de pièces sélectionné
        int nombreDePieces = Integer.parseInt(combonbpieces.getValue());



        for (int i = 1; i <= nombreDePieces; i++) {
            Label labelTypePiece = new Label("TYPE DE PIECE " + i);
            labelTypePiece.setLayoutX(124.0);
            labelTypePiece.setLayoutY(397.0 + 30 * i); // Ajustez la position en conséquence

            ComboBox<String> selectTypePiece = new ComboBox<>();
            selectTypePiece.setLayoutX(123.0);
            selectTypePiece.setLayoutY(414.0 + 30 * i); // Ajustez la position en conséquence

            Label labelSurfacePiece = new Label("SURFACE DE LA PIECE " + i);
            labelSurfacePiece.setLayoutX(316.0);
            labelSurfacePiece.setLayoutY(396.0 + 30 * i); // Ajustez la position en conséquence

            TextField surfacePiece = new TextField();
            surfacePiece.setLayoutX(314.0);
            surfacePiece.setLayoutY(414.0 + 30 * i); // Ajustez la position en conséquence

            container.getChildren().addAll(labelTypePiece, selectTypePiece, labelSurfacePiece, surfacePiece);
        }
    }
}