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
 * Cette classe permet d'ajouter, supprimer et charger des salons à partir des SharedPreferences.
 * Elle assure la gestion des données liées aux salons, leur persistance et leur récupération.
 */
public class MesSalonsViewModel extends ViewModel {
    // Liste des salons gérée dans le ViewModel
    private ArrayList<Salon> salonListe = new ArrayList<>();
    // Référence à l'objet SharedPreferences pour la persistance des données
    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY = "mes_salon_list";
    private final Gson gson = new Gson();

    /**
     * Retourne la liste des salons.
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
        enregistrerSalons(); // Sauvegarde la liste mise à jour des salons.
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
     * Enregistre la liste des salons dans les SharedPreferences sous forme de chaîne JSON.
     */
    public void enregistrerSalons() {
        // Création d'un éditeur pour modifier les SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Sauvegarde du JSON dans SharedPreferences avec la clé "mes_salon_list"
        editor.putString(PREF_KEY, gson.toJson(salonListe));
        editor.apply(); // Applique les changements de manière asynchrone
    }

    /**
     * Charge la liste des salons depuis SharedPreferences et la désérialise.
     * Les salons sont récupérés sous forme de JSON puis convertis en objets de type ArrayList<Salon>.
     */
    public void chargementSalons() {
        // Récupération de la chaîne JSON des SharedPreferences
        String json = sharedPreferences.getString(PREF_KEY, null);

        // Définition du type générique pour la désérialisation
        Type type = new TypeToken<ArrayList<Salon>>() {
        }.getType();

        // Si le JSON existe, on désérialise la chaîne en une liste de salons
        if (json != null) {
            salonListe = gson.fromJson(json, type);
        }
    }
}