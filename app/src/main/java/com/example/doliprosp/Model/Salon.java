package com.example.doliprosp.Model;

import java.io.Serializable;
import java.util.UUID;

public class Salon implements Serializable {
    private UUID idSalon;
    private String nom;

    public Salon(String nom)
    {
        this.idSalon = UUID.randomUUID();
        this.nom = nom;
    }

    public String getNom()
    {
        return this.nom;
    }

    public UUID getIdSalon()
    {
        return this.idSalon;
    }

    public void setNom(String name)
    {
        this.nom = nom;
    }



}
