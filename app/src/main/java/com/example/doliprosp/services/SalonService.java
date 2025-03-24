package com.example.doliprosp.services;

import static com.example.doliprosp.services.Outils.appelAPIGetList;
import static com.example.doliprosp.services.Outils.appelAPIPostInteger;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.interfaces.ISalonService;
import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.viewmodels.MesSalonsViewModel;
import com.example.doliprosp.viewmodels.SalonsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Salon> listeSalonsEnregistres = new ArrayList<>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t.date_creation&sortorder=DESC";

        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());
        appelAPIGetList(urlAppel, apiKeyDechiffre, context, new Outils.APIResponseCallbackArray() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nom = object.getString("label");
                        listeSalonsEnregistres.add(new Salon(nom));

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

    /**
     * Méthode pour envoyer les salons dans Dolibarr
     *
     * @param utilisateur
     * @param context
     * @param salonAEnvoyer
     * @param callback
     */
    public void envoyerSalon(Utilisateur utilisateur, Context context, Salon salonAEnvoyer, Outils.APIResponseCallbackString callback) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories";
        JSONObject jsonBody = creationJsonSalon(salonAEnvoyer);
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());

        appelAPIPostInteger(urlAppel, apiKeyDechiffre, jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) throws JSONException {
                callback.onSuccess(String.valueOf(response));
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError("erreur");
            }
        });
    }

    /**
     * Méthode pour créer le body JSON à envoyer
     *
     * @param salonAEnvoyer
     * @return jsonBody Renvoi le json à envoyer pour Dolibarr
     */
    private JSONObject creationJsonSalon(Salon salonAEnvoyer) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("label", salonAEnvoyer.getNom());
            jsonBody.put("type", 2);

        } catch (JSONException e) {
            Log.d("json exception", e.toString());
        }
        return jsonBody;
    }

    /**
     * Méthode qui renvoie la liste des salons sélectionnés
     *
     * @param mesSalonsViewModel
     * @param salonsViewModel
     * @return salonSelectionnes renvoie la liste des salons sélectionnées
     * par l'utilisateur
     */
    public List<Salon> getListeSalonsSelectionnes(MesSalonsViewModel mesSalonsViewModel, SalonsViewModel salonsViewModel) {

        List<Salon> salonsSelectionnes = new ArrayList<>();
        for (Salon salon : mesSalonsViewModel.getSalonListe()) {
            if (salon.estSelectionne()) {
                salonsSelectionnes.add(salon);
            }
        }
        for (Salon salon : salonsViewModel.getSalonListe()) {
            if (salon.estSelectionne()) {
                salonsSelectionnes.add(salon);
            }
        }
        return salonsSelectionnes;
    }

    /**
     * Renvoie l'id d'un salon enregistré dans Dolibarr
     *
     * @param utilisateur
     * @param recherche
     * @param context
     * @param callback
     */
    public void recupererIdSalon(Utilisateur utilisateur, String recherche, Context context, Outils.APIResponseCallbackString callback) {
        url = utilisateur.getUrl();
        String rechercheSansEspace = remplaceEspace(recherche);
        urlAppel = url + "/api/index.php/categories?&sortorder=DESC&sqlfilters=(t.label%3A=%3A'" + rechercheSansEspace + "')";

        Log.d("urlAppel", urlAppel);
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());


        appelAPIGetList(urlAppel, apiKeyDechiffre, context, new Outils.APIResponseCallbackArray() {

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

    /**
     * Renvoie la liste des salons trouvés après une recherche au sein des
     * salons en local
     *
     * @param recherche
     * @param mesSalonsViewModel
     * @return resultat
     */
    public List<Salon> rechercheMesSalons(String recherche,
                                          MesSalonsViewModel mesSalonsViewModel) {

        List<Salon> resultat = new ArrayList<>();

        // met la recherche en minuscule pour empecher les problemes de cases
        String rechercheLower = recherche.toLowerCase();

        for (Salon salon : mesSalonsViewModel.getSalonListe()) {
            if (salon.getNom().toLowerCase().contains(rechercheLower)) {
                resultat.add(salon);
            }
        }
        return resultat;
    }

    /**
     * Renvoie la liste des salons trouvés après une recherche
     *
     * @param recherche
     * @param salonsViewModel
     * @return resultat
     */
    public List<Salon> rechercheSalons(String recherche, SalonsViewModel salonsViewModel) {

        List<Salon> resultat = new ArrayList<>();

        // met la recherche en minuscule pour empecher les problemes de cases
        String rechercheLower = recherche.toLowerCase();

        for (Salon salon : salonsViewModel.getSalonListe()) {
            if (salon.getNom().toLowerCase().contains(rechercheLower)) {
                resultat.add(salon);
            }
        }
        return resultat;
    }

    /**
     * Méthode pour ajouter des %20 à la place des espaces
     *
     * @param str
     * @return renvoie le champs à envoyer à l'API sans espace
     */
    private String remplaceEspace(String str) {
        // Remplacer chaque espace par "%20"
        return str.replace(" ", "%20");
    }
}