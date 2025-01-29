package com.example.doliprosp.viewModel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;

import com.example.doliprosp.Modele.Prospect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ViewModel pour gérer la liste des prospects dans l'application.
 * Ce ViewModel est basé sur AndroidViewModel, permettant d'avoir accès à l'Application pour un cycle de vie plus long.
 * Il permet d'ajouter, de supprimer et de charger des prospects à partir des SharedPreferences.
 */
public class ProspectViewModel extends AndroidViewModel {

    // Liste des prospects gérée dans le ViewModel
    private ArrayList<Prospect> prospectListe = new ArrayList<>();

    // Référence à l'objet SharedPreferences pour la persistance des données
    private SharedPreferences sharedPreferences;

    /**
     * Constructeur de la classe, initialisant le ViewModel avec l'application.
     * Cela permet à ce ViewModel d'être lié au cycle de vie de l'application et d'avoir un accès à l'application.
     *
     * @param application L'application associée.
     */
    public ProspectViewModel(Application application) {
        super(application);
    }

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
     * Vide la liste des prospects et met à jour les SharedPreferences en conséquence.
     */
    public void clear() {
        prospectListe.clear();
        enregistrerProspect(); // Sauvegarde la liste vide des prospects.
    }

    /**
     * Enregistre la liste des prospects dans les SharedPreferences sous forme de chaîne JSON.
     */
    private void enregistrerProspect() {
        // Création d'un éditeur pour modifier les SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Sérialisation de la liste des prospects en JSON
        Gson gson = new Gson();
        String json = gson.toJson(prospectListe);

        // Sauvegarde du JSON dans SharedPreferences avec la clé "prospect_liste"
        editor.putString("prospect_liste", json);
        editor.apply(); // Applique les changements de manière asynchrone
    }

    /**
     * Charge la liste des prospects depuis SharedPreferences et la désérialise.
     * Les prospects sont récupérés sous forme de JSON puis convertis en objets de type ArrayList<Prospect>.
     */
    public void chargementProspect() {
        // Création d'une instance de Gson pour la désérialisation
        Gson gson = new Gson();

        // Récupération de la chaîne JSON des SharedPreferences
        String json = sharedPreferences.getString("prospect_liste", null);

        // Définition du type générique pour la désérialisation
        Type type = new TypeToken<ArrayList<Prospect>>() {
        }.getType();

        // Si le JSON existe, on désérialise la chaîne en une liste de prospects
        if (json != null) {
            prospectListe = gson.fromJson(json, type);
        }
    }
}