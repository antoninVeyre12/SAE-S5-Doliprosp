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

    private List<Salon> showList;


    public ShowAdapter(List<Salon> showList) {
        this.showList = showList;
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
            Salon show = showList.get(position);
            holder.show_name.setText(show.getNom());
        }
    }

    @Override
    public int getItemCount() {
        // Limite le nombre d'éléments affichés à 6 (2 lignes)
        return Math.min(showList.size(), 6);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView show_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            show_name = itemView.findViewById(R.id.show_name);
        }
    }
}