package com.example.doliprosp.services;

import android.content.Context;

import com.example.doliprosp.interfaces.ConnexionCallBack;
import com.example.doliprosp.interfaces.IConnexionService;
import com.example.doliprosp.modeles.Utilisateur;

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
    public ConnexionService() {
        //Besoin d'aucun paramètre pour instancier le service
    }

    /**
     * Effectue une tentative de connexion à l'API avec le nom d'utilisateur et le mot de passe.
     *
     * @param url            L'URL de l'API à utiliser pour la connexion.
     * @param nomUtilisateur Le nom d'utilisateur pour la connexion.
     * @param motDePasse     Le mot de passe pour la connexion.
     * @param context        Le contexte de l'application pour effectuer l'appel API.
     * @param callback       Le callback qui sera appelé à la fin de la tentative de connexion.
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
                    callback.onError(errorMessage); // Notifie en cas d'erreur
                }
            });
        } catch (UnsupportedEncodingException e) {
            callback.onError(e.getMessage()); // Notifie l'erreur de l'encodage
        }
    }

    /**
     * Retourne l'objet Utilisateur associé à la connexion.
     *
     * @return L'utilisateur nouvellement connecté.
     */
    public Utilisateur getNouvelUtilisateur() {
        return nouvelUtilisateur;
    }
}