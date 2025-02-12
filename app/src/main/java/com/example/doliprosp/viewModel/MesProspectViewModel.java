package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.Modele.Prospect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ViewModel pour gérer la liste des prospects.
 * Assure la persistance et la récupération des données à l'aide de
 * SharedPreferences.
 */
public class MesProspectViewModel extends ViewModel {

    private ArrayList<Prospect> prospectListe = new ArrayList<>();
    private final Gson gson = new Gson();
    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY = "mes_prospect_list";

    /**
     * Retourne la liste des prospects.
     *
     * @return liste des prospects
     */
    public ArrayList<Prospect> getProspectListe() {
        return prospectListe;
    }

    /**
     * Ajoute un prospect à la liste et met à jour les SharedPreferences.
     *
     * @param prospect le prospect à ajouter
     */
    public void addProspect(Prospect prospect) {
        prospectListe.add(prospect);
        enregistrerProspect();
    }

    /**
     * Initialise les SharedPreferences pour la persistance des données.
     *
     * @param sharedPreferences instance de SharedPreferences
     */
    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Supprime un prospect de la liste et met à jour les SharedPreferences.
     *
     * @param prospect le prospect à supprimer
     */
    public void enleverProspect(Prospect prospect) {
        prospectListe.remove(prospect);
        enregistrerProspect();
    }

    /**
     * Enregistre la liste des prospects dans les SharedPreferences sous
     * forme de JSON.
     */
    private void enregistrerProspect() {
        SharedPreferences.Editor editeur = sharedPreferences.edit();
        editeur.putString(PREF_KEY, gson.toJson(prospectListe));
        editeur.apply();
    }

    /**
     * Charge la liste des prospects depuis les SharedPreferences.
     * Désérialise le JSON stocké en une liste d'objets Prospect.
     */
    public void chargementProspect() {
        String json = sharedPreferences.getString(PREF_KEY, null);
        Type type = new TypeToken<ArrayList<Prospect>>() {
        }.getType();
        if (json != null) {
            prospectListe = gson.fromJson(json, type);
        }
    }
}
