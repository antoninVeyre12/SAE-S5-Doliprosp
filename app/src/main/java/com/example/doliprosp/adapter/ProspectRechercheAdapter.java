package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.util.List;

// La classe ProspectAdapter est un adaptateur pour lier une liste de prospects à un RecyclerView.
// Elle implémente Serializable pour la sérialisation de l'adaptateur.
public class ProspectRechercheAdapter extends RecyclerView.Adapter<ProspectRechercheAdapter.MyViewHolder> implements Serializable {

    // Liste des prospects à afficher dans le RecyclerView
    private List<Prospect> prospectListe;

    // Interface pour gérer les événements de clic sur les items
    private ProspectRechercheAdapter.OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur qui prend la liste des prospects et un gestionnaire de clics
    public ProspectRechercheAdapter(List<Prospect> prospectListe, ProspectRechercheAdapter.OnItemClickListener onItemClickListener) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
    }

    // Cette méthode est appelée pour créer un nouvel item dans le RecyclerView.
    @NonNull
    @Override
    public ProspectRechercheAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_prospect pour chaque élément de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recheche_prospect, parent, false);
        return new ProspectRechercheAdapter.MyViewHolder(view); // Retourne un ViewHolder contenant la vue gonflée
    }

    // Cette méthode est appelée pour lier les données du prospect aux vues correspondantes.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Récupère le prospect à la position donnée dans la liste
        Prospect prospect = prospectListe.get(position);

        // Remplie le TextView avec le nom du prospect
        holder.nom.setText(prospect.getNom());
        holder.mail.setText(prospect.getMail());
        holder.tel.setText(prospect.getNumeroTelephone());

        // Définit un écouteur de clic sur le nom du prospect
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(prospect); // Déclenche le clic pour le nom du prospect
            }
        });


        //String imageName = prospect.getImage();
        //int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        // On affiche l'image
    }

    // Cette méthode retourne le nombre d'éléments à afficher dans le RecyclerView.
    @Override
    public int getItemCount() {
        return prospectListe.size();
    }

    // Interface pour le gestionnaire de clics. Permet d'exécuter des actions lors du clic sur un item.
    public interface OnItemClickListener {
        void onSelectClick(Prospect prospect);

        void onDeleteClick(int position);

    }

    // ViewHolder pour chaque item du RecyclerView.
    // Contient les vues d'un item : le nom du prospect et l'icône.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public TextView mail;
        public TextView tel;

        // Constructeur pour récupérer les vues par leur ID
        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.textViewNomPrenom);
            mail = itemView.findViewById(R.id.textViewEmail);
            tel = itemView.findViewById(R.id.textViewTelephone);

        }
    }
}