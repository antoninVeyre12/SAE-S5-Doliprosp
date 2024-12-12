package com.example.doliprosp.Model;

import java.util.UUID;

public class Prospect {
    private UUID idProspect;
    private UUID idSalon;
    private String prenom;
    private String nom;
    private int codePostal;
    private String ville;
    private String adresse;
    private String mail;
    private String numeroTelephone;
    private Boolean estClient;


    public Prospect(UUID idShow, String firstName, String lastName, int postCode,
                    String city, String postalAddress, String email, String phoneNumber,
                    Boolean isClient) {
        this.idProspect = UUID.randomUUID();
        this.idSalon = idShow;
        this.prenom = firstName;
        this.nom = lastName;
        this.codePostal = postCode;
        this.ville = city;
        this.adresse = postalAddress;
        this.mail = email;
        this.numeroTelephone = phoneNumber;
        this.estClient = isClient;
    }

    public UUID getIdProspect() {
        return idProspect;
    }

    public void setIdProspect(UUID idProspect) {
        this.idProspect = idProspect;
    }

    public UUID getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(UUID idSalon) {
        this.idSalon = idSalon;
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

    public Boolean getEstClient() {
        return estClient;
    }

    public void setEstClient(Boolean estClient) {
        this.estClient = estClient;
    }
}

