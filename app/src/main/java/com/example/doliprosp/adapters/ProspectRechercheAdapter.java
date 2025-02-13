package com.example.doliprosp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.modeles.Prospect;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * L'adaptateur pour lier une liste de prospects à un RecyclerView.
 * Il permet d'afficher des informations détaillées sur chaque prospect dans un item de la liste.
 * Cette classe implémente Serializable pour permettre la sérialisation de l'adaptateur.
 */
public class ProspectRechercheAdapter extends RecyclerView.Adapter<ProspectRechercheAdapter.MyViewHolder> implements Serializable {

    /**
     * Liste des prospects à afficher dans le RecyclerView.
     */
    private List<Prospect> prospectListe;

    /**
     * interfaces pour gérer les événements de clic sur les items du RecyclerView.
     */
    private ProspectRechercheAdapter.OnItemClickListener onItemClickListener;

    /**
     * Constructeur de l'adaptateur.
     *
     * @param prospectListe       la liste des prospects à afficher
     * @param onItemClickListener un gestionnaire d'événements de clic sur les items
     */
    public ProspectRechercheAdapter(List<Prospect> prospectListe, ProspectRechercheAdapter.OnItemClickListener onItemClickListener) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Crée une nouvelle vue pour un item dans le RecyclerView.
     *
     * @param parent   le parent dans lequel l'élément va être ajouté
     * @param viewType le type de vue (en général, inutile dans ce cas)
     * @return un ViewHolder contenant la vue gonflée
     */
    @NonNull
    @Override
    public ProspectRechercheAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recheche_prospect, parent, false);
        return new ProspectRechercheAdapter.MyViewHolder(view);
    }

    /**
     * Lie les données d'un prospect aux vues correspondantes dans un item du RecyclerView.
     *
     * @param holder   le ViewHolder contenant les vues de l'item
     * @param position la position du prospect dans la liste
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Prospect prospect = prospectListe.get(position);
        holder.nom.setText(prospect.getNom());
        holder.mail.setText(prospect.getMail());
        holder.tel.setText(prospect.getNumeroTelephone());
        holder.codePostal.setText(String.valueOf(prospect.getCodePostal()));
        holder.ville.setText(prospect.getVille());
        holder.adresse.setText(prospect.getAdresse());

        String nomComplet = prospect.getNom();
        String initiales = "";
        String[] splitNom = nomComplet.split(" ");
        if (splitNom.length > 0) {
            initiales += splitNom[0].substring(0, 1).toUpperCase();
            if (splitNom.length > 1) {
                initiales += splitNom[1].substring(0, 1).toUpperCase();
            }
        }

        holder.initiales.setText(initiales);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(prospect);
            }
        });
    }

    /**
     * Retourne le nombre total d'éléments dans la liste des prospects.
     *
     * @return le nombre d'éléments dans la liste
     */
    @Override
    public int getItemCount() {
        return prospectListe.size();
    }

    /**
     * interfaces pour gérer les événements de clic sur un item.
     * Permet de déclencher une action lorsqu'un item est sélectionné.
     */
    public interface OnItemClickListener {
        /**
         * Méthode appelée lorsqu'un item est sélectionné.
         *
         * @param prospect le prospect sélectionné
         */
        void onSelectClick(Prospect prospect);
    }

    /**
     * ViewHolder représentant une vue d'un item du RecyclerView.
     * Contient les vues pour afficher les informations d'un prospect.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public TextView mail;
        public TextView tel;
        public TextView adresse;
        public TextView codePostal;
        public TextView ville;
        public TextView initiales;

        /**
         * Constructeur du ViewHolder qui initialise les vues par leur ID.
         *
         * @param itemView la vue de l'élément à afficher
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.textViewNomPrenom);
            mail = itemView.findViewById(R.id.textViewEmail);
            tel = itemView.findViewById(R.id.textViewTelephone);
            adresse = itemView.findViewById(R.id.textViewAdresse);
            codePostal = itemView.findViewById(R.id.textViewCodePostal);
            ville = itemView.findViewById(R.id.textViewVille);
            initiales = itemView.findViewById(R.id.textViewInitiales);
        }
    }
}
