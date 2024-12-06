package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyShowAdapter extends RecyclerView.Adapter<MyShowAdapter.MyViewHolder> {

    private List<Salon> salonList;
    private OnItemClickListener onItemClickListener;

    // Constructeur pour initialiser la liste des shows et le listener
    public MyShowAdapter(List<Salon> salonList, OnItemClickListener onItemClickListener) {
        this.salonList = salonList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_show, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Salon salon = salonList.get(position);
        holder.salon_nom.setText(salon.getNom());

        // Définir l'événement de clic pour le bouton de suppression
        holder.salon_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                // Créer et afficher la boîte de dialogue de confirmation
                new AlertDialog.Builder(v.getContext())
                        .setMessage("Êtes-vous sûr de vouloir supprimer ce salon ? Si ce salon posséde des prospects et des projets ils seront aussi supprimé")
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
        holder.salon_case.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, salonList);

            }
        });
    }

    @Override
    public int getItemCount() {
        return salonList.size();
    }

    // Interface pour le gestionnaire de clics
    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onSelectClick(int position, List<Salon> salonList);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView salon_nom;
        public ImageButton salon_supprimer;
        public FrameLayout salon_case;


        public MyViewHolder(View itemView) {
            super(itemView);
            salon_nom = itemView.findViewById(R.id.salon_nom);
            salon_supprimer = itemView.findViewById(R.id.salon_supprimer);
            salon_case = itemView.findViewById(R.id.salon_case);

        }
    }
}