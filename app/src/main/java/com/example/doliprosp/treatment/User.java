package com.example.doliprosp.treatment;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    public String connexion(String url, String userName, String password) throws JSONException {

        final String[] resultat = new String[1];
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                // écouteur de la réponse renvoyée par la requête
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        resultat[0] = reponse.substring(0, Math.min(400, reponse.length()));
                    }
                },
                // écouteur du retour de la requête si aucun résultat n'est renvoyé
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {

                    }
                });
        // la requête est placée dans la file d'attente des requêtes
        applicationManager.getRequestQueue().add(requeteVolley);

        JSONTokener tokenJSON = new JSONTokener(resultat.toString());
        JSONObject objectJSON = (JSONObject) tokenJSON.nextValue();
        StringBuilder resultatFormate = new StringBuilder();
        return resultatFormate.append(objectJSON.getString("token")).toString();

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