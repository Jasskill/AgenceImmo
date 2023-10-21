package com.example.agenceimmo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.FileSystem;
import java.sql.*;
import java.util.ArrayList;


import com.jcraft.jsch.*;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public class AjoutController {
    @FXML
    private Label msgajoutphoto;

    @FXML
    private TextField saisierue;
    @FXML
    private TextField saisiecodepostal;
    @FXML
    private TextField saisieville;
    @FXML
    private TextArea saisiedescription;
    @FXML
    private ComboBox<String> combonbpieces;

    @FXML
    private ComboBox<String> selectTypePiece;
    @FXML
    private ComboBox<Integer> selectnbequipements;

    @FXML
    private Button boutonAjouter;
    public int choixuti;

    private Photo laPhotoLogement;
    private File laFilePhoto;





    public void onAjoutPhotoclick() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir une photo");
        File file = chooser.showOpenDialog(new Stage());
        Photo laPhoto;

        // Ajouter un type d'extension sur mon Chooser
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png"));
        if (file != null) {
            //file.toPath();
            String typeMime = URLConnection.guessContentTypeFromName(file.getName());
            laPhoto = new Photo(file.getName(), file.length(), typeMime);
            if(typeMime.equals("image/png") ||typeMime.equals("image/jpeg") || typeMime.equals("image/bmp")) {
                msgajoutphoto.setText("Fichier sélectionné : " + file.getName());
                msgajoutphoto.setVisible(true);

                changerLien(laPhoto);
                laPhotoLogement = laPhoto;
                laFilePhoto = file;
                //envoyerImage(file, laPhoto);
            }else{
                System.out.println("mauvais type de fichier");
            }

        } else {
            msgajoutphoto.setVisible(false);
            System.out.println("On va rien faire, comme tu veux !");
        }
    }

    public void envoyerImage(File file, Photo laPhoto){
        String fileAsString = file.toString();
        System.out.println("On va uploader : " + laPhoto.getLien());
        System.out.println("On va uploader : " + fileAsString);
        FileSystemManager manager = null;
        try {
            manager = VFS.getManager();

            FileSystemOptions fsOptions = new FileSystemOptions();
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fsOptions, "no");
            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fsOptions, false);
            SftpFileSystemConfigBuilder.getInstance().setTimeout(fsOptions, 10000);
            FileSystemManager fsManager = VFS.getManager();
            // découpe du fichier

            String uri = "sftp://agenceimmo:0550002D@172.19.0.44/var/www/html/uploads/" + file.getName();

            FileObject fo = fsManager.resolveFile(uri, fsOptions);
            FileObject local = manager.resolveFile(fileAsString);

            fo.copyFrom(local, new AllFileSelector());

            fo.close();
            local.close();

            envoyerImageBDD(file, laPhoto);

        } catch (Exception e) {
            // Erreur dans l'envoie du fichier
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void envoyerImageBDD(File file, Photo unePhoto){
        try {
            Connection co = Connexion.getConnexion();

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            String requete = "INSERT INTO Photo (taille, type, lien) VALUES (?, ?, ?)";
            PreparedStatement stmt = co.prepareStatement(requete);
            stmt.setLong(1, unePhoto.getSize());
            stmt.setString(2, mimeType);
            stmt.setString(3, unePhoto.getLien());
            stmt.execute();
            stmt.close();
            co.close();
            // à finir
        } catch (SQLException e) {
            // Erreur dans la connexion ou dans la requête à la base de données
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void changerLien(Photo unePhoto){
        try{
            Connection co = Connexion.getConnexion();
            String reqSelect = "SELECT LAST_INSERT_ID() as id FROM Photo";
            PreparedStatement stmtSelect = co.prepareStatement(reqSelect);
            ResultSet res = stmtSelect.executeQuery(reqSelect);
            res.next();
            int id = res.getInt("id");
            id++;
            unePhoto.setId(id);
            unePhoto.setLien(id + "_" + unePhoto.getLien());
            stmtSelect.close();
            co.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        int i = 0;
        while(!verifierLienUnique(unePhoto)){
            unePhoto.setLien(unePhoto.getLien() + i);
            i++;
        }
    }

    public boolean verifierLienUnique(Photo unePhoto){
        boolean ok = false;
        try{
            Connection co = Connexion.getConnexion();
            PreparedStatement stmtSelect = co.prepareStatement("SELECT COUNT(*) as c FROM Photo WHERE lien = ?");
            stmtSelect.setString(1, unePhoto.getLien());
            ResultSet res = stmtSelect.executeQuery();
            res.next();
            if(res.getInt("c") == 0){
                ok = true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ok;
    }

    //private Logement leLogement;
    public void ajouterLogement(){
        Logement leLogement = creerLeLogement();
        envoyerLogementBDD(leLogement);
    }

    public void envoyerLogementBDD(Logement leLogement){
        /*
        try{
            Connection co = Connexion.getConnexion();
            PreparedStatement stmtSelect = co.prepareStatement("INSERT INTO Logement(rue, codePostal, ville, description) VALUES (?,?,?,?)");
            stmtSelect.setString(1, leLogement.getRue());
            stmtSelect.setString(2, leLogement.getCodePostale());
            stmtSelect.setString(3, leLogement.getVille());
            stmtSelect.setString(4, leLogement.getDescription());
            stmtSelect.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        */
        leLogement.setId(8);
    }

    public Logement creerLeLogement(){
        Logement leLogement = new Logement(saisierue.getText(), saisiecodepostal.getText(), saisieville.getText(), saisiedescription.getText());
        ArrayList<Piece> lesPieces = new ArrayList<Piece>();

        for(int i=0;i<toutesLesTypesPieces.size();i++){
            Piece p = new Piece(Integer.parseInt(toutesLesSurfaces.get(i).getText()), toutesLesTypesPieces.get(i).getValue());
            ArrayList<Equipement> lesEquipements = new ArrayList<Equipement>();

            for (TextField t : tousLesNomsEquipements.get(i)) {
                Equipement unEquipement = new Equipement(t.getText());
                lesEquipements.add(unEquipement);
            }
            p.setLesEquipement(lesEquipements);
            lesPieces.add(p);
        }
        leLogement.setLesPieces(lesPieces);
        return leLogement;
    }


    private ArrayList<ComboBox<String>> toutesLesTypesPieces;
    private ArrayList<TextField> toutesLesSurfaces;
    private ArrayList<ArrayList<TextField>> tousLesNomsEquipements;

    @FXML
    private VBox piecesContainer;

    public void onSelectNbPieces() {
        toutesLesTypesPieces = new ArrayList<ComboBox<String>>();
        toutesLesSurfaces = new ArrayList<TextField>();

        // Récupérer le nombre de pièces sélectionné
        int nombreDePieces = Integer.parseInt(combonbpieces.getValue());
        // Sinon c'est le bazar et j'en génère 1500000
        piecesContainer.getChildren().clear();

        for (int i = 1; i <= nombreDePieces; i++) {
            int finalI = i;
            //CREER UN NV CONTENEUR POUR LES PIECES
            VBox pieceContainer = new VBox();

            Label labelTypePiece = new Label("TYPE DE PIECE : " + i);
            ComboBox<String> selectTypePiece = new ComboBox<>();
            selectTypePiece.getItems().addAll("Salon", "Salle-de-bain", "Chambre", "Garage", "Cuisine");
            toutesLesTypesPieces.add(selectTypePiece);

            Label labelSurfacePiece = new Label("SURFACE DE LA PIECE : " + i);
            TextField surfacePiece = new TextField();
            toutesLesSurfaces.add(surfacePiece);

            //Label labelEquipementPiece = new Label("EQUIPEMENT DE LA PIECE : " + i);
            //TextField equipementPiece = new TextField();

            Label labelNbEquipement = new Label("Sélectionner nombre équipement de la pièce");
            ObservableList<Integer> equipementOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            ComboBox<Integer> selectnbequipements = new ComboBox<>(equipementOptions);

            // Ajoutez tous les éléments de la pièce au conteneur de la pièce
            pieceContainer.getChildren().addAll(labelTypePiece, selectTypePiece, labelSurfacePiece, surfacePiece, /*labelEquipementPiece, equipementPiece,*/ labelNbEquipement, selectnbequipements);
            // Ajoutez le conteneur de la pièce à piecesContainer
            piecesContainer.getChildren().add(pieceContainer);

            selectnbequipements.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                ArrayList<TextField> lesNomsEquipements = new ArrayList<TextField>();
                pieceContainer.getChildren().clear();
                VBox equipementContainer = new VBox();
                choixuti = newValue;
                System.out.println("Nouvelle valeur de choixuti : " + choixuti);
                for (int j = 1; j <= choixuti; j++) {
                    Label labelNomEquip = new Label("NOM DE L'EQUIPEMENT n° " + j + " DE LA PIECE : " + finalI);
                    TextField nomEquip = new TextField();
                    equipementContainer.getChildren().addAll(labelNomEquip, nomEquip);
                    lesNomsEquipements.add(nomEquip);
                }
                pieceContainer.getChildren().addAll(labelTypePiece, selectTypePiece, labelSurfacePiece, surfacePiece, /*labelEquipementPiece, equipementPiece,*/ labelNbEquipement, selectnbequipements, equipementContainer);
                tousLesNomsEquipements.add(lesNomsEquipements);
            });
        }
    }
}