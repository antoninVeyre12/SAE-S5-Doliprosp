package com.example.doliprosp.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.lang.reflect.Type;

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
        String username = sharedPreferences.getString("username", null);
        String url = sharedPreferences.getString("url", null);
        String motDePasse = sharedPreferences.getString("motDePasse", null);
        String apiKey = sharedPreferences.getString("apiKey", null);
        String prenom = sharedPreferences.getString("prenom", null);
        String nom = sharedPreferences.getString("nom", null);
        String ville = sharedPreferences.getString("ville", null);
        int codePostal = sharedPreferences.getInt("codePostal", 0);
        String adresse = sharedPreferences.getString("adresse", null);
        String mail = sharedPreferences.getString("mail", null);
        String numTelephone = sharedPreferences.getString("numTelephone", null);

        // Crée un nouvel objet Utilisateur à partir des données récupérées
        utilisateurActuel = new Utilisateur(url, username, motDePasse, apiKey);
        utilisateurActuel.setPrenom(prenom);
        utilisateurActuel.setNom(nom);
        utilisateurActuel.setVille(ville);
        utilisateurActuel.setCodePostal(codePostal);
        utilisateurActuel.setAdresse(adresse);
        utilisateurActuel.setMail(mail);
        utilisateurActuel.setNumTelephone(numTelephone);

        return utilisateurActuel;
    }

    /**
     * Supprime les données utilisateur des SharedPreferences.
     */
    public void supprimerDonnerUtilisateur() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Supprime les informations utilisateur stockées dans SharedPreferences
        editor.putString("username", null);
        editor.putString("url", null);
        editor.putString("motDePasse", null);
        editor.putString("apiKey", null);
        editor.putString("prenom", null);
        editor.putString("nom", null);
        editor.putString("ville", null);
        editor.putString("codePostal", null);
        editor.putString("adresse", null);
        editor.putString("mail", null);
        editor.putString("numTelephone", null);
        editor.apply(); // Applique les changements de manière asynchrone
    }
}