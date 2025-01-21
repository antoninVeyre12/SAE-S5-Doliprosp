package com.example.doliprosp.Interface;

import com.example.doliprosp.Modele.Utilisateur;

public interface ConnexionCallBack {

    void onSuccess(Utilisateur nouvelUtilisateur);
    void onError(String errorMessage);

}
