package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.Services.Outils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Interface définissant les opérations liées à la gestion des prospects.
 */
public interface IProspectService {

    /**
     * Ajoute un nouveau prospect.
     * @param prospectListe Le prospect à ajouter.
     */
    void envoyerProspect(Utilisateur utilisateur, Context context, List<Prospect> prospectListe, int idSalon);

    /**
     * Supprime un prospect existant.
     * @param prospect Le prospect à supprimer.
     */
    void supprimerProspect(Prospect prospect);

    /**
     * Récupère la liste des prospects associés à un salon donné.
     * @param prospectListe La liste des prospects disponibles.
     * @param nomSalon Le nom du salon dont on souhaite récupérer les prospects.
     * @return Une liste des prospects appartenant au salon spécifié.
     */
    ArrayList<Prospect> getProspectDUnSalon(ArrayList<Prospect> prospectListe, String nomSalon);

    /**
     * Vérifie si un prospect ou un client existe en fonction d'un critère de recherche.
     * @param context Le contexte de l'application.
     * @param recherche La valeur à rechercher.
     * @param champ Le champ sur lequel effectuer la recherche (ex: nom, email).
     * @param tri Le critère de tri à appliquer.
     * @param utilisateur L'utilisateur effectuant la recherche.
     * @param callback Le callback pour récupérer les résultats de la recherche.
     */
    void prospectClientExiste(Context context, String recherche, String champ, String tri,
                                     Utilisateur utilisateur, Outils.APIResponseCallbackArrayProspect callback);

    /**
     * Met à jour les informations d'un prospect existant.
     * @param prenom Le prénom du prospect.
     * @param nom Le nom du prospect.
     * @param codePostal Le code postal du prospect.
     * @param ville La ville du prospect.
     * @param adresse L'adresse du prospect.
     * @param email L'adresse email du prospect.
     * @param numeroTelephone Le numéro de téléphone du prospect.
     * @param estClient Indique si le prospect est devenu client.
     * @param idProspet L'identifiant unique du prospect.
     */
    void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String email, String numeroTelephone,
                               Boolean estClient, UUID idProspet);
}
