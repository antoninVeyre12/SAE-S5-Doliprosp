package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.treatment.Show;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {

    private List<Show> showList;


    public ShowAdapter(List<Show> showList) {
        this.showList = showList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, parent, false);
        return new MyViewHolder(view);

    }

    // Remplit le ViewHolder avec les données
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Show show = showList.get(position);
        holder.show_name.setText(show.getName());
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView show_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            show_name = itemView.findViewById(R.id.show_name);
        }
    }
}