package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.Modele.Projet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MesProjetsViewModel extends ViewModel {
    private ArrayList<Projet> projetListe = new ArrayList<>();
    private SharedPreferences sharedPreferences;

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

    private void enregistrerProjet() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(projetListe);
        editor.putString("mes_projets_list", json);
        editor.apply();
    }

    public void chargementProjet() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("mes_projets_list", null);
        Type type = new TypeToken<ArrayList<Projet>>() {
        }.getType();
        if (json != null) {
            projetListe = gson.fromJson(json, type);
        }
    }
}
