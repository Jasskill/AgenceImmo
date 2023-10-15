package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    protected void userLogIn() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AgenceImmo.class.getResource("principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        newWindow.setScene(scene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.show();
    }
}