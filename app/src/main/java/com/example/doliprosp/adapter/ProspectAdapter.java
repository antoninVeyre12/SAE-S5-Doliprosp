package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProspectAdapter extends RecyclerView.Adapter<ProspectAdapter.MyViewHolder> {

    private List<Prospect> prospectListe;

    public ProspectAdapter(List<Prospect> prospectListe) {
        this.prospectListe = prospectListe;

    }


    @NonNull
    @Override
    public ProspectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospect, parent, false);
        return new ProspectAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProspectAdapter.MyViewHolder holder, int position) {
        Prospect prospect = prospectListe.get(position);

        holder.nom.setText(prospect.getNom());
        holder.prenom.setText(prospect.getPrenom());

        String imageName = prospect.getImage();
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());

        // On affiche l'image

    }

    @Override
    public int getItemCount() {
        // Limite le nombre d'éléments affichés à 15 (5 lignes)
        return Math.min(prospectListe.size(), 15);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView prenom;
        public TextView nom;
        public ImageView icone;
        public MyViewHolder(View itemView) {
            super(itemView);
            prenom = itemView.findViewById(R.id.prenom);
            nom = itemView.findViewById(R.id.nom);
            icone = itemView.findViewById(R.id.icone);
        }
    }
}
