package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;

import com.example.doliprosp.Modele.Prospect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel pour gérer la liste des prospects dans l'application.
 * Cette classe permet d'ajouter, supprimer et charger des prospects à partir des SharedPreferences.
 * Elle assure la gestion des données liées aux prospects, leur persistance et leur récupération.
 */
public class MesProspectViewModel extends ViewModel {
    // Liste des prospects gérée dans le ViewModel
    private ArrayList<Prospect> prospectListe = new ArrayList<>();
    private final Gson gson = new Gson();
    // Référence à l'objet SharedPreferences pour la persistance des données
    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY = "mes_prospect_list";

    /**
     * Retourne la liste des prospects.
     *
     * @return La liste des prospects.
     */
    public ArrayList<Prospect> getProspectListe() {
        return prospectListe;
    }

    /**
     * Ajoute un prospect à la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param prospect Le prospect à ajouter.
     */
    public void addProspect(Prospect prospect) {
        prospectListe.add(prospect);
        enregistrerProspect(); // Sauvegarde la liste mise à jour des prospects.
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
     * Supprime un prospect de la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param prospect Le prospect à supprimer.
     */
    public void removeProspect(Prospect prospect) {
        prospectListe.remove(prospect);
        enregistrerProspect(); // Sauvegarde la liste mise à jour après suppression du prospect.
    }

    /**
     * Enregistre la liste des prospects dans les SharedPreferences sous forme de chaîne JSON.
     */
    private void enregistrerProspect() {
        // Création d'un éditeur pour modifier les SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Sauvegarde du JSON dans SharedPreferences avec la clé "mes_prospect_list"
        editor.putString(PREF_KEY, gson.toJson(prospectListe));
        editor.apply(); // Applique les changements de manière asynchrone
    }

    /**
     * Charge la liste des prospects depuis SharedPreferences et la désérialise.
     * Les prospects sont récupérés sous forme de JSON puis convertis en objets de type ArrayList<Prospect>.
     */
    public void chargementProspect() {
        // Récupération de la chaîne JSON des SharedPreferences
        String json = sharedPreferences.getString(PREF_KEY, null);

        // Définition du type générique pour la désérialisation
        Type type = new TypeToken<ArrayList<Prospect>>() {
        }.getType();

        // Si le JSON existe, on désérialise la chaîne en une liste de prospects
        if (json != null) {
            prospectListe = gson.fromJson(json, type);
        }
    }
}