package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        newWindow.setScene(scene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.show();

    }
}