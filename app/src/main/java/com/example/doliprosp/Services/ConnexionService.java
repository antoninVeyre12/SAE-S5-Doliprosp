package com.example.doliprosp.Services;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.Interface.ConnexionCallBack;
import com.example.doliprosp.Interface.IConnexionService;
import com.example.doliprosp.Modele.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConnexionService implements IConnexionService {
    private Utilisateur nouvelUtilisateur;
    private String urlUtilisateur;



    public ConnexionService(){}

    public void connexion(String url, String nomUtilisateur, String motDePasse, Context context, ConnexionCallBack callback) {
        urlUtilisateur = url;

        try {
            String userNameEncoder = URLEncoder.encode(nomUtilisateur, "UTF-8");
            String passwordEncoder = URLEncoder.encode(motDePasse, "UTF-8");
            url = String.format("%s/api/index.php/login?login=%s&password=%s", urlUtilisateur, userNameEncoder, passwordEncoder);
            Log.d("URLL", url);
            Log.d("USERNAME", nomUtilisateur);
            Log.d("PASSWORD" , motDePasse);
            Outils.appelAPIConnexion(url, context, new Outils.APIResponseCallback() {
                @Override
                public void onSuccess(JSONObject reponse) throws JSONException {
                    JSONObject successJSON = reponse.getJSONObject("success");
                    String apiKey = successJSON.getString("token");
                    nouvelUtilisateur = new Utilisateur(urlUtilisateur, nomUtilisateur, motDePasse, apiKey);
                    Log.d("APIKEY", nouvelUtilisateur.getCleApi());
                    callback.onSuccess(nouvelUtilisateur); // Notifie le contrôleur
                }

                @Override
                public void onError(String errorMessage) {
                    Log.d("error apiConnexion", errorMessage);
                    callback.onError(errorMessage); // Notifie en cas d'erreur
                }
            });
        } catch (UnsupportedEncodingException e) {
            callback.onError(e.getMessage());
        }
    }
    public String chiffrementApiKey(String cleApi)
    {
        return cleApi;
        //TODO chiffrer avec la méthode Vigenère
    }

    public Utilisateur getNouvelUtilisateur()
    {
        return nouvelUtilisateur;
    }
}
