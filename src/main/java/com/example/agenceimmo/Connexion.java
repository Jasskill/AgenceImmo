package com.example.agenceimmo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    private static String URL = "jdbc:mysql://172.19.0.44:3306/Immobilier" ;
    private static String LOGIN = "agentimmobilier";
    private static String MDP = "0550002D";

    public String getUrl() {
        return this.URL;
    }

    public void setURL() {
        this.URL = URL;
    }

    public String getLOGIN() {
        return this.LOGIN;
    }

    public void setLOGIN() {
        this.LOGIN = LOGIN;
    }

    public String getMDP() {
        return this.MDP;
    }

    public void setMDP() {
        this.MDP = MDP;
    }
    public static void getConnexion(){
        return DriverManager.getConnection(this.getUrl(), this.getLOGIN(), this.getMDP());
    }


}