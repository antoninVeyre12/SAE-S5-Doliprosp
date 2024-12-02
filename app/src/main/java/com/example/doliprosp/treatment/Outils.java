package com.example.doliprosp.treatment;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doliprosp.ViewModel.ApplicationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class Outils {

    private static IApplication applicationManager;


    public static void appelAPIGet(String url, Context context, APIResponseCallback callback) {

        applicationManager = ApplicationViewModel.getApplication();
        String apiKey = "816w91HKCO0gAg580ycDyezS5SCQIwpw";
        //String apiKey = applicationManager.getUser().getApiKey();
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
        applicationManager.getRequestQueue(context).add(requeteVolley);
    }



    public static void appelAPIGetList(String url, Context context, APIResponseCallbackArray callback) {

        applicationManager = ApplicationViewModel.getApplication();
        String apiKey = "816w91HKCO0gAg580ycDyezS5SCQIwpw";
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
        applicationManager.getRequestQueue(context).add(requeteVolley);
    }




    public interface APIResponseCallback {
        void onSuccess(JSONObject response) throws JSONException;
        void onError(String errorMessage);
    }

    public interface APIResponseCallbackArray {
        void onSuccess(JSONArray response);
        void onError(String errorMessage);
    }
}
