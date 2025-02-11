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
import java.util.List;

import static com.example.doliprosp.Services.Outils.appelAPIGetList;
import static com.example.doliprosp.Services.Outils.appelAPIPostInteger;

/**
 * Service gérant les salons de l'application.
 */
public class SalonService implements ISalonService {

    private String url;
    private String urlAppel;

    /**
     * Méthode pour récupérer les salons enregistrées dans Dolibarr
     *
     * @param context     Le contexte de l'application.
     * @param utilisateur L'utilisateur effectuant la requête.
     * @param callback    Le callback pour récupérer la liste des salons trouvés.
     */
    public void getSalonsEnregistres(Context context, Utilisateur utilisateur, Outils.APIResponseCallbackArrayTest callback) {
        ArrayList<Salon> listeSalonsEnregistres = new ArrayList<Salon>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t.date_creation&sortorder=DESC";
        Log.d("urlAppel", urlAppel);
        appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nom = object.getString("label");
                        listeSalonsEnregistres.add(new Salon(nom));

                        Log.d("TEST_SALON", nom);
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
     *
     * @param nomRecherche       nom saisi par l'utilisateur.
     * @param salonsViewModel    Contient la liste des salons disponibles dans Dolibarr
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


    public void envoyerSalon(Utilisateur utilisateur, Context context, Salon salonAEnvoyer, Outils.APIResponseCallbackString callback) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories";
        String apikey = utilisateur.getCleApi();
        JSONObject jsonBody = creationJsonSalon(salonAEnvoyer);
        Log.d("jsonBody", jsonBody.toString());

        appelAPIPostInteger(urlAppel, utilisateur.getCleApi(), jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) throws JSONException {
                callback.onSuccess(String.valueOf(response));
            }

            @Override
            public void onError(String errorMessage) {
                //Log.d("onerror", errorMessage.toString());
            }
        });
    }

    private JSONObject creationJsonSalon(Salon salonAEnvoyer) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("label", salonAEnvoyer.getNom());
            jsonBody.put("type", 2);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonBody;
    }

    public List<Salon> getListeSalonsSelectionnes(MesSalonsViewModel mesSalonsViewModel) {

        List<Salon> salonsSelectionnes = new ArrayList<>();
        for (Salon salon : mesSalonsViewModel.getSalonListe()) {
            if (salon.estSelectionne()) {
                salonsSelectionnes.add(salon);
            }
        }
        return salonsSelectionnes;
    }

    public void recupererIdSalon(Utilisateur utilisateur, String recherche, Context context, Outils.APIResponseCallbackString callback) {
        url = utilisateur.getUrl();
        String rechercheSansEspace = remplaceEspace(recherche);
        urlAppel = url + "/api/index.php/categories?&sortorder=DESC&sqlfilters=(t.label%3A=%3A'" + rechercheSansEspace + "')";
        String apikey = utilisateur.getCleApi();

        Log.d("urlAppel", urlAppel);

        appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {

            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String idSalon = object.getString("id");
                        callback.onSuccess(idSalon);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    public List<Salon> rechercheMesSalons(String recherche,
                                          MesSalonsViewModel mesSalonsViewModel) {

        List<Salon> resultat = new ArrayList<>();

        for (Salon salon : mesSalonsViewModel.getSalonListe()) {
            if (salon.getNom().contains(recherche)) {
                resultat.add(salon);
            }
        }
        return resultat;
    }

    public List<Salon> rechercheSalons(String recherche, SalonsViewModel salonsViewModel) {

        List<Salon> resultat = new ArrayList<>();

        for (Salon salon : salonsViewModel.getSalonListe()) {
            if (salon.getNom().contains(recherche)) {
                resultat.add(salon);
            }
        }
        return resultat;
    }

    private String remplaceEspace(String str) {
        // Remplacer chaque espace par "%20"
        return str.replace(" ", "%20");
    }


}