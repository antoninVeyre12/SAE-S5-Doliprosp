package com.example.doliprosp.Services;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.Modele.Projet;

import java.util.ArrayList;

public class ProjetService implements IProjetService {

    public ProjetService() {
    }

    public void updateProjet(Projet projet, String titre, String description, String dateDebut) {
        projet.setTitre(titre);
        projet.setDescription(description);
        projet.setDateDebut(dateDebut);

        // Log.d("Date attribut : ", dateDebut);
        // Log.d("Date result : ", projet.getDateDebut());
    }

    @Override
    public ArrayList<Projet> getProjetDUnProspect(ArrayList<Projet> projetListe, String nomProspect) {

        ArrayList<Projet> projetDuProspect = new ArrayList<>();
        for (Projet projet : projetListe) {
            if (projet.getNomProspect().equals(nomProspect)) {
                projetDuProspect.add(projet);
            }
        }
        return projetDuProspect;
    }
}