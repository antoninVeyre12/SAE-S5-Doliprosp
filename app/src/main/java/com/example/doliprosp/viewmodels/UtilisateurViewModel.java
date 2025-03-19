package com.example.doliprosp.viewmodels;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.services.ChiffrementVigenere;
import com.example.doliprosp.services.Outils;

import androidx.lifecycle.ViewModel;

public class UtilisateurViewModel extends ViewModel {

    private Utilisateur utilisateur;
    private static final String SEPARATOR = ";";
    private static final String NOM_FICHIER = "utilisateur.csv";


    /**
     * Retourne l'utilisateur actuel.
     *
     * @return L'utilisateur actuel.
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit un nouvel utilisateur et l'enregistre dans le fichier CSV.
     *
     * @param nouvelUtilisateur L'utilisateur à définir.
     * @param key               la cle de chiffrement
     */
    public void setUtilisateur(Utilisateur nouvelUtilisateur, Context context, String key) {
        this.utilisateur = nouvelUtilisateur;
        enregistrerUtilisateur(context, key);
    }

    /**
     * Enregistre les données de l'utilisateur dans un fichier CSV.
     *
     * @param key la cle de chiffrement
     */
    public void enregistrerUtilisateur(Context context, String key) {
        String apiKeyChifre =
                ChiffrementVigenere.chiffrement(utilisateur.getCleApi(), key);
        String content =
                utilisateur.getNomUtilisateur() + SEPARATOR + utilisateur.getUrl() + SEPARATOR + utilisateur.getMotDePasse() + SEPARATOR + utilisateur.getCleApi() + SEPARATOR + apiKeyChifre + SEPARATOR + utilisateur.getMail() + SEPARATOR + utilisateur.getNom() + SEPARATOR + utilisateur.getPrenom() + SEPARATOR + utilisateur.getVille() + SEPARATOR + utilisateur.getCodePostal() + SEPARATOR + utilisateur.getAdresse() + SEPARATOR + utilisateur.getNumTelephone() + SEPARATOR + key + SEPARATOR;
        Outils.ecrireDansFichierInterne(context, NOM_FICHIER, content);
    }

    /**
     * Charge les données de l'utilisateur depuis les SharedPreferences.
     *
     * @return L'objet Utilisateur contenant les informations chargées.
     */
    public Utilisateur chargementUtilisateur(Context context) {
        if (Outils.fichierValide(context, NOM_FICHIER)) {
            Log.d("content", Outils.lireFichierInterne(context, NOM_FICHIER));
            String[] valeurs = Outils.lireFichierInterne(context,
                    NOM_FICHIER).split(";");
            Log.d("valeurs", String.valueOf(valeurs.length));
            // Crée un nouvel objet Utilisateur avec les premiers paramètres obligatoires
            utilisateur = new Utilisateur(valeurs[1], valeurs[0], valeurs[2], valeurs[3]);

            // Remplit les autres informations avec les setters
            utilisateur.setCleChiffrement(valeurs[4]);
            utilisateur.setPrenom(valeurs[7]);
            utilisateur.setNom(valeurs[6]);
            utilisateur.setVille(valeurs[8]);
            utilisateur.setCodePostal(Integer.parseInt(valeurs[9]));
            utilisateur.setAdresse(valeurs[10]);
            utilisateur.setMail(valeurs[5]);
            utilisateur.setNumTelephone(valeurs[11]);
            utilisateur.setClePremierChiffrement(valeurs[12]);

        }
        return utilisateur;
    }

    /**
     * Supprime les données utilisateur en supprimant le fichier CSV associé.
     */
    public void supprimerDonnerUtilisateur(Context context) {
        utilisateur = null;
        context.deleteFile(NOM_FICHIER);
    }
}