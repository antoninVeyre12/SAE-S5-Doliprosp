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

public class ProspectAdapter extends RecyclerView.Adapter<ProspectAdapter.MyViewHolder> implements Serializable {

    private List<Prospect> prospectListe;

    private ProspectAdapter.OnItemClickListener onItemClickListener;
    public ProspectAdapter(List<Prospect> prospectListe, ProspectAdapter.OnItemClickListener onItemClickListener) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
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
        Log.d("zzz","eeeffffffffffee");
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
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public ImageView icone;

        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            icone = itemView.findViewById(R.id.icone);
        }
    }
}
