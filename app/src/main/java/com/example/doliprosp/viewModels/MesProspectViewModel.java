package com.example.doliprosp.viewModels;

import android.content.Context;

import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.services.Outils;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel pour gérer la liste des prospects dans l'application.
 * Cette classe permet d'ajouter, supprimer et charger des prospects à partir d'u fichier CSV.
 * Elle assure la gestion des données liées aux prospects, leur persistance et leur récupération.
 */
public class MesProspectViewModel extends ViewModel {
    // Liste des prospects gérée dans le ViewModel
    private ArrayList<Prospect> prospectListe = new ArrayList<>();
    private final String NOM_FICHIER = "mesProspects.csv";
    private final String SEPARATEUR = ";";
    private final String SAUT_DE_LIGNE = "\n";

    /**
     * Retourne la liste des prospects.
     *
     * @return La liste des prospects.
     */
    public ArrayList<Prospect> getProspectListe() {
        return prospectListe;
    }

    /**
     * Ajoute un prospect à la liste et enregistre la liste mise à jour dans le fichier CSV.
     *
     * @param prospect Le prospect à ajouter.
     * @param context  le context de l'acivité
     */
    public void addProspect(Prospect prospect, Context context) {
        prospectListe.add(prospect);
        enregistrerProspect(context); // Sauvegarde la liste mise à jour des prospects.
    }

    /**
     * Supprime un prospect de la liste et enregistre la liste mise à jour dans SharedPreferences.
     *
     * @param prospect Le prospect à supprimer.
     * @param context  le context de l'acivité
     */
    public void removeProspect(Prospect prospect, Context context) {
        prospectListe.remove(prospect);
        enregistrerProspect(context); // Sauvegarde la liste mise à jour après suppression du prospect.
    }

    /**
     * Enregistre la liste des prospects dans le fichier CSV.
     *
     * @param context le context de l'acivité
     */
    public void enregistrerProspect(Context context) {
        String content = "";
        for (Prospect monProspect : prospectListe) {
            content += saisieProspect(monProspect) + SAUT_DE_LIGNE;
        }
        Outils.ecrireDansFichierInterne(context, NOM_FICHIER, content);
    }


    /**
     * Charge la liste des prospects depuis le fichier CSV..
     *
     * @param context le context de l'acivité
     */
    public void chargementProspect(Context context) {

        prospectListe.clear();
        String content = Outils.lireFichierInterne(context, NOM_FICHIER);

        for (String prospect : content.split(SAUT_DE_LIGNE)) {
            String[] champs = prospect.split(SEPARATEUR);
            if (champs.length == 11) {
                Prospect monProspect = new Prospect(champs[0], champs[1], champs[2], champs[3], champs[4],
                        champs[5], champs[6], champs[7], champs[8], champs[9],
                        Long.valueOf(champs[10]));
                prospectListe.add(monProspect);
            }
        }
    }

    /**
     * Retourne une string correspondante à la ligne du prospect à insérer dans le CSV
     *
     * @param monProspect
     * @return
     */
    private String saisieProspect(Prospect monProspect) {
        return monProspect.getNomSalon() + SEPARATEUR + monProspect.getNom()
                + SEPARATEUR + monProspect.getCodePostal() + SEPARATEUR + monProspect.getVille()
                + SEPARATEUR + monProspect.getAdresse() + SEPARATEUR + monProspect.getMail()
                + SEPARATEUR + monProspect.getNumeroTelephone() + SEPARATEUR
                + monProspect.getEstClient() + SEPARATEUR + monProspect.getImage() + SEPARATEUR
                + monProspect.getIdDolibarr() + SEPARATEUR + (monProspect.getHeureSaisieTimestamp());
    }
}