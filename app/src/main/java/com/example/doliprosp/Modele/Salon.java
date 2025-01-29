package com.example.doliprosp.Modele;

import java.io.Serializable;

/**
 * Représente un salon avec son nom.
 */
public class Salon implements Serializable {
    private String nom;

    /**
     * Constructeur de la classe Salon.
     * @param nom Le nom du salon.
     */
    public Salon(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le nom du salon.
     * @return Le nom du salon.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Définit le nom du salon.
     * @param nom Le nouveau nom du salon.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}