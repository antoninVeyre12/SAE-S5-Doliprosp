package com.example.doliprosp.Interface;

import com.example.doliprosp.Services.ProjetService;

import java.util.UUID;

public interface IProjet {

    public void ajouterProjet(ProjetService projet);
    public void supprimerProjet(ProjetService projet);
    public void envoyerProjet(ProjetService projet);

    public void updateProjet(String titre, String description);

    public UUID getIdProjet();

    public UUID getIdProspect();

    public String getTitre();

    public String getDescription();

    public void setTitre(String titre);

    public void setDescription(String description);

}
