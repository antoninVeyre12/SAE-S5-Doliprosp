package com.example.doliprosp.Interface;

import com.example.doliprosp.Modele.Prospect;

import java.util.UUID;

public interface IProspetService {
    public void ajouterProspect(Prospect prospect);
    public void supprimerProspect(Prospect prospect);

    public Prospect getProspectEnregistre(String recherche);

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String email, String numeroTelephone, Boolean estClient, UUID idProspet);

}
