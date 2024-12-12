package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.viewModel.MesSalonViewModel;
import com.example.doliprosp.viewModel.SalonViewModel;

import java.util.ArrayList;

public interface ISalonService {


    public void getSalonsEnregistres(Context context, String recherche, Utilisateur utilisateur, Outils.APIResponseCallbackArrayTest callback);
    public void envoyerSalon(SalonService salon);
    public boolean salonExiste(String nomRecherche, SalonViewModel salonViewModel, MesSalonViewModel mesSalonViewModel);

}
