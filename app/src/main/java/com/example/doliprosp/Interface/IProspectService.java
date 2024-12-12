package com.example.doliprosp.Interface;

import com.example.doliprosp.Model.Prospect;
import com.example.doliprosp.Services.ProspectService;

import java.util.UUID;

public interface IProspectService {
    public void ajouterProspect(Prospect prospect);
    public void supprimerProspect(Prospect prospect);

    public Prospect getProspectEnregistre(String recherche);

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String email, String numeroTelephone, Boolean estClient, UUID idProspect);

}
