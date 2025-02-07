package com.example.doliprosp.Modele;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Représente un prospect avec ses informations.
 */
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

    /**
     * Constructeur de la classe Prospect.
     *
     * @param nomSalon        Le nom du salon où le prospect a été rencontré.
     * @param nom             Le nom du prospect.
     * @param codePostal      Le code postal du prospect.
     * @param ville           La ville du prospect.
     * @param adressePostale  L'adresse postale du prospect.
     * @param mail            L'adresse e-mail du prospect.
     * @param numeroTelephone Le numéro de téléphone du prospect.
     * @param estClient       Indique si le prospect est un client ("oui" ou "non").
     * @param image           Une image associée au prospect (URL ou chemin).
     */
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

    /**
     * Retourne l'identifiant unique du prospect.
     *
     * @return L'UUID du prospect.
     */
    public UUID getIdProspect() {
        return idProspect;
    }

    /**
     * Définit un nouvel identifiant unique pour le prospect.
     *
     * @param idProspect L'UUID du prospect.
     */
    public void setIdProspect(UUID idProspect) {
        this.idProspect = idProspect;
    }

    /**
     * Retourne le nom du salon associé au prospect.
     *
     * @return Le nom du salon.
     */
    public String getNomSalon() {
        return nomSalon;
    }

    /**
     * Définit le nom du salon associé au prospect.
     *
     * @param nomSalon Le nom du salon.
     */
    public void setNomSalon(String nomSalon) {
        this.nomSalon = nomSalon;
    }

    /**
     * Retourne le nom du prospect.
     *
     * @return Le nom du prospect.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du prospect.
     *
     * @param nom Le nom du prospect.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le code postal du prospect.
     *
     * @return Le code postal du prospect.
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal du prospect.
     *
     * @param codePostal Le code postal du prospect.
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Retourne la ville du prospect.
     *
     * @return La ville du prospect.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Définit la ville du prospect.
     *
     * @param ville La ville du prospect.
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Retourne l'adresse du prospect.
     *
     * @return L'adresse du prospect.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse du prospect.
     *
     * @param adresse L'adresse du prospect.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Retourne l'adresse e-mail du prospect.
     *
     * @return L'adresse e-mail du prospect.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Définit l'adresse e-mail du prospect.
     *
     * @param mail L'adresse e-mail du prospect.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Retourne le numéro de téléphone du prospect.
     *
     * @return Le numéro de téléphone du prospect.
     */
    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    /**
     * Définit le numéro de téléphone du prospect.
     *
     * @param numeroTelephone Le numéro de téléphone du prospect.
     */
    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    /**
     * Indique si le prospect est un client.
     *
     * @return "oui" si c'est un client, sinon "non".
     */
    public String getEstClient() {
        return estClient;
    }

    /**
     * Définit si le prospect est un client.
     *
     * @param estClient "oui" si c'est un client, sinon "non".
     */
    public void setEstClient(String estClient) {
        this.estClient = estClient;
    }

    /**
     * Retourne l'image associée au prospect.
     *
     * @return L'URL ou le chemin de l'image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Définit l'image associée au prospect.
     *
     * @param image L'URL ou le chemin de l'image.
     */
    public void setImage(String image) {
        this.image = image;
    }


    /**
     * Compare l'objet actuel avec un autre objet pour vérifier s'ils sont égaux.
     * Deux objets sont considérés égaux si tous leurs champs sont identiques.
     *
     * @param obj L'objet à comparer avec l'objet actuel.
     * @return true si les objets sont égaux, sinon false.
     */
//    @Override
//    public boolean equals(Object obj) {
//        // Vérifier si les deux objets sont identiques (même référence)
//        if (this == obj) {
//            return true;
//        }
//
//        // Vérifier si l'objet est une instance de Prospect
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//
//        // Convertir l'objet en Prospect
//        Prospect other = (Prospect) obj;
//
//        return other.nomSalon.equals(nomSalon) && other.nom.equals(nom) && other.codePostal == codePostal
//                && other.ville.equals(ville) && other.adresse.equals(adresse) && other.mail.equals(mail)
//                && other.numeroTelephone.equals(numeroTelephone) && other.estClient.equals(estClient)
//                && other.image.equals(image);
//
////        if (nomSalon == null || nom == null || codePostal == 0 || ville == null || adresse == null
////                || mail == null || numeroTelephone == null || estClient == null || image == null) {
////            return false;
////        }
//
//    }
    @Override
    public boolean equals(Object obj) {
        // Vérifier si les deux objets sont identiques (même référence)
        if (this == obj) {
            return true;
        }

        // Vérifier si l'objet comparé est de type Prospect
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Convertir l'objet en un prospect
        Prospect other = (Prospect) obj;

        // Comparer tous les champs un par un
        return codePostal == other.codePostal &&
                Objects.equals(nom, other.nom) &&
                Objects.equals(ville, other.ville) &&
                Objects.equals(adresse, other.adresse) &&
                Objects.equals(mail, other.mail) &&
                Objects.equals(numeroTelephone, other.numeroTelephone);
    }

}