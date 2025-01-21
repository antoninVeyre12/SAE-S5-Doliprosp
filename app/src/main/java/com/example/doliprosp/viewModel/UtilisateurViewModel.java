package com.example.doliprosp.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModel;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;

import java.io.Serializable;

public class UtilisateurViewModel extends ViewModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Utilisateur utilisateurActuel;

    public void setUtilisateur(Utilisateur nouvelUtilisateur, Context context) {
        utilisateurActuel = nouvelUtilisateur;
        if(utilisateurActuel != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("users_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", nouvelUtilisateur.getNomUtilisateur());
            editor.putString("url", nouvelUtilisateur.getUrl());
            editor.putString("motDePasse", nouvelUtilisateur.getMotDePasse());
            editor.putString("apiKey", nouvelUtilisateur.getApiKey());

            editor.apply();
        }

    }

    public Utilisateur getUtilisateur(Context context, Activity activity) {
        if (utilisateurActuel == null) {
            // Chargement depuis SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("users_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);
            String url = sharedPreferences.getString("url", null);
            String motDePasse = sharedPreferences.getString("motDePasse", null);
            String apiKey = sharedPreferences.getString("apiKey", null);

            if (username != null) {
                utilisateurActuel = new Utilisateur(url, username, motDePasse, apiKey);

                LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);
                bottomNav.setVisibility(View.VISIBLE);
                ((MainActivity)activity).setColors(1);

            }
        }
        return utilisateurActuel;
    }
}
