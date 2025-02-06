package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface définissant les opérations de gestion des projets.
 */
public interface IProjetService {

    /**
     * Met à jour les informations d'un projet existant.
     *
     * @param projet      Le projet à mettre à jour.
     * @param titre       Le nouveau titre à mettre à jour.
     * @param description La nouvelle description à mettre à jour.
     * @param dateDebut   La nouvelle date de début du projet à mettre à jour.
     */
    void updateProjet(Projet projet, String titre, String description, String dateDebut, long dateTimestamp);

    /**
     * Récupère la liste des projets associés à un prospect donné.
     *
     * @param projetListe La liste des projets disponibles.
     * @param nomProspect Le nom du prospect dont on souhaite récupérer les projets.
     * @return Une liste des projets appartenant au prospect spécifié.
     */
    List<Projet> getProjetDUnProspect(ArrayList<Projet> projetListe, String nomProspect);

    public void envoyerProjet(Utilisateur utilisateur, Context context, Projet projetAEnvoyer, int idProspect);

}