package com.example.doliprosp.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.lang.reflect.Type;

public class UtilisateurViewModel extends ViewModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Utilisateur utilisateurActuel;

    private SharedPreferences sharedPreferences;

    public Utilisateur getUtilisateur() {
        return utilisateurActuel;
    }

    public void setUtilisateur(Utilisateur nouvelUtilisateur) {
        this.utilisateurActuel = nouvelUtilisateur;
        enregistrerUtilisateur();
    }

    public void initSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private void enregistrerUtilisateur() {
        if (utilisateurActuel != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", utilisateurActuel.getUserName());
            editor.putString("url", utilisateurActuel.getUrl());
            editor.putString("motDePasse", utilisateurActuel.getMotDePasse());
            editor.putString("apiKey", utilisateurActuel.getApiKey());
            editor.putString("prenom", utilisateurActuel.getPrenom());
            editor.putString("nom", utilisateurActuel.getNom());
            editor.putString("ville", utilisateurActuel.getVille());
            editor.putString("codePostal", String.valueOf(utilisateurActuel.getCodePostal()));
            editor.putString("adresse", utilisateurActuel.getAdresse());
            editor.putString("mail", utilisateurActuel.getMail());
            editor.putString("numTelephone", utilisateurActuel.getNumTelephone());
            editor.apply();
        }
    }

    public void chargementUtilisateur() {
        String username = sharedPreferences.getString("username", null);
        String url = sharedPreferences.getString("url", null);
        String motDePasse = sharedPreferences.getString("motDePasse", null);
        String apiKey = sharedPreferences.getString("apiKey", null);
        String prenom = sharedPreferences.getString("prenom", null);
        String nom = sharedPreferences.getString("nom", null);
        String ville = sharedPreferences.getString("ville", null);
        String codePostal = sharedPreferences.getString("codePostal", null);
        String adresse = sharedPreferences.getString("adresse", null);
        String mail = sharedPreferences.getString("mail", null);
        String numTelephone = sharedPreferences.getString("numTelephone", null);


        if (username != null && url != null && motDePasse != null && apiKey != null) {
            utilisateurActuel = new Utilisateur(url, username, motDePasse, apiKey);
            utilisateurActuel.setPrenom(prenom);
            utilisateurActuel.setNom(nom);
            utilisateurActuel.setVille(ville);
            utilisateurActuel.setCodePostal(Integer.valueOf(codePostal));
            utilisateurActuel.setAdresse(adresse);
            utilisateurActuel.setMail(mail);
            utilisateurActuel.setNumTelephone(numTelephone);
        }
    }

    public void supprimerDonnerUtilisateur() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
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
        editor.apply();
    }
}
