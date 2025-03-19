package com.example.doliprosp.modeles;

/**
 * Représente un projet associé à un prospect.
 */
public class Projet {


    private String nomProspect;
    private String titre;
    private String description;
    private String dateDebut;
    private long dateTimestamp;

    /**
     * Constructeur de la classe Projet.
     *
     * @param nomProspect Le nom du prospect associé au projet.
     * @param titre       Le titre du projet.
     * @param description Une description du projet.
     * @param dateDebut   La date de début du projet (format String).
     */
    public Projet(String nomProspect, String titre, String description, String dateDebut, long dateTimestamp) {
        this.nomProspect = nomProspect;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateTimestamp = dateTimestamp;
    }

    /**
     * Retourne le titre du projet.
     *
     * @return Le titre du projet.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre du projet.
     *
     * @param titre Le titre du projet.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne la description du projet.
     *
     * @return La description du projet.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du projet.
     *
     * @param description La description du projet.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Retourne la date de début du projet.
     *
     * @return La date de début sous forme de String.
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date de début du projet.
     *
     * @param dateDebut La date de début sous forme de String.
     */
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Retourne la date de début du projet.
     *
     * @return La date de début sous forme de String.
     */
    public long getDateTimestamp() {
        return dateTimestamp;
    }

    /**
     * Définit la date de début du projet.
     *
     * @param dateDebut La date de début sous forme de String.
     */
    public void setDateTimestamp(long dateTimestamp) {
        this.dateTimestamp = dateTimestamp;
    }

    /**
     * Retourne le nom du prospect associé au projet.
     *
     * @return Le nom du prospect.
     */
    public String getNomProspect() {
        return nomProspect;
    }

    /**
     * Définit le nom du prospect associé au projet.
     *
     * @param nomProspect Le nom du prospect.
     */
    public void setNomProspect(String nomProspect) {
        this.nomProspect = nomProspect;
    }
}