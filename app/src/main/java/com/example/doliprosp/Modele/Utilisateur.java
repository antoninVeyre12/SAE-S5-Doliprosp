package com.example.doliprosp.Modele;

import java.io.Serializable;

/**
 * Représente un utilisateur avec ses informations personnelles et ses identifiants de connexion.
 */
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

    /**
     * Constructeur de la classe Utilisateur.
     *
     * @param url L'URL de l'utilisateur.
     * @param nomUtilisateur Le nom d'utilisateur pour la connexion.
     * @param motDePasse Le mot de passe pour la connexion.
     * @param apiKey La clé API associée à l'utilisateur.
     */
    public Utilisateur(String url, String nomUtilisateur, String motDePasse, String apiKey) {
        this.url = url;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cleAPI = apiKey;
    }

    /**
     * Vérifie si toutes les informations personnelles de l'utilisateur ont été récupérées.
     *
     * @return true si les informations personnelles sont complètes, false sinon.
     */
    public boolean informationutilisateurDejaRecupere() {
        return mail != null && adresse != null && codePostal != 0 && ville != null && numTelephone != null
                && nom != null && prenom != null;
    }

    // Getters et Setters

    /**
     * Retourne la ville de l'utilisateur.
     *
     * @return La ville de l'utilisateur.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Définit la ville de l'utilisateur.
     *
     * @param ville La ville de l'utilisateur.
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Retourne le code postal de l'utilisateur.
     *
     * @return Le code postal de l'utilisateur.
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal de l'utilisateur.
     *
     * @param codePostal Le code postal de l'utilisateur.
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Retourne l'adresse de l'utilisateur.
     *
     * @return L'adresse de l'utilisateur.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse de l'utilisateur.
     *
     * @param adresse L'adresse de l'utilisateur.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Retourne l'adresse e-mail de l'utilisateur.
     *
     * @return L'adresse e-mail de l'utilisateur.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur.
     *
     * @param mail L'adresse e-mail de l'utilisateur.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Retourne le numéro de téléphone de l'utilisateur.
     *
     * @return Le numéro de téléphone de l'utilisateur.
     */
    public String getNumTelephone() {
        return numTelephone;
    }

    /**
     * Définit le numéro de téléphone de l'utilisateur.
     *
     * @param numTelephone Le numéro de téléphone de l'utilisateur.
     */
    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    /**
     * Retourne l'URL de l'utilisateur.
     *
     * @return L'URL de l'utilisateur.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Retourne le nom d'utilisateur pour la connexion.
     *
     * @return Le nom d'utilisateur.
     */
    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    /**
     * Retourne le mot de passe pour la connexion.
     *
     * @return Le mot de passe.
     */
    public String getMotDePasse() {
        return this.motDePasse;
    }

    /**
     * Retourne la clé API associée à l'utilisateur.
     *
     * @return La clé API.
     */
    public String getCleApi() {
        return this.cleAPI;
    }

    /**
     * Définit l'URL de l'utilisateur.
     *
     * @param url L'URL de l'utilisateur.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Définit le nom d'utilisateur pour la connexion.
     *
     * @param newUserName Le nouveau nom d'utilisateur.
     */
    public void setNomUtilisateur(String newUserName) {
        this.nomUtilisateur = newUserName;
    }

    /**
     * Définit le mot de passe pour la connexion.
     *
     * @param nouveauMotDePasse Le nouveau mot de passe.
     */
    public void setMotDePasse(String nouveauMotDePasse) {
        this.motDePasse = nouveauMotDePasse;
    }

    /**
     * Définit la clé API de l'utilisateur.
     *
     * @param cleAPI La nouvelle clé API.
     */
    public void setApiKey(String cleAPI) {
        this.cleAPI = cleAPI;
    }

    /**
     * Retourne le prénom de l'utilisateur.
     *
     * @return Le prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     *
     * @param prenom Le prénom de l'utilisateur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}