package com.example.doliprosp.viewModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Services.Outils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel permettant de gérer la liste des projets dans l'application.
 * Cette classe contient les méthodes pour ajouter, supprimer et charger des projets à partir des SharedPreferences.
 * Elle est responsable de la gestion des données liées aux projets, de leur persistance et de leur chargement.
 */
public class MesProjetsViewModel extends ViewModel {
    // Liste des projets gérée dans le ViewModel
    private ArrayList<Projet> projetListe = new ArrayList<>();

    private final String NOM_FICHIER = "mesProjets.csv";
    private final String SEPARATEUR = ";";
    private final String SAUT_DE_LIGNE = "\n";

    /**
     * Retourne la liste des projets.
     *
     * @return La liste des projets.
     */
    public ArrayList<Projet> getProjetListe() {
        return projetListe;
    }

    /**
     * Ajoute un projet à la liste et enregistre la liste dans le fichier CSV.
     *
     * @param projet Le projet à ajouter.
     * @param context le context de l'activité
     */
    public void addProjet(Projet projet, Context context) {
        projetListe.add(projet);
        enregistrerProjet(context); // Sauvegarde la liste mise à jour des projets.
    }

    /**
     * Supprime un projet de la liste et enregistre la liste mise à jour dans le fichier CSV.
     *
     * @param projet Le projet à supprimer
     * @param context le context de l'activité
     */
    public void removeProjet(Projet projet, Context context) {
        projetListe.remove(projet);
        enregistrerProjet(context); // Sauvegarde la liste mise à jour après suppression du projet.
    }

    /**
     * Enregistre la liste des projets dans le fichier CSV.
     * @param context le context de l'activité
     */
    public void enregistrerProjet(Context context) {
        String content = "";
        for(Projet projet : projetListe) {
            content += saisieProjet(projet) + SAUT_DE_LIGNE;
        }
        Outils.ecrireDansFichierInterne(context, NOM_FICHIER, content);
    }

    /**
     * Charge la liste des projets depuis le fichier CSV.
     * @param context le context de l'activité
     */
    public void chargementProjet(Context context) {
        projetListe.clear();
        String content = Outils.lireFichierInterne(context, NOM_FICHIER);

        for(String projet : content.split(SAUT_DE_LIGNE)) {
            String [] champs = projet.split(SEPARATEUR);
            if(champs.length == 5) {
                Projet monProjet = new Projet(champs[0], champs[1], champs[2], champs[3], Long.valueOf(champs[4]));
                projetListe.add(monProjet);
            }
        }
    }

    /**
     * Retourne une string correspondante à la ligne du projet à insérer dans le CSV
     * @param monProjet
     * @return
     */
    private String saisieProjet(Projet monProjet) {
        return monProjet.getNomProspect() + SEPARATEUR + monProjet.getTitre()
                + SEPARATEUR + monProjet.getDescription() + SEPARATEUR + monProjet.getDateDebut()
                + SEPARATEUR + monProjet.getDateTimestamp();
    }
}