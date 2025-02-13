package com.example.doliprosp.interfaces;

import com.example.doliprosp.modeles.Utilisateur;

/**
 * interfaces permettant de gérer les retours d'une tentative de connexion.
 */
public interface ConnexionCallBack {

    /**
     * Méthode appelée lorsque la connexion réussit.
     *
     * @param nouvelUtilisateur L'utilisateur authentifié après une connexion réussie.
     */
    void onSuccess(Utilisateur nouvelUtilisateur);

    /**
     * Méthode appelée lorsqu'une erreur survient lors de la connexion.
     *
     * @param errorMessage Le message d'erreur décrivant la cause de l'échec.
     */
    void onError(String errorMessage);
}
