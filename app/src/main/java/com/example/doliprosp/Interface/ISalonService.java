package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface définissant les opérations liées à la gestion des salons.
 */
public interface ISalonService {

    /**
     * Récupère la liste des salons enregistrés.
     * @param context Le contexte de l'application.
     * @param recherche Le terme de recherche à utiliser pour filtrer les salons.
     * @param utilisateur L'utilisateur effectuant la requête.
     * @param callback Le callback pour récupérer la liste des salons trouvés.
     */
    void getSalonsEnregistres(Context context, String recherche, Utilisateur utilisateur,
                              Outils.APIResponseCallbackArrayTest callback);

    /**
     * Envoie les informations d'un salon à Dolibarr.
     * @param salonService L'instance du service qui gère l'envoi du salon.
     */
    void envoyerSalon(Utilisateur utilisateur, Context context, Salon salonAEnvoyer,Outils.APIResponseCallbackString callback);

    /**
     * Vérifie si un salon existe déjà dans les listes de salons.
     * @param nomRecherche Le nom du salon à rechercher.
     * @param salonsViewModel Le ViewModel contenant la liste générale des salons.
     * @param mesSalonsViewModel Le ViewModel contenant la liste des salons de l'utilisateur.
     * @return {@code true} si le salon existe, {@code false} sinon.
     */
    boolean salonExiste(String nomRecherche, SalonsViewModel salonsViewModel, MesSalonsViewModel mesSalonsViewModel);
    List<Salon> getListeSalonsSelectionnes(MesSalonsViewModel mesSalonsViewModel) ;

    void recupererIdSalon(Utilisateur utilisateur,String recherche, Context context, Outils.APIResponseCallbackPost callback);

    }
