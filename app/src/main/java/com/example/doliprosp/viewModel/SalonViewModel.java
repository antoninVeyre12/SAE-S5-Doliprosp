package com.example.doliprosp.viewModel;

import com.example.doliprosp.Model.Salon;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class SalonViewModel extends ViewModel {
    private ArrayList<Salon> salonList = new ArrayList<>();

    public ArrayList<Salon> getSalonList() {
        return salonList;
    }

    public void addSalon(Salon salon) {
        salonList.add(salon);
    }

    public void removeSalon(Salon salon) {
        salonList.remove(salon);
    }
}