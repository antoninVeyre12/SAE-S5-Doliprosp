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

public class ProjetViewModel extends AndroidViewModel {

    private ArrayList<Projet> projetListe = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public ProjetViewModel(Application application) {
        super(application);
    }

    public ArrayList<Projet> getProjetListe() {
        return projetListe;
    }

    public void addProjet(Projet projet) {
        projetListe.add(projet);
        enregistrerProjet();
    }

    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void removeProjet(Projet projet) {
        projetListe.remove(projet);
        enregistrerProjet();
    }

    public void clear() {
        projetListe.clear();
        enregistrerProjet();
    }

    private void enregistrerProjet() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(projetListe);
        editor.putString("projet_liste", json);
        editor.apply();
    }

    public void chargementProspect() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("projet_liste", null);
        Type type = new TypeToken<ArrayList<Prospect>>() {}.getType();
        if (json != null) {
            projetListe = gson.fromJson(json, type);
        }
    }
}
