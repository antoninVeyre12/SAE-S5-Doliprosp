package com.example.doliprosp.interfaces;

import android.content.Context;

import com.example.doliprosp.modeles.Utilisateur;

/**
 * interfaces définissant les opérations de la connexion.
 */
public interface IConnexionService {
    /**
     * Effectue une tentative de connexion à un service en ligne.
     *
     * @param url               L'URL du service d'authentification.
     * @param userName          Le nom d'utilisateur.
     * @param motDePasse        Le mot de passe de l'utilisateur.
     * @param context           Le contexte de l'application Android.
     * @param connexionCallBack Le callback permettant de récupérer le résultat de la connexion.
     */
    void connexion(String url, String userName, String motDePasse, Context context, ConnexionCallBack connexionCallBack);

    /**
     * Récupère le dernier utilisateur créé ou authentifié.
     *
     * @return Un objet {@link Utilisateur} représentant l'utilisateur.
     */
    Utilisateur getNouvelUtilisateur();
}
