package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConnexionController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label labelAngece;
    @FXML
    private Label labeImmo;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField prompTextUsername;
    @FXML
    private TextField prompTextPasword;
    @FXML
    private Button ButtonLogin;
    @FXML
    private Label labelErreur;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}