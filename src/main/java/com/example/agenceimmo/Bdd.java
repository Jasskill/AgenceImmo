package com.example.agenceimmo;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bdd {
    private String nomBase = "Immobilier";
    private String user = "dev";
    private String mdp = "0550002D";


        public Connection getConnection() throws SQLException{
            String url = "jdbc:mysql://172.19.0.44:3306/"+nomBase;
            return DriverManager.getConnection(url, user, mdp);
        }


        /*
        public void changeScene (ActionEvent event,){

        }

         */






    }

