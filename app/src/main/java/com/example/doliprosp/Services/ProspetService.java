package com.example.doliprosp.Services;

import com.example.doliprosp.Interface.IProspetService;
import com.example.doliprosp.Model.Prospet;

import java.util.UUID;

public class ProspetService implements IProspetService {
    private UUID idProspect;
    private UUID idSalon;
    private String prenom;
    private String nom;
    private int codePostal;
    private String ville;
    private String adresse;
    private String email;
    private String numeroTelephone;
    private Boolean estClient;


    public ProspetService(){}

    public void ajouterProspect(Prospet prospet) {

    }

    public void supprimerProspect(Prospet prospet) {

    }


    public Prospet getProspectEnregistre(String recherche) {
        return null; //Bouchon
    }

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String mail, String numeroTelephone,
                               Boolean estClient, UUID idProspet)
    {

    }


}