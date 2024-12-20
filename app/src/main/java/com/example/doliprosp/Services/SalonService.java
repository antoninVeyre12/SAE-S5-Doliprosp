package com.example.doliprosp.Services;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.fragment.UserFragment;
import com.example.doliprosp.viewModel.SalonViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

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
        urlAppel = url + "/api/index.php/categories?sortfield=t.rowid&sortorder=DESC&limit=6&sqlfilters=(t.label%3Alike%3A'%25" + recherche +"%25')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getApiKey(), context, new Outils.APIResponseCallbackArray() {
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

    public boolean salonExiste(String nomRecherche, ArrayList<Salon> showSavedList, SalonViewModel salonViewModel) {
        for (Salon salon : showSavedList) {
            // Vérification si le nom du salon correspond à nomRecherche
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                return true;
            }
        }
        for (Salon salon : salonViewModel.getSalonList()){
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