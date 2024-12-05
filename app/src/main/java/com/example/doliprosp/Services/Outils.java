package com.example.doliprosp.Services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.fragment.UserFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModelProvider;

public class Outils {

    private static RequestQueue fileRequete;

    private static String apiKey;


    public static void appelAPIConnexion(String url, Context context, APIResponseCallback callback) {

        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            // Crée un JSONObject à partir de la réponse
                            JSONObject objectJSON = new JSONObject(reponse);

                            callback.onSuccess(objectJSON); // Notifie la méthode appelante avec la réponse
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        // Log l'erreur pour diagnostic
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
        };
        // Ajouter la requête à la file d'attente
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }


    public static void appelAPIGet(String url, Context context, APIResponseCallback callback) {
        apiKey = UserFragment.utilisateurActuel.getApiKey();
        //apiKey = "816w91HKCO0gAg580ycDyezS5SCQIwpw";
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            // Crée un JSONObject à partir de la réponse
                            JSONObject objectJSON = new JSONObject(reponse);

                            callback.onSuccess(objectJSON); // Notifie la méthode appelante avec la réponse
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        // Log l'erreur pour diagnostic
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", apiKey);
                return headers;
            }
        };
        // Ajouter la requête à la file d'attente
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }



    public static void appelAPIGetList(String url, Context context, APIResponseCallbackArray callback) {
        apiKey = UserFragment.utilisateurActuel.getApiKey();
        //apiKey = "816w91HKCO0gAg580ycDyezS5SCQIwpw";
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            // Crée un JSONObject à partir de la réponse
                            JSONArray arrayJSON = new JSONArray(reponse);

                            callback.onSuccess(arrayJSON); // Notifie la méthode appelante avec la réponse
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        // Log l'erreur pour diagnostic
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", apiKey);
                return headers;
            }
        };
        // Ajouter la requête à la file d'attente
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }


    public static RequestQueue getRequestQueue(Context context) {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(context);
        }
        return fileRequete;
    }

    public interface APIResponseCallback {
        void onSuccess(JSONObject response) throws JSONException;
        void onError(String errorMessage);
    }

    public interface APIResponseCallbackArray {
        void onSuccess(JSONArray response);
        void onError(String errorMessage);
    }
    public interface APIResponseCallbackArrayTest {
        void onSuccess(ArrayList<Salon> response);
        void onError(String errorMessage);
    }
}
