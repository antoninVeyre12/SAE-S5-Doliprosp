package com.example.doliprosp.modeles;

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
    private String idDolibarr;
    private long heureSaisieTimestamp;
    private boolean modifier;

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
     */
    public Prospect(String nomSalon, String nom, String codePostal,
                    String ville, String adressePostale, String mail, String numeroTelephone,
                    String estClient, String idDolibarr, long heureSaisieTimestamp) {
        this.nomSalon = nomSalon;
        this.nom = nom;
        this.codePostal = codePostal;
        this.ville = ville;
        this.adresse = adressePostale;
        this.mail = mail;
        this.numeroTelephone = numeroTelephone;
        this.estClient = estClient;
        this.idDolibarr = idDolibarr;
        this.heureSaisieTimestamp = heureSaisieTimestamp;
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
     * Indique si le prospect est un client.
     *
     * @return "oui" si c'est un client, sinon "non".
     */
    public String getIdDolibarr() {
        return idDolibarr;
    }

    /**
     * Défini si le prospect est un client.
     *
     * @param idDolibarr "oui" si c'est un client, sinon "non".
     */
    public void setIdDolibarr(String idDolibarr) {
        this.idDolibarr = idDolibarr;
    }


    public long getHeureSaisieTimestamp() {
        return heureSaisieTimestamp;
    }

    public void heureSaisieTimestamp(long heureSaisieTimestamp) {
        this.heureSaisieTimestamp = heureSaisieTimestamp;
    }

    public boolean getModifier() {
        return modifier;
    }

    public void setModifier(boolean modifier) {
        this.modifier = modifier;
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

        return Objects.equals(codePostal, other.codePostal) &&
                Objects.equals(nom, other.nom) &&
                Objects.equals(ville, other.ville) &&
                Objects.equals(adresse, other.adresse) &&
                Objects.equals(mail, other.mail) &&
                Objects.equals(numeroTelephone, other.numeroTelephone);
    }

    /**
     * Génère un code de hachage pour l'objet {@link Prospect}.
     *
     * Cette méthode retourne un entier représentant l'état de l'objet basé sur ses attributs.
     * Le code de hachage est calculé à l'aide des attributs suivants : {@code codePostal},
     * {@code nom}, {@code ville}, {@code adresse}, {@code mail}, et {@code numeroTelephone}
     * @return Un entier représentant le code de hachage de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codePostal, nom, ville, adresse, mail, numeroTelephone);
    }

    /**
     * Comparator pour comparer les prospects par leur nom.
     */
    public static final Comparator<Prospect> COMPARE_NOM = (prospect1, prospect2) ->
            prospect1.getNom().compareTo(prospect2.getNom());

    /**
     * Comparator pour comparer les prospects par leur mail.
     */
    public static final Comparator<Prospect> COMPARE_MAIL = (prospect1, prospect2) ->
            prospect1.getMail().compareTo(prospect2.getMail());

    /**
     * Comparator pour comparer les prospects par leur numéro de téléphone.
     */
    public static final Comparator<Prospect> COMPARE_TELEPHONE= (prospect1, prospect2) ->
            prospect1.getNumeroTelephone().compareTo(prospect2.getNumeroTelephone());

    /**
     * Comparator pour comparer les prospects par leur adresse.
     */
    public static final Comparator<Prospect> COMPARE_ADRESSE = (prospect1, prospect2) ->
            prospect1.getAdresse().compareTo(prospect2.getAdresse());

    /**
     * Comparator pour comparer les prospects par leur code postal.
     */
    public static final Comparator<Prospect> COMPARE_CODE_POSTAL = (prospect1, prospect2) ->
            prospect1.getCodePostal().compareTo(prospect2.getCodePostal());

    /**
     * Comparator pour comparer les prospects par leur ville.
     */
    public static final Comparator<Prospect> COMPARE_VILLE = (prospect1, prospect2) ->
            prospect1.getVille().compareTo(prospect2.getVille());
}