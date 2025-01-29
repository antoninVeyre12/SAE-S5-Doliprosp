package com.example.doliprosp.Services;

import android.util.Log;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.Modele.Projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjetService implements IProjetService {

    public ProjetService(){ }


    public void ajouterProjet(Projet projet)
    {
        //Ajoute un projet à la liste sauvegardée en local
    }

    public void supprimerProjet(Projet projet)
    {
        //Supprime un projet à la liste sauvegardée en local
    }

    public void updateProjet(Projet projet)
    {
        //Modifie un projet sélectionnée à la liste sauvegardée en local
    }

    @Override
    public ArrayList<Projet> getProjetDUnProspect(ArrayList<Projet> projetListe, String nomProspect) {

        ArrayList<Projet> projetDuProspect = new ArrayList<>();
        Log.d("nbProjetsss", String.valueOf(projetListe.size()));
        for(Projet projet : projetListe) {
            Log.d("nomProspect", nomProspect);
            Log.d("nomProspecttt", projet.getNomProspect());
            if(projet.getNomProspect().equals(nomProspect)) {
                projetDuProspect.add(projet);
            }
        }
        return projetDuProspect;
    }
}