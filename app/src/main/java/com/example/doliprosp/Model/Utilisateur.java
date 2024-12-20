package com.example.doliprosp.Model;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;

    private String url;
    private String userName;
    private String motDePasse;
    private String apiKey;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;
    private int codePostal;
    private String ville;
    private String numTelephone;

    public Utilisateur(String url, String userName, String motDePasse, String apiKey)
    {
        this.url = url;
        this.userName = userName;
        this.motDePasse = motDePasse;
        this.apiKey = apiKey;

    }

    public boolean informationutilisateurDejaRecupere()
    {
        return mail != null;
    }


    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getMotDePasse()
    {
        return this.motDePasse;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setUrl(String newUrl)
    {
        this.url = newUrl;
    }

    public void setUserName(String newUserName)
    {
        this.userName = newUserName;
    }

    public void setMotDePasse(String nouveauMotDePasse) {
        this.motDePasse = nouveauMotDePasse;
    }

    public void setApiKey(String newApiKey)
    {
        this.apiKey = newApiKey;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
