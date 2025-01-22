package com.example.doliprosp.viewModel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;

import com.example.doliprosp.Modele.Prospect;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProspectViewModel extends AndroidViewModel {  // Changer de ViewModel à AndroidViewModel ou simplement ViewModel

    private ArrayList<Prospect> prospectListe = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public ProspectViewModel(Application application) {
        super(application);
    }

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

    public void clear() {
        prospectListe.clear();
        enregistrerProspect();
    }

    private void enregistrerProspect() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(prospectListe);
        editor.putString("prospect_liste", json);
        editor.apply();
    }

    public void chargementProspect() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("prospect_list", null);
        Type type = new TypeToken<ArrayList<Prospect>>() {
        }.getType();
        if (json != null) {
            prospectListe = gson.fromJson(json, type);
        }
    }
}
