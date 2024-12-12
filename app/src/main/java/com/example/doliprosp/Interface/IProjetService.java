package com.example.doliprosp.Interface;

import com.example.doliprosp.Model.Projet;
import com.example.doliprosp.Services.ProjetService;

public interface IProjetService {

    public void ajouterProjet(Projet projet);
    public void supprimerProjet(Projet projet);

    public void updateProjet(Projet projet);
}
