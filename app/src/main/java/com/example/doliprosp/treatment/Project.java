package com.example.doliprosp.treatment;

import java.util.UUID;

public class Project {

    private UUID idProject;
    private UUID idProspect;
    private String title;
    private String description;

    private IApplication applicationManager;

    public Project(UUID idProspect, String title, String description)
    {
        this.idProject = UUID.randomUUID();
        this.idProspect = idProspect;
        this.title = title;
        this.description = description;
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

    public UUID getIdProspect()
    {
        return this.idProspect;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.description;
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