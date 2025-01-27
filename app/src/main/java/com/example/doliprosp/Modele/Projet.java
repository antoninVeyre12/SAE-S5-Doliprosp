package com.example.doliprosp.Modele;

import java.util.Date;
import java.util.UUID;

public class Projet {

    private UUID idProjet;
    private String titre;
    private String description;
    private Date dateDebut;
    private Date dateFin;


    public Projet(String titre, String description, Date dateDebut, Date dateFin) {
        this.idProjet = UUID.randomUUID();
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public UUID getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(UUID idProjet) {
        this.idProjet = idProjet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
}
