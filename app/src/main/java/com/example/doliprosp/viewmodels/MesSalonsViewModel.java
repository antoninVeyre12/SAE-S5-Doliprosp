package com.example.doliprosp.viewmodels;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.services.Outils;

import java.io.File;
import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel pour gérer la liste des salons dans l'application.
 * Cette classe permet d'ajouter, supprimer et charger des salons à partir des SharedPreferences.
 * Elle assure la gestion des données liées aux salons, leur persistance et leur récupération.
 */
public class MesSalonsViewModel extends ViewModel {

    // Liste des salons gérée dans le ViewModel
    private ArrayList<Salon> salonListe = new ArrayList<>();
    private static final String NOM_FICHIER = "mesSalons.csv";
    private static final String SEPARATOR = ";";

    /**
     * Retourne la liste des salons.
     *
     * @return La liste des salons.
     */
    public ArrayList<Salon> getSalonListe() {
        Log.d("nombre mes salons", String.valueOf(salonListe.size()));
        return salonListe;
    }

    /**
     * Ajoute un salon à la liste et enregistre la liste dans le fichier CSV.
     *
     * @param salon Le salon à ajouter.
     */
    public void addSalon(Salon salon, Context context) {
        salonListe.add(salon);
        enregistrerSalons(context); // Sauvegarde la liste mise à jour des salons.
    }

    /**
     * Supprime un salon de la liste et enregistre la liste dans le fichier CSV
     *
     * @param salon Le salon à supprimer.
     */
    public void removeSalon(Salon salon, Context context) {
        salonListe.remove(salon);
        enregistrerSalons(context); // Sauvegarde la liste mise à jour après suppression du salon.
    }

    /**
     * Enregistre la liste des salons dans un fichier CSV.
     */
    public void enregistrerSalons(Context context) {
        StringBuilder content = new StringBuilder();
        for (Salon mesSalons : salonListe) {
            content.append(mesSalons.getNom() + SEPARATOR);
        }
        Outils.ecrireDansFichierInterne(context, NOM_FICHIER, content.toString());
    }

    /**
     * Charge la liste des salons depuis le fichier CSV..
     */
    public void chargementSalons(Context context) {
        salonListe.clear();
        Log.d("chargement salons", String.valueOf(salonListe.size()));
        File file = context.getFileStreamPath(NOM_FICHIER);
        if(file.exists()) {
            Log.d("mes salons", "fichier existe");
            String content = Outils.lireFichierInterne(context, NOM_FICHIER);
            for (String champ : content.split(";")) {
                if (!champ.isEmpty()) {
                    Salon salon = new Salon(champ);
                    salonListe.add(salon);
                }
            }
        }
    }

    /**
     * Vide la liste de mes salons
     */
    public void clearListe() {
        salonListe.clear();
    }
}