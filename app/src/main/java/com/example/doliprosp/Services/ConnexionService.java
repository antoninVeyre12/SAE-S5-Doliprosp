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

/**
 * Service gérant la connexion d'un utilisateur et la gestion de ses informations.
 */
public class ConnexionService implements IConnexionService {
    private Utilisateur nouvelUtilisateur;
    private String urlUtilisateur;

    /**
     * Constructeur par défaut de la classe ConnexionService.
     */
    public ConnexionService() {}

    /**
     * Effectue une tentative de connexion à l'API avec le nom d'utilisateur et le mot de passe.
     * @param url L'URL de l'API à utiliser pour la connexion.
     * @param nomUtilisateur Le nom d'utilisateur pour la connexion.
     * @param motDePasse Le mot de passe pour la connexion.
     * @param context Le contexte de l'application pour effectuer l'appel API.
     * @param callback Le callback qui sera appelé à la fin de la tentative de connexion.
     */
    public void connexion(String url, String nomUtilisateur, String motDePasse, Context context, ConnexionCallBack callback) {
        urlUtilisateur = url;

        try {
            String userNameEncoder = URLEncoder.encode(nomUtilisateur, "UTF-8");
            String passwordEncoder = URLEncoder.encode(motDePasse, "UTF-8");
            url = String.format("%s/api/index.php/login?login=%s&password=%s", url, userNameEncoder, passwordEncoder);
            Outils.appelAPIConnexion(url, context, new Outils.APIResponseCallback() {
                @Override
                public void onSuccess(JSONObject reponse) throws JSONException {
                    JSONObject successJSON = reponse.getJSONObject("success");
                    String apiKey = successJSON.getString("token");
                    nouvelUtilisateur = new Utilisateur(urlUtilisateur, nomUtilisateur, motDePasse, apiKey);
                    callback.onSuccess(nouvelUtilisateur); // Notifie le contrôleur en cas de succès
                }

                @Override
                public void onError(String errorMessage) {
                    Log.d("error apiConnexion", errorMessage);
                    callback.onError(errorMessage); // Notifie en cas d'erreur
                }
            });
        } catch (UnsupportedEncodingException e) {
            callback.onError(e.getMessage()); // Notifie l'erreur de l'encodage
        }
    }

    /**
     * Méthode de chiffrement de la clé API.
     * @param cleApi La clé API à chiffrer.
     * @return La clé API chiffrée
     */
    public String chiffrementApiKey(String cleApi) {
        return cleApi; // TODO chiffrer avec la méthode Vigenère
    }

    /**
     * Retourne l'objet Utilisateur associé à la connexion.
     * @return L'utilisateur nouvellement connecté.
     */
    public Utilisateur getNouvelUtilisateur() {
        return nouvelUtilisateur;
    }
}