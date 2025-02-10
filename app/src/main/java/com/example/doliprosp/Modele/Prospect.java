package com.example.doliprosp.Modele;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Représente un prospect avec ses informations.
 */
public class Prospect implements Serializable {
    private String nomSalon;
    private String nom;
    private String codePostal;
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
    public Prospect(String nomSalon, String nom, String codePostal,
                    String ville, String adressePostale, String mail, String numeroTelephone,
                    String estClient, String image) {
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
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal du prospect.
     *
     * @param codePostal Le code postal du prospect.
     */
    public void setCodePostal(String codePostal) {
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Prospect other = (Prospect) obj;

        return codePostal == other.codePostal &&
                Objects.equals(nom, other.nom) &&
                Objects.equals(ville, other.ville) &&
                Objects.equals(adresse, other.adresse) &&
                Objects.equals(mail, other.mail) &&
                Objects.equals(numeroTelephone, other.numeroTelephone);
    }

    /**
     * Comparator pour comparer les prospects par leur nom.
     */
    public static Comparator<Prospect> compareNom = new Comparator<Prospect>() {
        /**
         * Compare deux prospects par leur nom.
         *
         * @param prospect1 le premier prospect à comparer
         * @param prospect2 le deuxième prospect à comparer
         * @return un entier négatif, nul ou positif si le nom de prospect1 est respectivement inférieur,
         * égal ou supérieur dans l'ordre alphabétique à celui de prospect2
         */
        @Override
        public int compare(Prospect prospect1, Prospect prospect2) {
            return prospect1.getNom().compareTo(prospect2.getNom());
        }
    };

    /**
     * Comparator pour comparer les prospects par leur mail.
     */
    public static Comparator<Prospect> compareMail = new Comparator<Prospect>() {
        /**
         * Compare deux prospects par leur mail.
         *
         * @param prospect1 le premier prospect à comparer
         * @param prospect2 le deuxième prospect à comparer
         * @return un entier négatif, nul ou positif si le mail du prospect1 est respectivement inférieur,
         * égal ou supérieur dans l'ordre alphabétique à celui du prospect2
         */
        @Override
        public int compare(Prospect prospect1, Prospect prospect2) {
            return prospect1.getMail().compareTo(prospect2.getMail());
        }
    };

    /**
     * Comparator pour comparer les prospects par leur numéro de téléphone.
     */
    public static Comparator<Prospect> compareTelephone = new Comparator<Prospect>() {
        /**
         * Compare deux prospects par leur numéro de téléphone.
         *
         * @param prospect1 le premier prospect à comparer
         * @param prospect2 le deuxième prospect à comparer
         * @return un entier négatif, nul ou positif si le numéro de téléphone du prospect1 est respectivement inférieur,
         * égal ou supérieur dans l'ordre alphabétique à celui du prospect2
         */
        @Override
        public int compare(Prospect prospect1, Prospect prospect2) {
            return prospect1.getNumeroTelephone().compareTo(prospect2.getNumeroTelephone());
        }
    };

    /**
     * Comparator pour comparer les prospects par leur adresse.
     */
    public static Comparator<Prospect> compareAdresse = new Comparator<Prospect>() {
        /**
         * Compare deux prospects par leur adresse.
         *
         * @param prospect1 le premier prospect à comparer
         * @param prospect2 le deuxième prospect à comparer
         * @return un entier négatif, nul ou positif si l'adresse du prospect1 est respectivement inférieur,
         * égal ou supérieur dans l'ordre alphabétique à celui du prospect2
         */
        @Override
        public int compare(Prospect prospect1, Prospect prospect2) {
            return prospect1.getAdresse().compareTo(prospect2.getAdresse());
        }
    };

    /**
     * Comparator pour comparer les prospects par leur code postal.
     */
    public static Comparator<Prospect> compareCodePostal = new Comparator<Prospect>() {
        /**
         * Compare deux prospects par leur code postal.
         *
         * @param prospect1 le premier prospect à comparer
         * @param prospect2 le deuxième prospect à comparer
         * @return un entier négatif, nul ou positif si le code postal du prospect1 est respectivement inférieur,
         * égal ou supérieur à celui du prospect2
         */
        @Override
        public int compare(Prospect prospect1, Prospect prospect2) {
            return Integer.compare(prospect1.getCodePostal(), prospect2.getCodePostal());
        }
    };


    /**
     * Comparator pour comparer les prospects par leur ville.
     */
    public static Comparator<Prospect> compareVille = new Comparator<Prospect>() {
        /**
         * Compare deux prospects par leur ville.
         *
         * @param prospect1 le premier prospect à comparer
         * @param prospect2 le deuxième prospect à comparer
         * @return un entier négatif, nul ou positif si le ville du prospect1 est respectivement inférieur,
         * égal ou supérieur dans l'ordre alphabétique à celui du prospect2
         */
        @Override
        public int compare(Prospect prospect1, Prospect prospect2) {
            return prospect1.getVille().compareTo(prospect2.getVille());
        }
    };


}