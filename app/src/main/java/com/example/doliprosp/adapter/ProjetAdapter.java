package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.MyViewHolder> implements Serializable {

    private List<Projet> projetListe;

    public ProjetAdapter(List<Projet> projetListe) {
        this.projetListe = projetListe;

    }


    @NonNull
    @Override
    public ProjetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjetAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Projet projet = projetListe.get(position);

        holder.titre.setText(projet.getTitre());

        /*holder.projet_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.confirmation_suppresion_projet)
                        .setPositiveButton("Oui", (dialog, which) -> {
                            // Si l'utilisateur confirme, appeler la méthode de suppression
                            onItemClickListener.onDeleteClick(position);
                        })
                        .setNegativeButton("Non", (dialog, which) -> {
                            // L'utilisateur annule la suppression
                            dialog.dismiss();
                        })
                        .show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        // Limite le nombre d'éléments affichés à 15 (5 lignes)
        return Math.min(projetListe.size(), 15);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titre;
        public ImageView icone;
        public MyViewHolder(View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.project_name);
        }
    }
}
