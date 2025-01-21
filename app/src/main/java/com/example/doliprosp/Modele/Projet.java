package com.example.doliprosp.Modele;

import java.util.UUID;

public class Projet {

    private UUID idProjet;
    private UUID idProspect;
    private String titre;
    private String description;

    public Projet(UUID idProspect, String titre, String description)
    {
        this.idProjet = UUID.randomUUID();
        this.idProspect = idProspect;
        this.titre = titre;
        this.description = description;
    }

    public UUID getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(UUID idProjet) {
        this.idProjet = idProjet;
    }

    public UUID getIdProspect() {
        return idProspect;
    }

    public void setIdProspect(UUID idProspect) {
        this.idProspect = idProspect;
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
