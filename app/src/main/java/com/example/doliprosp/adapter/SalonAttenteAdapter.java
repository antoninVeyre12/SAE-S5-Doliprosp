package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.viewModel.MesProspectViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Adapter pour la liste des salons dans un RecyclerView
public class SalonAttenteAdapter extends RecyclerView.Adapter<SalonAttenteAdapter.MyViewHolder> implements Serializable {

    // Liste des salons à afficher
    private List<Salon> salonListe;
    private List<Integer> salonsSelectionnesIndices;
    private ISalonService salonService;
    private IProspectService prospectService;

    // Listener pour gérer les actions sur chaque item de la liste
    private MesProspectViewModel mesProspectViewModel;

    // Constructeur pour initialiser la liste des salons et le listener
    public SalonAttenteAdapter(ArrayList<Salon> salonList, MesProspectViewModel mesProspectViewModel) {
        this.salonListe = salonList;
        this.mesProspectViewModel = mesProspectViewModel;

//        this.salonsViewModel = salonsViewModel;
    }

    // Crée une nouvelle vue pour un item de la liste
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salon_attente, parent, false);
        return new MyViewHolder(view);
    }

    // Remplit l'item de la vue avec les données du salon
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        salonService = new SalonService();
        prospectService = new ProspectService();

        Salon salon = salonListe.get(position);
        holder.salon_nom.setText(salon.getNom());
        holder.nb_prospect.setText(String.valueOf(prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salon.getNom()).size()));
        holder.salon_checkbox.setChecked(salon.estSelectionne());


        holder.salon_checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            salon.setEstSelectionne(isChecked);
            holder.itemView.post(() -> notifyItemChanged(position));

        });
    }


    // Retourne le nombre d'items dans la liste
    @Override
    public int getItemCount() {
        return salonListe.size();
    }

    public void selectAllSalons() {
        for (Salon salon : salonListe) {
            salon.setEstSelectionne(true);
        }
        notifyDataSetChanged();
    }


    // Vue qui représente un item de la liste des salons
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView salon_nom;
        public TextView nb_prospect;
        public TextView nb_projet;

        public CheckBox salon_checkbox;
        public FrameLayout salon_case;

        public MyViewHolder(View itemView) {
            super(itemView);
            salon_checkbox = itemView.findViewById(R.id.salon_checkbox);
            salon_nom = itemView.findViewById(R.id.salon_nom);
            salon_case = itemView.findViewById(R.id.salon_case);
            nb_prospect = itemView.findViewById(R.id.nb_prospect);
            nb_projet = itemView.findViewById(R.id.nb_projet);
        }
    }
}