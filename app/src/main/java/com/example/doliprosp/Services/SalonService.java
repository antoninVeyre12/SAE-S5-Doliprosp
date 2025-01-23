package com.example.doliprosp.Services;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
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
        ArrayList<Salon> listeSalonsEnregistres = new ArrayList<Salon>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t.date_creation&sortorder=DESC&limit=6&sqlfilters=(t.label%3Alike%3A'%25" + recherche +"%25')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nom = object.getString("label");
                        listeSalonsEnregistres.add(new Salon(nom));
                        Log.d("TEST_SALON",nom);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(listeSalonsEnregistres);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public boolean salonExiste(String nomRecherche, SalonsViewModel salonsViewModel, MesSalonsViewModel mesSalonsViewModel) {
        boolean existe = false;
        for (Salon salon : salonsViewModel.getSalonListe()) {
            // Vérification si le nom du salon correspond à nomRecherche
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                existe = true;
            }
        }
        for (Salon salon : mesSalonsViewModel.getSalonListe()){
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                existe = true;
            }
        }
        for (Salon salon : mesSalonsViewModel.getSalonListe()){
            if (nomRecherche == null || nomRecherche == "") {
                existe = false;
            }
        }
        return existe;
    }


    public void envoyerSalon(SalonService salon) {

    }

    public void updateSalon(String nouveauNom) {

    }
}