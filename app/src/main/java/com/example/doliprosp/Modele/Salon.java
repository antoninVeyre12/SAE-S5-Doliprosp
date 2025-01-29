package com.example.doliprosp.Modele;

import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

public class Salon implements Serializable {
    private String nom;
    public Salon(String nom)
    {
        this.nom = nom;
    }
    public String getNom()
    {
        return this.nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }
}
