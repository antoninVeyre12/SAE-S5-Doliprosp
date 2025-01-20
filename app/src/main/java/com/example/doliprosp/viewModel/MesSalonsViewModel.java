package com.example.doliprosp.viewModel;

import android.content.SharedPreferences;

import com.example.doliprosp.Model.Salon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class MesSalonsViewModel extends ViewModel {
    private ArrayList<Salon> salonList = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public ArrayList<Salon> getSalonList() {
        return salonList;
    }

    public void addSalon(Salon salon) {
        salonList.add(salon);
        enregistrerSalons();
    }

    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void removeSalon(Salon salon) {
        salonList.remove(salon);
        enregistrerSalons();
    }

    private void enregistrerSalons() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(salonList);
        editor.putString("mes_salon_list", json);
        editor.apply();
    }

    public void chargementSalons() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("mes_salon_list", null);
        Type type = new TypeToken<ArrayList<Salon>>() {}.getType();
        if (json != null) {
            salonList = gson.fromJson(json, type);
        }
    }
}