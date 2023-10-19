package com.example.agenceimmo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    private static String URL = "jdbc:mysql://172.19.0.44:3306/Immobilier" ;
    private static String LOGIN = "agentimmobilier";
    private static String MDP = "0550002D";

    public static String getUrl() {
        return URL;
    }

    public static String getLOGIN() {
        return LOGIN;
    }

    public static String getMDP() {
        return MDP;
    }

    public static Connection getConnexion(){
        Connection co = null;
        try{
            co = DriverManager.getConnection(Connexion.getUrl(), Connexion.getLOGIN(), Connexion.getMDP());
        }catch(Exception e){

        }
        return co;
    }


}