package com.example.doliprosp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.modeles.Salon;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// La classe SalonsAdapter est un adaptateur pour lier une liste de salons à un RecyclerView.
// Elle permet d'afficher une liste de salons avec leur nom et gère les événements de clic sur chaque élément.
public class SalonsAdapter extends RecyclerView.Adapter<SalonsAdapter.MyViewHolder> {

    // Liste des salons à afficher dans le RecyclerView
    private List<Salon> salonListe;

    // interfaces pour gérer les événements de clic sur les items
    private SalonsAdapter.OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur qui prend la liste des salons et un gestionnaire de clics
    public SalonsAdapter(List<Salon> salonList, SalonsAdapter.OnItemClickListener onItemClickListener) {
        this.salonListe = salonList;
        this.onItemClickListener = onItemClickListener;
    }

    // Cette méthode est appelée pour créer un nouvel item dans le RecyclerView.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_salon pour chaque élément de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salon, parent, false);
        return new MyViewHolder(view); // Retourne un ViewHolder contenant la vue gonflée
    }

    // Cette méthode est appelée pour lier les données du salon aux vues correspondantes.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Limite l'affichage à 6 éléments
        if (position < 6) {
            Salon salon = salonListe.get(position); // Récupère le salon à la position donnée dans la liste

            // Remplie le TextView avec le nom du salon
            holder.salon_nom.setText(salon.getNom());

            // Définit un écouteur de clic sur la FrameLayout contenant l'item
            holder.salon_case.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelectClick(position, salonListe); // Déclenche le clic sur le salon
                }
            });

            // Définit également un écouteur de clic sur le nom du salon
            holder.salon_nom.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelectClick(position, salonListe); // Déclenche le clic sur le nom du salon
                }
            });
        }
    }

    public void setSalonsList(List<Salon> salonsListe) {
        this.salonListe = salonsListe;
        notifyDataSetChanged();
    }


    // Cette méthode retourne le nombre d'éléments à afficher dans le RecyclerView.
    // Limite à un maximum de 6 éléments affichés.
    @Override
    public int getItemCount() {
        return Math.min(salonListe.size(), 6); // Limite le nombre d'éléments à 6
    }

    // interfaces pour le gestionnaire de clics. Permet d'exécuter des actions lors du clic sur un item.
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Salon> salonList); // Méthode pour gérer le clic
    }

    // ViewHolder pour chaque item du RecyclerView.
    // Contient les vues d'un item : le nom du salon et la case contenant le salon.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView salon_nom; // Référence au TextView pour le nom du salon
        public FrameLayout salon_case; // Référence à la FrameLayout de l'item

        // Constructeur pour récupérer les vues par leur ID
        public MyViewHolder(View itemView) {
            super(itemView);
            salon_nom = itemView.findViewById(R.id.salon_nom); // Trouve le TextView du nom
            salon_case = itemView.findViewById(R.id.salon_case); // Trouve la FrameLayout contenant l'item
        }
    }
}