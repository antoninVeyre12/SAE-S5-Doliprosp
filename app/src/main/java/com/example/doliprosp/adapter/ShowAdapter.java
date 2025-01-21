package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {

    private List<Salon> salonListe;

    private ShowAdapter.OnItemClickListener onItemClickListener;


    public ShowAdapter(List<Salon> salonList, ShowAdapter.OnItemClickListener onItemClickListener) {
        this.salonListe = salonList;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salon, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position < 6) {
            Salon salon = salonListe.get(position);
            holder.salon_nom.setText(salon.getNom());
            holder.salon_case.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelectClick(position, salonListe);

                }
            });
            holder.salon_nom.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelectClick(position, salonListe);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // Limite le nombre d'éléments affichés à 6 (2 lignes)
        return Math.min(salonListe.size(), 6);
    }

    // Interface pour le gestionnaire de clics
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Salon> salonList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView salon_nom;
        public FrameLayout salon_case;
        public MyViewHolder(View itemView) {
            super(itemView);
            salon_nom = itemView.findViewById(R.id.salon_nom);
            salon_case = itemView.findViewById(R.id.salon_case);
        }
    }
}