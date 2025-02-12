package com.example.doliprosp.viewModel;

import android.content.Context;

import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Services.Outils;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel pour gérer la liste des salons dans l'application.
 * Ce ViewModel utilise Su fichier CSV pour enregistrer des salons.
 */
public class SalonsViewModel extends ViewModel {

    private ArrayList<Salon> salonListe = new ArrayList<>();
    private final String SEPARATOR = ";";
    private final String NOM_FICHIER = "salons.csv";


    /**
     * Retourne la liste actuelle des salons.
     *
     * @return La liste des salons.
     */
    public ArrayList<Salon> getSalonListe() {
        return salonListe;
    }

    /**
     * Ajoute un salon à la liste et enregistre la liste mise à jour dans le fichier CSV.
     *
     * @param salon Le salon à ajouter.
     */
    public void addSalon(Salon salon, Context context) {
        salonListe.add(salon);
        enregistrerSalons(context);
    }

    /**
     * Ajoute un salon à la liste et enregistre la liste mise à jour dans le fichier CSV.
     *
     * @param context le context de l'activité.
     */
    public void clear(Context context) {
        salonListe.clear();
        enregistrerSalons(context);
    }

    public void removeSalon(Salon salon, Context context) {
        salonListe.remove(salon);
        enregistrerSalons(context); // Sauvegarde la liste mise à jour après suppression du salon.
    }

    /**
     * Enregistre la liste des salons dans un fichier CSV
     */
    private void enregistrerSalons(Context context) {
        String content = "";
        for (Salon salon : salonListe) {
            content += salon.getNom() + SEPARATOR;
        }
        Outils.ecrireDansFichierInterne(context, NOM_FICHIER, content);
    }

    /**
     * Charge la liste des salons depuis le fichier CSV.
     */
    public void chargementSalons(Context context) {
        salonListe.clear();
        String content = Outils.lireFichierInterne(context, NOM_FICHIER);
        for (String champ : content.split(";")) {
            if (!champ.isEmpty()) {
                Salon salon = new Salon(champ);
                salonListe.add(salon);
            }
        }
    }
}