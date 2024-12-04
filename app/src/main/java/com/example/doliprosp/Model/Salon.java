package com.example.doliprosp.Model;

import android.content.Context;

import com.example.doliprosp.Service.SalonService;

import java.util.ArrayList;
import java.util.UUID;

public class Salon {
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
