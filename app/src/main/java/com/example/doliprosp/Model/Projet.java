package com.example.doliprosp.Model;

import com.example.doliprosp.Services.ProjetService;

import java.util.UUID;

public class Projet {

    private UUID idProjet;
    private UUID idProspet;
    private String titre;
    private String description;

    public Projet(UUID idProspect, String titre, String description)
    {
        this.idProjet = UUID.randomUUID();
        this.idProspet = idProspect;
        this.titre = titre;
        this.description = description;
    }

    public UUID getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(UUID idProjet) {
        this.idProjet = idProjet;
    }

    public UUID getIdProspet() {
        return idProspet;
    }

    public void setIdProspet(UUID idProspet) {
        this.idProspet = idProspet;
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
}
