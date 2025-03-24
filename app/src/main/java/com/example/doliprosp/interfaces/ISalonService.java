package com.example.doliprosp.interfaces;

import android.content.Context;

import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.services.Outils;
import com.example.doliprosp.viewmodels.MesSalonsViewModel;
import com.example.doliprosp.viewmodels.SalonsViewModel;

import java.util.List;

/**
 * interfaces définissant les opérations liées à la gestion des salons.
 */
public interface ISalonService {

    /**
     * Récupère la liste des salons enregistrés.
     *
     * @param context     Le contexte de l'application.
     * @param utilisateur L'utilisateur effectuant la requête.
     * @param callback    Le callback pour récupérer la liste des salons trouvés.
     */
    void getSalonsEnregistres(Context context, Utilisateur utilisateur,
                              Outils.APIResponseCallbackArrayTest callback);

    /**
     * Envoie les informations d'un salon à Dolibarr.
     */
    void envoyerSalon(Utilisateur utilisateur, Context context, Salon salonAEnvoyer, Outils.APIResponseCallbackString callback);

    /**
     * Vérifie si un salon existe déjà dans les listes de salons.
     *
     * @param nomRecherche       Le nom du salon à rechercher.
     * @param salonsViewModel    Le ViewModel contenant la liste générale des salons.
     * @param mesSalonsViewModel Le ViewModel contenant la liste des salons de l'utilisateur.
     * @return {@code true} si le salon existe, {@code false} sinon.
     */
    boolean salonExiste(String nomRecherche, SalonsViewModel salonsViewModel, MesSalonsViewModel mesSalonsViewModel);

    List<Salon> getListeSalonsSelectionnes(MesSalonsViewModel mesSalonsViewModel, SalonsViewModel salonsViewModel);

    void recupererIdSalon(Utilisateur utilisateur, String recherche, Context context, Outils.APIResponseCallbackString callback);

    List<Salon> rechercheSalons(String recherche,
                                SalonsViewModel salonsViewModel);

    List<Salon> rechercheMesSalons(String recherche,
                                   MesSalonsViewModel mesSalonsViewModel);

}
