package com.example.agenceimmo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class ConnexionController {
    @FXML
    private TextField prompTextUsername;
    @FXML
    private TextField prompTextPassword;
    @FXML
    private Button ButtonLogin;
    @FXML
    private Label labelErreur;
    private String UtilisateurCo;
    @FXML
    protected void userLogIn() throws IOException {
        String user = prompTextUsername.getText();
        String mdp = prompTextPassword.getText();
        try{
            Connection coBaseImmobilier = Connexion.getConnexion();
            String requete = "SELECT mdp FROM Utilisateur WHERE mail = ? ";
            PreparedStatement stmtSelect = coBaseImmobilier.prepareStatement(requete);
            stmtSelect.setString(1, user);
            ResultSet res = stmtSelect.executeQuery();
            while (res.next()){
                boolean verif = BCrypt.checkpw(mdp, res.getString("utilisateur.mdp"));
                if (verif){
                    UtilisateurCo = user;
                    Stage newWindow = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(AgenceImmo.class.getResource("principal.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
                    newWindow.setScene(scene);
                    newWindow.initModality(Modality.APPLICATION_MODAL);
                    newWindow.show();
                    PrincipalController principalController = fxmlLoader.getController();
                    principalController.setUtilisateurCo(UtilisateurCo);
                }else {
                    Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
                    uneAlerte.setContentText("Login ou mot de passe incorrect");
                    uneAlerte.show();
                }
            }
            res.close();
            stmtSelect.close();
            coBaseImmobilier.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
