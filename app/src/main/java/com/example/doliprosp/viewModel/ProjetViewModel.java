package com.example.doliprosp.viewModel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Prospect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ViewModel pour gérer la liste des projets dans l'application.
 * Ce ViewModel est basé sur AndroidViewModel, permettant d'avoir accès à l'Application pour un cycle de vie plus long.
 * Il permet d'ajouter, de supprimer et de charger des projets à partir des SharedPreferences.
 */
public class ProjetViewModel extends AndroidViewModel {

    // Liste des projets gérée dans le ViewModel
    private ArrayList<Projet> projetListe = new ArrayList<>();

    // Référence à l'objet SharedPreferences pour la persistance des données
    private SharedPreferences sharedPreferences;

    /**
     * Constructeur de la classe, initialisant le ViewModel avec l'application.
     * Cela permet à ce ViewModel d'être lié au cycle de vie de l'application et d'avoir un accès à l'application.
     *
     * @param application L'application associée.
     */
    public ProjetViewModel(Application application) {
        super(application);
    }

    /**
     * Retourne la liste des projets.
     *
     * @return La liste des projets.
     */
    public ArrayList<Projet> getProjetListe() {
        return projetListe;
    }

    /**
     * Ajoute un projet à la liste et enregistre la liste mise à jour dans SharedPreferences.
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
     * Vide la liste des projets et met à jour les SharedPreferences en conséquence.
     */
    public void clear() {
        projetListe.clear();
        enregistrerProjet(); // Sauvegarde la liste vide des projets.
    }

    /**
     * Enregistre la liste des projets dans les SharedPreferences sous forme de chaîne JSON.
     */
    private void enregistrerProjet() {
        // Création d'un éditeur pour modifier les SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Sérialisation de la liste des projets en JSON
        Gson gson = new Gson();
        String json = gson.toJson(projetListe);

        // Sauvegarde du JSON dans SharedPreferences avec la clé "projet_liste"
        editor.putString("projet_liste", json);
        editor.apply(); // Applique les changements de manière asynchrone
    }

    /**
     * Charge la liste des projets depuis SharedPreferences et la désérialise.
     * Les projets sont récupérés sous forme de JSON puis convertis en objets de type ArrayList<Projet>.
     */
    public void chargementProspect() {
        // Création d'une instance de Gson pour la désérialisation
        Gson gson = new Gson();

        // Récupération de la chaîne JSON des SharedPreferences
        String json = sharedPreferences.getString("projet_liste", null);

        // Définition du type générique pour la désérialisation
        Type type = new TypeToken<ArrayList<Prospect>>() {
        }.getType();

        // Si le JSON existe, on désérialise la chaîne en une liste de projets
        if (json != null) {
            projetListe = gson.fromJson(json, type);
        }
    }
}