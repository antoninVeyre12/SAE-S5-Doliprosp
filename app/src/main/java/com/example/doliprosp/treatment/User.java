package com.example.doliprosp.treatment;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.ViewModel.ApplicationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class User {
    private String url;
    private String userName;
    private String password;
    private String apiKey;
    private IApplication applicationManager;


    public User(String url, String userName, String password)
    {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public void changementUtilisateur(String url, String userName, String password)
    {
        this.setUrl(url);
        this.setUserName(userName);
        this.setPassword(password);
    }


    /*
     * on crée une requête GET, paramètrée par l'url préparée ci-dessus,
     * Le résultat de cette requête sera une chaîne de caractères, donc la requête
     * est de type StringRequest
     */
    public String connexion(String url, Context context) throws JSONException {
        Log.d("URL",url);
        ApplicationViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ApplicationViewModel.class);
        applicationManager = viewModel.getApplication();

        // Le résultat de la requête Volley sera un JSONObject directement
        final StringBuilder resultatFormate = new StringBuilder(); // Utiliser un StringBuilder pour l'accumulation
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                // Écouteur de la réponse renvoyée par la requête
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            // Crée un JSONObject à partir de la réponse
                            JSONObject objectJSON = new JSONObject(reponse);

                            // Récupère l'objet 'success' imbriqué
                            JSONObject successJSON = objectJSON.getJSONObject("success");

                            // Récupère le champ 'token' dans l'objet 'success'
                            String token = successJSON.getString("token");

                            // Stocker le token dans le StringBuilder
                            resultatFormate.append(token);

                            // Log pour vérification
                            Log.d("REPONSEAPI", "Token extrait : " + token);
                        } catch (JSONException e) {
                            // Log si le JSON est mal formé
                            Log.e("JSON_ERROR", "Erreur dans le parsing du JSON : " + e.getMessage());
                        }
                    }
                },
                // Écouteur en cas d'erreur de la requête
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        // Log l'erreur pour diagnostic
                        Log.e("VOLLEY_ERROR", "Erreur de requête : " + erreur.getMessage());
                    }
                });
        // Ajouter la requête à la file d'attente
        applicationManager.getRequestQueue(context).add(requeteVolley);
        // Retourner le résultat (attention : cela sera asynchrone !)
        return resultatFormate.toString();
    }

    public void chiffrementApiKey()
    {
        /*TODO chiffrer la clé API avec la méthode de Vigenère*/
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setUrl(String newUrl)
    {
        this.url = newUrl;
    }

    public void setUserName(String newUserName)
    {
        this.userName = newUserName;
    }

    public void setPassword(String newPassword)
    {
        this.password = newPassword;
    }

    public void setApiKey(String newApiKey)
    {
        this.apiKey = newApiKey;
    }
}