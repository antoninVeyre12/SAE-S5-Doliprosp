package com.example.doliprosp.Services;

import android.app.Activity;
import android.content.Context;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.fragment.UserFragment;
import com.example.doliprosp.viewModel.UtilisateurViewModele;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class SalonService implements ISalonService {

    private String url;
    private String urlAppel;
    public SalonService()
    {
    }

    public void getSalonsEnregistres(Context context, Outils.APIResponseCallbackArrayTest callback)
    {
        ArrayList<Salon> listSavedShow = new ArrayList<Salon>();
        //url = UserFragment.utilisateurActuel.getUrl();

        urlAppel = "http://dolibarr.iut-rodez.fr/G2023-42/htdocs/api/index.php/categories?sortfield=t.rowid&sortorder=ASC&limit=100";
        Outils.appelAPIGetList(urlAppel, context, new Outils.APIResponseCallbackArray() {
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