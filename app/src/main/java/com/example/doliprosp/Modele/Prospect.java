package com.example.doliprosp.Modele;

import java.io.Serializable;
import java.util.UUID;

public class Prospect implements Serializable {
    private UUID idProspect;
    private String nomSalon;
    private String nom;
    private int codePostal;
    private String ville;
    private String adresse;
    private String mail;
    private String numeroTelephone;
    private String estClient;
    private String image;


    public Prospect(String nomSalon, String nom, int codePostal,
                    String ville, String adressePostale, String mail, String numeroTelephone,
                    String estClient, String image) {
        this.idProspect = UUID.randomUUID();
        this.nomSalon = nomSalon;
        this.nom = nom;
        this.codePostal = codePostal;
        this.ville = ville;
        this.adresse = adressePostale;
        this.mail = mail;
        this.numeroTelephone = numeroTelephone;
        this.estClient = estClient;
        this.image = image;
    }

    public UUID getIdProspect() {
        return idProspect;
    }

    public void setIdProspect(UUID idProspect) {
        this.idProspect = idProspect;
    }

    public String getNomSalon() {
        return nomSalon;
    }

    public void setNomSalon(String nomSalon) {
        this.nomSalon = nomSalon;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getEstClient() {
        return estClient;
    }

    public void setEstClient(String estClient) {
        this.estClient = estClient;
    }

    public String getImage(){return image;}

    public void setImage(String image){this.image = image;}
}

