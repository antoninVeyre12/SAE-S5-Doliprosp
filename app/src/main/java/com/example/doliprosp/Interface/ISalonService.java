package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;

public interface ISalonService {


    public void getSalonsEnregistres(Context context, String recherche, Utilisateur utilisateur, Outils.APIResponseCallbackArrayTest callback);
    public void envoyerSalon(SalonService salon);
    public boolean salonExiste(String nomRecherche, SalonsViewModel salonsViewModel, MesSalonsViewModel mesSalonsViewModel);

}
