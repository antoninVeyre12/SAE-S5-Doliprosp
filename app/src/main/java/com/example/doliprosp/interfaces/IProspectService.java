package com.example.doliprosp.interfaces;

import android.content.Context;

import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.services.Outils;
import com.example.doliprosp.viewmodels.MesProspectViewModel;

import java.util.ArrayList;

/**
 * interfaces définissant les opérations liées à la gestion des prospects.
 */
public interface IProspectService {

    /**
     * Ajoute un nouveau prospect.
     *
     * @param prospectAEnvoyer Le prospect à ajouter.
     */
    void envoyerProspect(Utilisateur utilisateur, Context context, Prospect prospectAEnvoyer, int idSalon, Outils.APIResponseCallbackString callback);


    /**
     * Récupère la liste des prospects associés à un salon donné.
     *
     * @param prospectListe La liste des prospects disponibles.
     * @param nomSalon      Le nom du salon dont on souhaite récupérer les prospects.
     * @return Une liste des prospects appartenant au salon spécifié.
     */
    ArrayList<Prospect> getProspectDUnSalon(ArrayList<Prospect> prospectListe, String nomSalon);

    /**
     * Vérifie si un prospect ou un client existe en fonction d'un critère de recherche.
     *
     * @param context     Le contexte de l'application.
     * @param recherche   La valeur à rechercher.
     * @param tri         Le critère de tri à appliquer.
     * @param utilisateur L'utilisateur effectuant la recherche.
     * @param callback    Le callback pour récupérer les résultats de la recherche.
     */
    void prospectClientExiste(Context context, String recherche, String tri,
                              Utilisateur utilisateur, Outils.APIResponseCallbackArrayProspect callback);

    void prospectDejaExistantDolibarr(Context context, String recherche, Utilisateur utilisateur, MesProspectViewModel mesProspectViewModel
            , Outils.CallbackProspectExiste callback);

    void lieProspectSalon(Utilisateur utilisateur, Context context,
                          int idSalon, int response);

    boolean existeDansViewModel(String recherche, MesProspectViewModel mesProspectViewModel);

    void modifierClient(Context context, Utilisateur utilisateur,
                        Prospect prospectAModifier, String idProspect);

}
