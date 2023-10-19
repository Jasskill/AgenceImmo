package com.example.agenceimmo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
    private String URL = "jdbc:mysql://172.19.0.44/Immobilier";
    private String LOGIN = "agentImmoblier";
    private String MDP = "0550002D";

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

    public Connexion(String unurl, String unlogin, String unmdp) {
        this.URL = unurl;
        this.LOGIN = unlogin;
        this.MDP = unmdp;

    }


    public Connection getConnection() throws SQLException {

        Connection coDbImmo = DriverManager.getConnection(URL,LOGIN,MDP);
        Statement statement = coDbImmo.createStatement();
        return coDbImmo;
    }



}