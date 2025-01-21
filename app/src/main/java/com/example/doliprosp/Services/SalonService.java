package com.example.doliprosp.Services;

import android.content.Context;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SalonService implements ISalonService {

    private String url;
    private String urlAppel;


    public void getSalonsEnregistres(Context context, String recherche, Utilisateur utilisateur, Outils.APIResponseCallbackArrayTest callback)
    {
        ArrayList<Salon> listSavedShow = new ArrayList<Salon>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t.date_creation&sortorder=DESC&limit=6&sqlfilters=(t.label%3Alike%3A'%25" + recherche +"%25')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nom = object.getString("label");
                        listSavedShow.add(new Salon(nom));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(listSavedShow);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public boolean salonExiste(String nomRecherche, SalonsViewModel salonsViewModel, MesSalonsViewModel mesSalonsViewModel) {
        for (Salon salon : salonsViewModel.getSalonListe()) {
            // Vérification si le nom du salon correspond à nomRecherche
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                return true;
            }
        }
        for (Salon salon : mesSalonsViewModel.getSalonListe()){
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                return true;
            }
        }
        return false;
    }


    public void envoyerSalon(SalonService salon) {

    }

    public void updateSalon(String nouveauNom) {

    }
}