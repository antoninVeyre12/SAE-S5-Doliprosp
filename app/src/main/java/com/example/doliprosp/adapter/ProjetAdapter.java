package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Déclaration de la classe ProjetAdapter qui hérite de RecyclerView.Adapter<ProjetAdapter.MyViewHolder>
// et implémente Serializable pour permettre la sérialisation de l'adaptateur (utile si vous voulez passer
// cet objet entre différentes activités ou fragments).
public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.MyViewHolder> implements Serializable {

    // Liste des projets qui sera affichée dans le RecyclerView
    private List<Projet> projetListe;
    private ProjetAdapter.OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur qui prend la liste des projets à afficher
    public ProjetAdapter(List<Projet> projetListe, ProjetAdapter.OnItemClickListener onItemClickListener) {
        this.projetListe = projetListe;
        this.onItemClickListener = onItemClickListener;

    }

    // Cette méthode est appelée pour créer une nouvelle vue (item) à afficher dans le RecyclerView.
    @NonNull
    @Override
    public ProjetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_project qui sera utilisé pour chaque item de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjetAdapter.MyViewHolder(view); // Retourne un ViewHolder qui contient la vue gonflée
    }

    // Cette méthode est appelée pour lier les données aux éléments de la vue (d'un item spécifique).
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Récupère l'objet Projet à la position donnée dans la liste
        Projet projet = projetListe.get(position);

        // Remplit le TextView avec le titre du projet
        holder.titre.setText(projet.getTitre());

        holder.projet_supprimer.setOnClickListener(v -> {
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
        });
    }

    // Retourne le nombre d'éléments à afficher dans le RecyclerView.
    // Ici, on limite le nombre d'éléments affichés à 15 (5 lignes, donc 3 éléments par ligne).
    @Override
    public int getItemCount() {
        return Math.min(projetListe.size(), 15); // Affiche au maximum 15 projets
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);

    }
    // Déclaration du ViewHolder qui représente chaque item de la liste
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titre;
        public ImageView icone;
        public ImageButton projet_supprimer;
        public MyViewHolder(View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.projet_name);
            projet_supprimer = itemView.findViewById(R.id.projet_supprimer);
        }
    }
}
