package com.example.doliprosp.Interface;

import com.example.doliprosp.Model.Prospet;
import com.example.doliprosp.Services.ProspetService;

import java.util.UUID;

public interface IProspetService {
    public void ajouterProspect(Prospet prospet);
    public void supprimerProspect(Prospet prospet);

    public Prospet getProspectEnregistre(String recherche);

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String email, String numeroTelephone, Boolean estClient, UUID idProspet);

}
