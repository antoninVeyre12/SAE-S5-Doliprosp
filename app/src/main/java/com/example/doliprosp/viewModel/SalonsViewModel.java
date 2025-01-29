package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;

import com.example.doliprosp.Modele.Salon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel pour gérer la liste des salons dans l'application.
 * Ce ViewModel utilise SharedPreferences pour la persistance des salons.
 */
public class SalonsViewModel extends ViewModel {

    // Liste des salons gérée par le ViewModel
    private ArrayList<Salon> salonListe = new ArrayList<>();

    // Référence à SharedPreferences pour stocker et récupérer les données persistantes
    private SharedPreferences sharedPreferences;

    /**
     * Retourne la liste actuelle des salons.
     *
     * @return La liste des salons.
     */
    public ArrayList<Salon> getSalonListe() {
        return salonListe;
    }

    /**
     * Ajoute un salon à la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param salon Le salon à ajouter.
     */
    public void addSalon(Salon salon) {
        salonListe.add(salon);
        enregistrerSalons(); // Sauvegarde la liste mise à jour.
    }

    /**
     * Initialise les SharedPreferences à utiliser pour la persistance des données.
     *
     * @param sharedPreferences L'objet SharedPreferences.
     */
    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Supprime un salon de la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param salon Le salon à supprimer.
     */
    public void removeSalon(Salon salon) {
        salonListe.remove(salon);
        enregistrerSalons(); // Sauvegarde la liste mise à jour après suppression du salon.
    }

    /**
     * Vide la liste des salons et met à jour les SharedPreferences en conséquence.
     */
    public void clear() {
        salonListe.clear();
        enregistrerSalons(); // Sauvegarde la liste vide des salons.
    }

    /**
     * Enregistre la liste des salons dans SharedPreferences sous forme de chaîne JSON.
     */
    private void enregistrerSalons() {
        // Création d'un éditeur pour modifier les SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Sérialisation de la liste des salons en JSON
        Gson gson = new Gson();
        String json = gson.toJson(salonListe);

        // Sauvegarde du JSON dans SharedPreferences avec la clé "salon_liste"
        editor.putString("salon_liste", json);
        editor.apply(); // Applique les changements de manière asynchrone
    }

    /**
     * Charge la liste des salons depuis SharedPreferences et la désérialise.
     * Les salons sont récupérés sous forme de JSON puis convertis en objets de type ArrayList<Salon>.
     */
    public void chargementSalons() {
        // Création d'une instance de Gson pour la désérialisation
        Gson gson = new Gson();

        // Récupération de la chaîne JSON des SharedPreferences
        String json = sharedPreferences.getString("salon_liste", null);

        // Définition du type générique pour la désérialisation
        Type type = new TypeToken<ArrayList<Salon>>() {
        }.getType();

        // Si le JSON existe, on désérialise la chaîne en une liste de salons
        if (json != null) {
            salonListe = gson.fromJson(json, type);
        }
    }
}