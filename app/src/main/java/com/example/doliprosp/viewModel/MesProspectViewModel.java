package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;

import com.example.doliprosp.Modele.Prospect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

public class MesProspectViewModel extends ViewModel {
    private ArrayList<Prospect> prospectListe = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public ArrayList<Prospect> getProspectListe() {
        return prospectListe;
    }


    public void addProspect(Prospect prospect) {
        prospectListe.add(prospect);
        enregistrerProspect();
    }

    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void removeProspect(Prospect prospect) {
        prospectListe.remove(prospect);
        enregistrerProspect();
    }

    private void enregistrerProspect() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(prospectListe);
        editor.putString("mes_prospect_list", json);
        editor.apply();
    }

    public void chargementProspect() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("mes_prospect_list", null);
        Type type = new TypeToken<ArrayList<Prospect>>() {}.getType();
        if (json != null) {
            prospectListe = gson.fromJson(json, type);
        }
    }
}
