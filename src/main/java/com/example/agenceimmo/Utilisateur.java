package com.example.agenceimmo;

/**
 * Classe utilisateur
 * créée le 4/10/2023
 */
public class Utilisateur {
    private int id;
    private String mdp;
    private String nom;
    private String prenom;
    private String mail;

    public int getid(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMdp(){
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }


    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom(){
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail(){
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public Utilisateur(int unId, String unMdp, String unNom, String unPrenom, String unMail){
        this.id = unId;
        this.mdp = unMdp;
        this.nom = unNom;
        this.prenom = unPrenom;
        this.mail = unMail;

    }
}
