package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;

public interface ISalonService {


    public void getSalonsEnregistres(Context context, Outils.APIResponseCallbackArrayTest callback);
    public void envoyerSalon(SalonService salon);

}
