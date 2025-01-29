package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Modele.Utilisateur;

/**
 * Interface définissant les opérations de la connexion.
 */
public interface IConnexionService {
    /**
     * Effectue une tentative de connexion à un service en ligne.
     *
     * @param url L'URL du service d'authentification.
     * @param userName Le nom d'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @param context Le contexte de l'application Android.
     * @param connexionCallBack Le callback permettant de récupérer le résultat de la connexion.
     */
    void connexion(String url, String userName, String motDePasse, Context context, ConnexionCallBack connexionCallBack);

    /**
     * Chiffre une clé API pour sécuriser son stockage.
     * @param apiKey La clé API à chiffrer.
     * @return La clé API chiffrée.
     */
    String chiffrementApiKey(String apiKey);

    /**
     * Récupère le dernier utilisateur créé ou authentifié.
     * @return Un objet {@link Utilisateur} représentant l'utilisateur.
     */
    Utilisateur getNouvelUtilisateur();
}
