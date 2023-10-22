package com.example.agenceimmo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


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

    private ArrayList<Photo> lesPhotosAEnvoyer = new ArrayList<Photo>();
    private ArrayList<File> lesFilesAEnvoyer = new ArrayList<File>();

    /*private Photo laPhotoLogement = null;
    private File laFilePhoto = null;*/


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
                if(msgajoutphoto.getText().equals("")){
                    msgajoutphoto.setText("Fichier sélectionné : " + file.getName());
                }else{
                    msgajoutphoto.setText(msgajoutphoto.getText() + " ; " + file.getName());
                }

                msgajoutphoto.setVisible(true);

                changerLien(laPhoto);
                lesPhotosAEnvoyer.add(laPhoto);
                lesFilesAEnvoyer.add(file);
            }else{
                System.out.println("mauvais type de fichier");
            }

        } else {
            msgajoutphoto.setVisible(false);
            System.out.println("On va rien faire, comme tu veux !");
        }
    }
//String uri = "sftp://agenceimmo:0550002D@172.19.0.44/var/www/html/uploads/" + file.getName();
    private String serveur = "192.168.1.27"; // 172.19.0.44
    private String user = "sio"; // agenceimmo
    private String mdp = "0550002D"; // 0550002D

    public void envoyerImage(File file, Photo laPhoto){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(serveur, 21);

            if(ftpClient.login(user, mdp)){
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                laPhoto.setLien(UUID.randomUUID().toString());
                String chemin = "./uploads/" + laPhoto.getLien();
                InputStream inputStream = new FileInputStream(file);
                if(ftpClient.storeFile(chemin, inputStream)){
                    envoyerImageBDD(file, laPhoto);
                }else{
                    System.out.println("ça marche pas");
                }
                inputStream.close();
            }else{
                System.out.println("erreur d'authentification");
            }
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
            String requete = "INSERT INTO Photo (taille, type, lien, idLogement) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = co.prepareStatement(requete);
            stmt.setLong(1, unePhoto.getSize());
            stmt.setString(2, mimeType);
            stmt.setString(3, unePhoto.getLien());
            stmt.setInt(4, leLogement.getId());
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
            stmtSelect.close();
            co.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ok;
    }

    private Logement leLogement;
    public void ajouterLogement(){
        if(!saisierue.getText().equals("") && !saisiecodepostal.getText().equals("") && !saisieville.getText().equals("")){
            if(toutesLesSurfaces != null && toutesLesTypesPieces != null && tousLesNomsEquipements != null){
                boolean okay = true;
                for(int i = 0; i<toutesLesSurfaces.size(); i++){
                    if(toutesLesSurfaces.get(i).getText().equals("") && toutesLesTypesPieces.get(i).getValue().equals("")){
                        okay = false;
                        System.out.println("Les types de piece ou les surfaces ne sont pas bien remplies");
                    }
                    System.out.println(tousLesNomsEquipements.get(i).size());
                    for(TextField t : tousLesNomsEquipements.get(i)){
                        if(t.getText().equals("")){
                            okay = false;
                            System.out.println("Les équipement ne sont pas bien remplies");
                        }
                    }
                }
                if(okay){
                    leLogement = creerLeLogement();
                    envoyerLogementBDD(leLogement);
                    for(int i=0;i<lesPhotosAEnvoyer.size();i++){
                        if(lesFilesAEnvoyer.get(i) != null && lesPhotosAEnvoyer.get(i) != null){
                            envoyerImage(lesFilesAEnvoyer.get(i), lesPhotosAEnvoyer.get(i));
                        }
                    }
                    lesPhotosAEnvoyer.clear();
                    lesFilesAEnvoyer.clear();
                }else{
                    Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
                    uneAlerte.setContentText("Champs non remplies");
                    uneAlerte.show();
                }
            }else{
                Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
                uneAlerte.setContentText("Une pièce au moins nécessaire");
                uneAlerte.show();
            }
        }else{
            Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
            uneAlerte.setContentText("Champs non-remplie");
            uneAlerte.show();
        }
    }

    public void envoyerLogementBDD(Logement leLogement){

        try{
            Connection co = Connexion.getConnexion();
            //Envoie le Logement
            PreparedStatement stmt = co.prepareStatement("INSERT INTO Logement(rue, codePostal, ville, description) VALUES (?,?,?,?)");
            stmt.setString(1, leLogement.getRue());
            stmt.setString(2, leLogement.getCodePostale());
            stmt.setString(3, leLogement.getVille());
            stmt.setString(4, leLogement.getDescription());
            stmt.execute();
            stmt = co.prepareStatement("SELECT LAST_INSERT_ID() as i FROM Logement");
            ResultSet res = stmt.executeQuery();
            res.next();
            int id = res.getInt("i");
            leLogement.setId(id);
            System.out.println("\n\n");
            System.out.println(leLogement.toString());

            for(Piece unePiece : leLogement.getLesPieces()){
                stmt = co.prepareStatement("INSERT INTO Piece(surface, type, idLogement) VALUES (?,?,?)");
                stmt.setLong(1, unePiece.getSurface());
                stmt.setString(2, unePiece.getType());
                stmt.setInt(3, unePiece.getLeLogement().getId());
                stmt.execute();
                stmt = co.prepareStatement("SELECT LAST_INSERT_ID() as i FROM Piece");
                res = stmt.executeQuery();
                res.next();
                id = res.getInt("i");
                unePiece.setId(id);
                unePiece.toString();
                for(Equipement unEquipement : unePiece.getLesEquipement()){
                    stmt = co.prepareStatement("INSERT INTO Equipement(libelle, idPiece, idLogement) VALUES (?,?,?)");
                    stmt.setString(1, unEquipement.getLibelle());
                    stmt.setInt(2, unEquipement.getLaPiece().getId());
                    stmt.setInt(3, unEquipement.getLaPiece().getLeLogement().getId());
                    stmt.execute();
                    stmt = co.prepareStatement("SELECT LAST_INSERT_ID() as i FROM Equipement");
                    res = stmt.executeQuery();
                    res.next();
                    id = res.getInt("i");
                    unEquipement.setId(id);
                    unEquipement.toString();
                }
            }
            stmt.close();
            co.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Logement creerLeLogement(){
        Logement leLogement = new Logement(saisierue.getText(), saisiecodepostal.getText(), saisieville.getText(), saisiedescription.getText());
        ArrayList<Piece> lesPieces = new ArrayList<Piece>();

        for(int i=0;i<toutesLesTypesPieces.size();i++){
            Piece unePiece = new Piece(Integer.parseInt(toutesLesSurfaces.get(i).getText()), toutesLesTypesPieces.get(i).getValue());
            unePiece.setLeLogement(leLogement);
            ArrayList<Equipement> lesEquipements = new ArrayList<Equipement>();

            for (TextField t : tousLesNomsEquipements.get(i)) {
                Equipement unEquipement = new Equipement(t.getText());
                unEquipement.setLaPiece(unePiece);
                unEquipement.toString();
                lesEquipements.add(unEquipement);
            }
            unePiece.setLesEquipement(lesEquipements);
            unePiece.toString();
            lesPieces.add(unePiece);
        }
        leLogement.setLesPieces(lesPieces);
        System.out.println(leLogement.toString());
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
        tousLesNomsEquipements = new ArrayList<ArrayList<TextField>>();

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
            surfacePiece.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("[0-9.]")) {
                        surfacePiece.setText(newValue.replaceAll("[^0-9.]", ""));
                    }
                }
            });
            toutesLesSurfaces.add(surfacePiece);

            //Label labelEquipementPiece = new Label("EQUIPEMENT DE LA PIECE : " + i);
            //TextField equipementPiece = new TextField();

            Label labelNbEquipement = new Label("Sélectionner nombre équipement de la pièce");
            ObservableList<Integer> equipementOptions = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
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