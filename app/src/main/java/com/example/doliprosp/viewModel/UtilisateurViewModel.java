package com.example.doliprosp.viewModel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.Modele.Utilisateur;

import java.io.Serializable;

public class UtilisateurViewModel extends ViewModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Utilisateur utilisateurActuel;

    private SharedPreferences sharedPreferences;

    /**
     * Retourne l'utilisateur actuel.
     *
     * @return L'utilisateur actuel.
     */
    public Utilisateur getUtilisateur() {
        return utilisateurActuel;
    }

    /**
     * Définit un nouvel utilisateur et l'enregistre dans les SharedPreferences.
     *
     * @param nouvelUtilisateur L'utilisateur à définir.
     */
    public void setUtilisateur(Utilisateur nouvelUtilisateur) {
        this.utilisateurActuel = nouvelUtilisateur;
        enregistrerUtilisateur(); // Sauvegarde les informations de l'utilisateur dans SharedPreferences.
    }

    /**
     * Initialise les SharedPreferences pour le stockage persistant des données utilisateur.
     *
     * @param context Le contexte pour accéder aux SharedPreferences.
     */
    public void initSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences("users_prefs", Context.MODE_PRIVATE);
    }

    /**
     * Enregistre les données de l'utilisateur dans les SharedPreferences.
     */
    public void enregistrerUtilisateur() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Enregistre chaque information de l'utilisateur dans les SharedPreferences.
        editor.putString("username", utilisateurActuel.getNomUtilisateur());
        editor.putString("url", utilisateurActuel.getUrl());
        editor.putString("motDePasse", utilisateurActuel.getMotDePasse());
        editor.putString("apiKey", utilisateurActuel.getCleApi());
        editor.putString("prenom", utilisateurActuel.getPrenom());
        editor.putString("nom", utilisateurActuel.getNom());
        editor.putString("ville", utilisateurActuel.getVille());
        editor.putInt("codePostal", utilisateurActuel.getCodePostal());
        editor.putString("adresse", utilisateurActuel.getAdresse());
        editor.putString("mail", utilisateurActuel.getMail());
        editor.putString("numTelephone", utilisateurActuel.getNumTelephone());
        editor.apply(); // Applique les changements de manière asynchrone.
    }

    /**
     * Charge les données de l'utilisateur depuis les SharedPreferences.
     *
     * @return L'objet Utilisateur contenant les informations chargées.
     */
    public Utilisateur chargementUtilisateur() {
        // Récupère les valeurs des SharedPreferences
        String[] cles = {
                "username", "url", "motDePasse", "apiKey", "prenom",
                "nom", "ville", "adresse", "mail", "numTelephone"
        };

        String[] valeurs = new String[cles.length];
        for (int i = 0; i < cles.length; i++) {
            valeurs[i] = sharedPreferences.getString(cles[i], null);
        }

        int codePostal = sharedPreferences.getInt("codePostal", 0);

        // Crée un nouvel objet Utilisateur avec les premiers paramètres obligatoires
        utilisateurActuel = new Utilisateur(valeurs[1], valeurs[0], valeurs[2], valeurs[3]);

        // Remplit les autres informations avec les setters
        utilisateurActuel.setPrenom(valeurs[4]);
        utilisateurActuel.setNom(valeurs[5]);
        utilisateurActuel.setVille(valeurs[6]);
        utilisateurActuel.setCodePostal(codePostal);
        utilisateurActuel.setAdresse(valeurs[7]);
        utilisateurActuel.setMail(valeurs[8]);
        utilisateurActuel.setNumTelephone(valeurs[9]);

        return utilisateurActuel;
    }

    /**
     * Supprime les données utilisateur des SharedPreferences.
     */
    public void supprimerDonnerUtilisateur() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String[] cles = {
                "username", "url", "motDePasse", "apiKey", "prenom", "nom",
                "ville", "codePostal", "adresse", "mail", "numTelephone"
        };
        for (String cle : cles) {
            editor.remove(cle);
        }
        editor.apply(); // Applique les changements de manière asynchrone
    }
}