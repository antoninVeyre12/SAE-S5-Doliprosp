package com.example.doliprosp.Services;

import android.content.Context;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Model.Salon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SalonService implements ISalonService {


    public SalonService()
    {
    }

    public void getSalonsEnregistres(Context context, Outils.APIResponseCallbackArrayTest callback)
    {
        ArrayList<Salon> listSavedShow = new ArrayList<Salon>();
        String url = "http://dolibarr.iut-rodez.fr/G2023-42/htdocs/api/index.php/categories?sortfield=t.rowid&sortorder=ASC&limit=100";
        //String apiKey = this.getUser().getApiKey();
        Outils.appelAPIGetList(url, context, new Outils.APIResponseCallbackArray() {
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


    public void envoyerSalon(SalonService salon) {

    }

    public void updateSalon(String nouveauNom) {

    }
}