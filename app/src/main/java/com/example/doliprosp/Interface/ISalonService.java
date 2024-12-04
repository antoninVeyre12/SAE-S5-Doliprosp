package com.example.doliprosp.Interface;

import android.content.Context;

import com.example.doliprosp.Service.Outils;
import com.example.doliprosp.Service.SalonService;

import java.util.ArrayList;
import java.util.UUID;

public interface ISalonService {


    public void getSalonsEnregistres(Context context, Outils.APIResponseCallbackArrayTest callback);
    public void envoyerSalon(SalonService salon);

}
