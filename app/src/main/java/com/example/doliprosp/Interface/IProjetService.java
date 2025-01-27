package com.example.doliprosp.Interface;

import com.example.doliprosp.Modele.Projet;

import java.util.ArrayList;
import java.util.List;

public interface IProjetService {

    public void ajouterProjet(Projet projet);
    public void supprimerProjet(Projet projet);

    public void updateProjet(Projet projet);

    List<Projet> getProjetDUnProspect(ArrayList<Projet> projetListe, String nom, String nom1);
}
