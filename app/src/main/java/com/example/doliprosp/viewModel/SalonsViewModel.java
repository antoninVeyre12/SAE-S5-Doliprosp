package com.example.doliprosp.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Services.Outils;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
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
        context.deleteFile(NOM_FICHIER);
        enregistrerSalons(context);
    }


    /**
     * Enregistre la liste des salons dans un fichier CSV
     */
    private void enregistrerSalons(Context context) {
        String content = "";
        for(Salon salon : salonListe) {
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
        for(String champ : content.split(";")) {
            if(!champ.isEmpty()) {
                Salon salon = new Salon(champ);
                salonListe.add(salon);
            }
        }
    }
}