package com.example.doliprosp.Services;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Model.Prospect;

import java.util.UUID;

public class ProspectService implements IProspectService {
    public ProspectService(){}

    public void ajouterProspect(Prospect prospect) {

    }

    public void supprimerProspect(Prospect prospect) {

    }


    public Prospect getProspectEnregistre(String recherche) {
        return null; //Bouchon
    }

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String mail, String numeroTelephone,
                               Boolean estClient, UUID idProspect)
    {

    }


}