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

import java.util.ArrayList;

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
        this.apiKey = null;

    }

    public void changementUtilisateur(String url, String userName, String password)
    {
        this.setUrl(url);
        this.setUserName(userName);
        this.setPassword(password);
        this.apiKey = null;
    }


    /*
     * on crée une requête GET, paramètrée par l'url préparée ci-dessus,
     * Le résultat de cette requête sera une chaîne de caractères, donc la requête
     * est de type StringRequest
     */
    public void connexion(String url, Context context, APIResponseCallback callback) throws JSONException {
        Outils.appelAPIGet(url, context, new Outils.APIResponseCallback() {

            @Override
            public void onSuccess(JSONObject reponse) throws JSONException {

                // Récupère l'objet 'success' imbriqué
                JSONObject successJSON = reponse.getJSONObject("success");

                // Récupère le champ 'token' dans l'objet 'success'
                String token = successJSON.getString("token");
                apiKey = token;
                callback.onSuccess(apiKey);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
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

    public interface APIResponseCallback {
        void onSuccess(String apiKey) throws JSONException;
        void onError(String errorMessage);
    }
}