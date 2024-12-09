package com.example.doliprosp.viewModel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;
import com.example.doliprosp.Model.Utilisateur;
import java.io.Serializable;

public class UtilisateurViewModel extends ViewModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Utilisateur utilisateurActuel;

    public void setUtilisateur(Utilisateur nouvelUtilisateur, Context context) {
        utilisateurActuel = nouvelUtilisateur;
        // Sauvegarde dans SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("users_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", nouvelUtilisateur.getUserName());
        editor.putString("url", nouvelUtilisateur.getUrl());
        editor.putString("motDePasse", nouvelUtilisateur.getMotDePasse());
        editor.putString("apiKey", nouvelUtilisateur.getApiKey());

        // Ajouter d'autres informations si nécessaire
        editor.apply();
    }

    public Utilisateur getUtilisateur(Context context) {
        if (utilisateurActuel == null) {
            // Chargement depuis SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("users_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);
            String url = sharedPreferences.getString("url", null);
            String motDePasse = sharedPreferences.getString("motDePasse", null);
            String apiKey = sharedPreferences.getString("apiKey", null);

            if (username != null) {
                utilisateurActuel = new Utilisateur(url, username, motDePasse, apiKey);
            }
        }
        return utilisateurActuel;
    }
}
