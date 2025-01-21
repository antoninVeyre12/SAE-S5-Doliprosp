package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;

import com.example.doliprosp.Modele.Salon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class SalonsViewModel extends ViewModel {
    private ArrayList<Salon> salonListe = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public ArrayList<Salon> getSalonListe() {
        return salonListe;
    }

    public void addSalon(Salon salon) {
        salonListe.add(salon);
        enregistrerSalons();
    }

    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void removeSalon(Salon salon) {
        salonListe.remove(salon);
        enregistrerSalons();
    }

    public void clear( ) {
        salonListe.clear();
        enregistrerSalons();
    }

    private void enregistrerSalons() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(salonListe);
        editor.putString("salon_liste", json);
        editor.apply();
    }

    public void chargementSalons() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("salon_list", null);
        Type type = new TypeToken<ArrayList<Salon>>() {}.getType();
        if (json != null) {
            salonListe = gson.fromJson(json, type);
        }
    }
}