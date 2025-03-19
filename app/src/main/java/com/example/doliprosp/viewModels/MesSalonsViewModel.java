package com.example.doliprosp.viewModels;

import android.content.Context;

import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.services.Outils;

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
    private final String NOM_FICHIER = "mesSalons.csv";
    private final String SEPARATOR = ";";

    /**
     * Retourne la liste des salons.
     *
     * @return La liste des salons.
     */
    public ArrayList<Salon> getSalonListe() {
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
        String content = "";
        for (Salon mesSalons : salonListe) {
            content += mesSalons.getNom() + SEPARATOR;
        }
        Outils.ecrireDansFichierInterne(context, NOM_FICHIER, content);
    }

    /**
     * Charge la liste des salons depuis le fichier CSV..
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