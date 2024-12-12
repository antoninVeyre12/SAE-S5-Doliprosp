package com.example.doliprosp.Interface;

import com.example.doliprosp.Model.Utilisateur;

public interface ConnexionCallBack {

    void onSuccess(Utilisateur nouvelUtilisateur);
    void onError(String errorMessage);

}
