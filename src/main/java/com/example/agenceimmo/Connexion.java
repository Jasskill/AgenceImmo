package com.example.agenceimmo;
public class Connexion {
    private String URL;
    private String LOGIN;
    private String MDP;

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

}