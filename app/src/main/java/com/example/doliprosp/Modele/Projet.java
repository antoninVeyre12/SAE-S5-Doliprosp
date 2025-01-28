package com.example.doliprosp.Modele;

import java.util.Date;
import java.util.UUID;

public class Projet {

    private UUID idProjet;
    private String nomProspect;
    private String titre;
    private String description;
    private String dateDebut;
    private String dateFin;


    public Projet(String nomProspect, String titre, String description, String dateDebut, String dateFin) {
        this.idProjet = UUID.randomUUID();
        this.nomProspect = nomProspect;
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

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }
}
