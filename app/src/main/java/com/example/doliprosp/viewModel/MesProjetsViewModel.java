package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.Modele.Projet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ViewModel permettant de gérer la liste des projets dans l'application.
 * Cette classe contient les méthodes pour ajouter, supprimer et charger des projets à partir des SharedPreferences.
 * Elle est responsable de la gestion des données liées aux projets, de leur persistance et de leur chargement.
 */
public class MesProjetsViewModel extends ViewModel {
    // Liste des projets gérée dans le ViewModel
    private ArrayList<Projet> projetListe = new ArrayList<>();
    private static final String PREF_KEY = "mes_projets_list";
    // Référence à l'objet SharedPreferences pour la persistance des données
    private SharedPreferences sharedPreferences;

    private final Gson gson = new Gson();

    /**
     * Retourne la liste des projets.
     *
     * @return La liste des projets.
     */
    public ArrayList<Projet> getProjetListe() {
        return projetListe;
    }

    /**
     * Ajoute un projet à la liste et enregistre la liste dans SharedPreferences.
     *
     * @param projet Le projet à ajouter.
     */
    public void addProjet(Projet projet) {
        projetListe.add(projet);
        enregistrerProjet(); // Sauvegarde la liste mise à jour des projets.
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
     * Supprime un projet de la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param projet Le projet à supprimer.
     */
    public void removeProjet(Projet projet) {
        projetListe.remove(projet);
        enregistrerProjet(); // Sauvegarde la liste mise à jour après suppression du projet.
    }

    /**
     * Modifie un projet de la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param projet Le projet à modifier.
     */
    public void updateProjet(Projet projet) {
        projetListe.remove(projet);
        enregistrerProjet(); // Sauvegarde la liste mise à jour après suppression du projet.
    }

    /**
     * Enregistre la liste des projets dans les SharedPreferences sous forme de chaîne JSON.
     */
    private void enregistrerProjet() {
        // Création d'un éditeur pour modifier les SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Sauvegarde du JSON dans SharedPreferences avec la clé "mes_projets_list"
        editor.putString(PREF_KEY, gson.toJson(projetListe));
        editor.apply(); // Applique les changements de manière asynchrone
    }

    /**
     * Charge la liste des projets depuis SharedPreferences et la désérialise.
     * Les projets sont récupérés sous forme de JSON puis convertis en objets de type ArrayList<Projet>.
     */
    public void chargementProjet() {
        // Récupération de la chaîne JSON des SharedPreferences
        String json = sharedPreferences.getString(PREF_KEY, null);
        // Définition du type générique pour la désérialisation
        Type type = new TypeToken<ArrayList<Projet>>() {}.getType();

        // Si le JSON existe, on désérialise la chaîne en une liste de projets
        if (json != null) {
            projetListe = gson.fromJson(json, type);
        }
    }
}