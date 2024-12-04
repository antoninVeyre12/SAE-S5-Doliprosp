package com.example.doliprosp.Service;

import com.example.doliprosp.Interface.IProjet;

import java.util.UUID;

public class ProjetService implements IProjet {

    private UUID idProject;
    private UUID idProspect;
    private String title;
    private String description;

    public ProjetService(UUID idProspect, String title, String description)
    {
        this.idProject = UUID.randomUUID();
        this.idProspect = idProspect;
        this.title = title;
        this.description = description;
    }
    public ProjetService()
    {

    }

    public void updateProject(String newTitle, String newDescription)
    {
        this.setTitle(newTitle);
        this.setDescription(newDescription);
    }

    public UUID getIdProject()
    {
        return this.idProject;
    }

    @Override
    public void ajouterProjet(ProjetService projet) {

    }

    @Override
    public void supprimerProjet(ProjetService projet) {

    }

    @Override
    public void envoyerProjet(ProjetService projet) {

    }

    @Override
    public void updateProjet(String titre, String description) {

    }

    @Override
    public UUID getIdProjet() {
        return null;
    }

    public UUID getIdProspect()
    {
        return this.idProspect;
    }

    @Override
    public String getTitre() {
        return "";
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.description;
    }

    @Override
    public void setTitre(String titre) {

    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}