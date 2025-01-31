package com.example.doliprosp.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire pour effectuer des appels API avec la bibliothèque Volley.
 */
public class Outils {

    private static RequestQueue fileRequete;

    private static String cleApi;

    private UtilisateurViewModel utilisateurVueModele;

    /**
     * Effectue un appel API de connexion.
     *
     * @param url L'URL de l'API.
     * @param context Le contexte pour obtenir la file de requêtes.
     * @param callback Le callback à invoquer après la réponse.
     */
    public static void appelAPIConnexion(String url, Context context, APIResponseCallback callback) {
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            JSONObject objectJSON = new JSONObject(reponse);
                            callback.onSuccess(objectJSON); // Notifie en cas de succès
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        Log.d("CONNEXION ECHOUE", "connexion échouée");
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                });
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    /**
     * Effectue un appel API GET avec un en-tête d'authentification.
     *
     * @param url L'URL de l'API.
     * @param cleApi La clé API utilisée pour l'authentification.
     * @param context Le contexte pour obtenir la file de requêtes.
     * @param callback Le callback à invoquer après la réponse.
     */
    public static void appelAPIGet(String url, String cleApi, Context context, APIResponseCallback callback) {
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            JSONObject objectJSON = new JSONObject(reponse);
                            callback.onSuccess(objectJSON); // Notifie en cas de succès
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", cleApi);
                return headers;
            }
        };
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    /**
     * Effectue un appel API GET pour récupérer une liste de données.
     *
     * @param url L'URL de l'API.
     * @param cleApi La clé API pour l'authentification.
     * @param context Le contexte pour obtenir la file de requêtes.
     * @param callback Le callback à invoquer après la réponse.
     */
    public static void appelAPIGetList(String url, String cleApi, Context context, APIResponseCallbackArray callback) {
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            JSONArray arrayJSON = new JSONArray(reponse);
                            callback.onSuccess(arrayJSON); // Notifie en cas de succès
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", cleApi);
                return headers;
            }
        };
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    /**
     * Retourne une instance de la file de requêtes Volley.
     *
     * @param context Le contexte pour obtenir la file de requêtes.
     * @return La file de requêtes.
     */
    public static RequestQueue getRequestQueue(Context context) {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(context);
        }
        return fileRequete;
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant un objet JSON.
     */
    public interface APIResponseCallback {
        void onSuccess(JSONObject response) throws JSONException;
        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant un tableau JSON.
     */
    public interface APIResponseCallbackArray {
        void onSuccess(JSONArray response);
        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant une liste de salons.
     */
    public interface APIResponseCallbackArrayTest {
        void onSuccess(ArrayList<Salon> response);
        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant une liste de prospects.
     */
    public interface APIResponseCallbackArrayProspect {
        void onSuccess(ArrayList<Prospect> response);
        void onError(String errorMessage);
    }

    public interface CallbackProspectExiste {
        void onResponse(boolean b);
    }
}