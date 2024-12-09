package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;

public interface ISalonService {


    public void getSalonsEnregistres(Context context, Utilisateur utilisateur, Outils.APIResponseCallbackArrayTest callback);
    public void envoyerSalon(SalonService salon);

}
