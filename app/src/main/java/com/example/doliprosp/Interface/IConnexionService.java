package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Model.Utilisateur;

public interface IConnexionService {

    public void connexion(String url, String userName, String motDePasse, Context context, ConnexionCallBack connexionCallBack);

    public String chiffrementApiKey(String apiKey);
    public Utilisateur getNouvelUtilisateur();


}
