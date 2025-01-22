package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.Services.Outils;

import java.util.UUID;

public interface IProspectService {
    public void ajouterProspect(Prospect prospect);
    public void supprimerProspect(Prospect prospect);

    public void prospectClientExiste(Context context, String recherche, String champ, String tri, Utilisateur utilisateur, Outils.APIResponseCallbackArrayProspect callback);

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String email, String numeroTelephone, Boolean estClient, UUID idProspet);

}
