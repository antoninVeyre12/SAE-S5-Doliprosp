package com.example.doliprosp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// La classe ProspectAdapter est un adaptateur pour lier une liste de prospects à un RecyclerView.
// Elle implémente Serializable pour la sérialisation de l'adaptateur.
public class ProspectAdapter extends RecyclerView.Adapter<ProspectAdapter.MyViewHolder> implements Serializable {

    // Liste des prospects à afficher dans le RecyclerView
    private List<Prospect> prospectListe;

    // Interface pour gérer les événements de clic sur les items
    private ProspectAdapter.OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur qui prend la liste des prospects et un gestionnaire de clics
    public ProspectAdapter(List<Prospect> prospectListe, ProspectAdapter.OnItemClickListener onItemClickListener) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
    }

    // Cette méthode est appelée pour créer un nouvel item dans le RecyclerView.
    @NonNull
    @Override
    public ProspectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_prospect pour chaque élément de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospect, parent, false);
        return new ProspectAdapter.MyViewHolder(view); // Retourne un ViewHolder contenant la vue gonflée
    }

    // Cette méthode est appelée pour lier les données du prospect aux vues correspondantes.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Récupère le prospect à la position donnée dans la liste
        Prospect prospect = prospectListe.get(position);

        // Remplie le TextView avec le nom du prospect
        holder.nom.setText(prospect.getNom());

        // Définit un écouteur de clic sur l'icône
        holder.icone.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, prospectListe); // Déclenche le clic pour l'icône
            }
        });

        // Définit un écouteur de clic sur le nom du prospect
        holder.nom.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, prospectListe); // Déclenche le clic pour le nom du prospect
            }
        });

        Log.d("zzz", "eeeffffffffffee"); // Un message de log pour déboguer (peut être supprimé en production)

        // Partie commentée pour gérer l'affichage d'une image
        // String imageName = prospect.getImage();
        // int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        // Vous pouvez afficher l'image ici, si nécessaire.
    }

    // Cette méthode retourne le nombre d'éléments à afficher dans le RecyclerView.
    // Limite à un maximum de 15 éléments affichés.
    @Override
    public int getItemCount() {
        return Math.min(prospectListe.size(), 15); // Limite le nombre d'éléments à 15
    }

    // Interface pour le gestionnaire de clics. Permet d'exécuter des actions lors du clic sur un item.
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Prospect> prospectListe); // Méthode pour gérer le clic
    }

    // ViewHolder pour chaque item du RecyclerView.
    // Contient les vues d'un item : le nom du prospect et l'icône.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nom; // Référence au TextView pour le nom du prospect
        public ImageView icone; // Référence à l'ImageView pour l'icône du prospect

        // Constructeur pour récupérer les vues par leur ID
        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom); // Trouve le TextView du nom
            icone = itemView.findViewById(R.id.icone); // Trouve l'ImageView de l'icône
        }
    }
}