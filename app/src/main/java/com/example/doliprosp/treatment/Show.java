package com.example.doliprosp.treatment;

import java.util.UUID;

public class Show {

    private UUID idShow;
    private String name;

    public Show(String name)
    {
        this.idShow = UUID.randomUUID();
        this.name = name;
    }

    public void updateShow(String newName)
    {
        this.setName(newName);
    }

    public String getName()
    {
        return this.name;
    }

    public UUID getIdShow()
    {
        return this.idShow;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}