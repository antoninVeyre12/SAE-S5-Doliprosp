package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {

    private List<Salon> salonList;

    private ShowAdapter.OnItemClickListener onItemClickListener;


    public ShowAdapter(List<Salon> salonList) {
        this.salonList = salonList;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position < 6) {
            Salon salon = salonList.get(position);
            holder.show_name.setText(salon.getNom());
            holder.salon_case.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelectClick(position,salonList);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // Limite le nombre d'éléments affichés à 6 (2 lignes)
        return Math.min(salonList.size(), 6);
    }

    // Interface pour le gestionnaire de clics
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Salon> salonList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView show_name;
        public View salon_case;
        public MyViewHolder(View itemView) {
            super(itemView);
            show_name = itemView.findViewById(R.id.show_name);
            salon_case = itemView.findViewById(R.id.salon_case);
        }
    }
}