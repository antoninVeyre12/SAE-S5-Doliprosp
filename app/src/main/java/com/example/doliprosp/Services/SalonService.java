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

/**
 * Service gérant les salons de l'application.
 */
public class SalonService implements ISalonService {

    private String url;
    private String urlAppel;

    /**
     * Méthode pour récupérer les salons enregistrées dans Dolibarr
     * @param context Le contexte de l'application.
     * @param recherche Le terme de recherche à utiliser pour filtrer les salons.
     * @param utilisateur L'utilisateur effectuant la requête.
     * @param callback Le callback pour récupérer la liste des salons trouvés.
     */
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

    /**
     * Méthode pour retrouver un salon dans Dolibarr.
     * @param nomRecherche nom saisi par l'utilisateur.
     * @param salonsViewModel Contient la liste des salons disponibles dans Dolibarr
     * @param mesSalonsViewModel contient la liste des salons saisis par l'utilisateur qui ne sont pas encore disponible dans Dolibarr
     * @return true si le salon existe sinon false
     */
    public boolean salonExiste(String nomRecherche, SalonsViewModel salonsViewModel, MesSalonsViewModel mesSalonsViewModel) {
        boolean existe = false;
        if (nomRecherche == null || nomRecherche.isEmpty()) {
            existe = false; // Si le nom de recherche est nul ou vide, on retourne directement false
        }
        for (Salon salon : salonsViewModel.getSalonListe()) {
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                existe = true; // Si le salon est trouvé dans salonsViewModel, on retourne true
            }
        }
        for (Salon salon : mesSalonsViewModel.getSalonListe()) {
            if (salon.getNom().equalsIgnoreCase(nomRecherche)) {
                existe = true; // Si le salon est trouvé dans mesSalonsViewModel, on retourne true
            }
        }
        return existe; // Si le salon n'est pas trouvé dans les deux listes, on retourne false
    }


    public void envoyerSalon(SalonService salon) {
        //TODO
    }

    public void updateSalon(String nouveauNom) {
        //TODO
    }
}