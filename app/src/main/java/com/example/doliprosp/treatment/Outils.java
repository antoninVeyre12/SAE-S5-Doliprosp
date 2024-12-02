package com.example.doliprosp.treatment;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doliprosp.ViewModel.ApplicationViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class Outils {

    private static IApplication applicationManager;

    private static JSONObject objectJSON = new JSONObject();

    public static JSONObject appelAPIGet(String url, Context context) throws JSONException {
        Log.d("URL",url);
        applicationManager = ApplicationViewModel.getApplication();
        //String apiKey =applicationManager.getUser().getApiKey();
        String apiKey = "816w91HKCO0gAg580ycDyezS5SCQIwpw";

        // Le résultat de la requête Volley sera un JSONObject directement
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            // Crée un JSONObject à partir de la réponse
                            objectJSON = new JSONObject(reponse);
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Erreur dans le parsing du JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        // Log l'erreur pour diagnostic
                        Log.e("VOLLEY_ERROR", "Erreur de requête : " + erreur.getMessage());
                    }
                })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY",apiKey);
                Log.d("HEADERS", "API Key : " + apiKey);
                return headers;
            }

        };
        // Ajouter la requête à la file d'attente
        applicationManager.getRequestQueue(context).add(requeteVolley);
        return objectJSON;
    }
}
