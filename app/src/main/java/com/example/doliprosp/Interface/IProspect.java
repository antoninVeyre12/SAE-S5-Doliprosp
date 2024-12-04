package com.example.doliprosp.Interface;

import com.example.doliprosp.Service.ProspectService;

import java.util.UUID;

public interface IProspect {
    public void ajouterProspect(ProspectService prospect);
    public void supprimerProspect(ProspectService prospect);

    public void envoyerProspect(ProspectService prospect);
    public ProspectService getProspectEnregistre(String prenom, String nom, int codePostal, String ville,
                                                 String adresse, String email, String numeroTelephone);

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String email, String numeroTelephone, Boolean estClient);

    public UUID getIdSalon();

    public UUID getIdProspect();

    public String getPrenom();

    public String getNom();

    public int getCodePostal();

    public String getEmail();

    public String getNumeroTelephone();

    public Boolean getEstClient();

    public void setPrenom(String prenom);

    public void setNom(String nom);

    public void setCodePostal(int codePostal);

    public void setVille(String ville);

    public void setAdresse(String adresse);

    public void setEmail(String email);

    public void setNumeroTelephone(String numeroTelephone);

    public void setEstClient(Boolean estClient);


}
