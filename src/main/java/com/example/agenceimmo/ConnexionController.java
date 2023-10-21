package com.example.agenceimmo;

import com.jcraft.jsch.jce.SHA256;
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
import java.security.MessageDigest;
import java.sql.*;



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
    private TextField prompTextPassword;
    @FXML
    private Button ButtonLogin;
    @FXML
    private Label labelErreur;


    @FXML
    public void connexUser(ActionEvent e)throws IOException{
        checkLogin();
    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        String user = prompTextUsername.getText();
        String mdp = prompTextPassword.getText();

        /*if (prompTextUsername.getText().toString().equals("javacoding") && prompTextPasword.getText().toString().equals("mdp")){
            wrongLogin
        }

         */
        //methode d'essaie à supprimer quand userLogin sera fonctionnel
        if ("admin".equals(mdp)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("principal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ButtonLogin.getScene().getWindow();
            Scene scene = new Scene(root);
        }else{
            //Bdd bdd = new Bdd;

        }

    }


    private boolean bonIdentifiaction(String user,String mdp){
        return "admin".equals(user) && "password".equals(mdp);
    }

    @FXML
    protected void userLogIn() throws IOException {
        Main m = new Main();
        String user = prompTextUsername.getText();
        String mdp = prompTextPassword.getText();
        try{
            Connection coBaseImmobilier = DriverManager.getConnection("jdbc:mysql://172.19.0.44/Immobilier", "agentimmobilier", "0550002D");
            String requete = "SELECT mdp FROM Utilisateur WHERE mail = ? ";
            PreparedStatement stmtSelect = coBaseImmobilier.prepareStatement(requete);
            stmtSelect.setString(1, user);
            ResultSet res = stmtSelect.executeQuery();

            /*
            Test hash avec SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] data = user.getBytes();
            byte[] hashData = messageDigest.digest(data);
            */




            String salt = BCrypt.gensalt();
            String mdpHash = BCrypt.hashpw(mdp, salt);



            while (res.next()){
                String leMdp = res.getString("utilisateur.mdp");
                String mdpBddHash = BCrypt.hashpw(leMdp, salt);

                boolean verif = BCrypt.checkpw(mdpHash, mdpBddHash);
                if (verif){
                    Stage newWindow = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(AgenceImmo.class.getResource("principal.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
                    newWindow.setScene(scene);
                    // Specifies the modality for new window.
                    newWindow.initModality(Modality.APPLICATION_MODAL);
                    newWindow.show();
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
