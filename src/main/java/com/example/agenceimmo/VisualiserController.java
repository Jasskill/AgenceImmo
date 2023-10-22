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

public class VisualiserController {
    @FXML
    private TableView<Piece> lesPieces;
    @FXML
    private TableColumn<Piece, String> pieceNom;
    @FXML
    private TableColumn<Piece, Long> pieceSurface;
    @FXML
    private TableView<Equipement> lesEquipements;
    @FXML
    private TableColumn<Equipement, String> equipementNom;

    private Logement leLogement;

    private Gestion g = new Gestion();

    public void setAll(Logement unLogement){
        this.leLogement = unLogement;

        pieceNom.setCellValueFactory(new PropertyValueFactory<Piece, String>("type"));
        pieceSurface.setCellValueFactory(new PropertyValueFactory<Piece, Long>("surface"));
        ObservableList<Piece> list = FXCollections.observableArrayList();
        try {
            for(Piece p : g.getLesPieces()){
                if(p.getLeLogement().getId() == leLogement.getId()){
                    list.add(p);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        lesPieces.setItems(list);

        equipementNom.setCellValueFactory(new PropertyValueFactory<Equipement, String>("libelle"));
        ObservableList<Equipement> listE = FXCollections.observableArrayList();
        try {
            for(Equipement eq : g.getLesEquipement()){
                if(eq.getLaPiece().getLeLogement().getId() == leLogement.getId()){
                    listE.add(eq);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        lesEquipements.setItems(listE);
    }

}
