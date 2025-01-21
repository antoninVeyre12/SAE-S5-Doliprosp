package com.example.doliprosp.Interface;

import com.example.doliprosp.Modele.Projet;

public interface IProjetService {

    public void ajouterProjet(Projet projet);
    public void supprimerProjet(Projet projet);

    public void updateProjet(Projet projet);
}
