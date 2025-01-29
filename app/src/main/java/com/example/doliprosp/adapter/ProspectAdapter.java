package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.MesSalonsViewModel;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProspectAdapter extends RecyclerView.Adapter<ProspectAdapter.MyViewHolder> implements Serializable {

    private List<Prospect> prospectListe;
    private MesProspectViewModel mesProspectViewModel;

    private ProspectAdapter.OnItemClickListener onItemClickListener;
    public ProspectAdapter(List<Prospect> prospectListe, ProspectAdapter.OnItemClickListener onItemClickListener,
                           MesProspectViewModel mesProspectViewModel) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
        this.mesProspectViewModel = mesProspectViewModel;
    }


    @NonNull
    @Override
    public ProspectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospect, parent, false);
        return new ProspectAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Prospect prospect = prospectListe.get(position);
        holder.nom.setText(prospect.getNom());
        holder.icone.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, prospectListe);
            }
        });
        holder.nom.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, prospectListe);

            }
        });
        holder.prospect_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.confirmation_suppresion_prospect)
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

        //String imageName = prospect.getImage();
        //int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        // On affiche l'image
    }

    @Override
    public int getItemCount() {
        // Limite le nombre d'éléments affichés à 15 (5 lignes)
        return Math.min(prospectListe.size(), 15);
    }

    // Interface pour le gestionnaire de clics
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Prospect> prospectListe);
        void onDeleteClick(int position);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public ImageView icone;
        public ImageButton prospect_supprimer;

        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            icone = itemView.findViewById(R.id.icone);
            prospect_supprimer = itemView.findViewById(R.id.prospect_supprimer);

        }
    }
}
