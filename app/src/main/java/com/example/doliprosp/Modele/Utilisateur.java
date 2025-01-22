package com.example.doliprosp.Modele;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;

    private String url;
    private String nomUtilisateur;
    private String motDePasse;
    private String cleAPI;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;
    private int codePostal;
    private String ville;
    private String numTelephone;

    public Utilisateur(String url, String nomUtilisateur, String motDePasse, String apiKey)
    {
        this.url = url;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cleAPI = apiKey;

    }

    public boolean informationutilisateurDejaRecupere()
    {
        return mail != null && adresse != null && codePostal != 0 && ville != null && numTelephone != null
                && nom != null && prenom != null;
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

    public String getNomUtilisateur()
    {
        return this.nomUtilisateur;
    }

    public String getMotDePasse()
    {
        return this.motDePasse;
    }

    public String getCleApi()
    {
        return this.cleAPI;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setNomUtilisateur(String newUserName)
    {
        this.nomUtilisateur = newUserName;
    }

    public void setMotDePasse(String nouveauMotDePasse) {
        this.motDePasse = nouveauMotDePasse;
    }

    public void setApiKey(String cleAPI)
    {
        this.cleAPI = cleAPI;
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
