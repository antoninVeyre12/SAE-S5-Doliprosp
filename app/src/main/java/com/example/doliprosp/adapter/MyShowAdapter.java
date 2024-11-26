package com.example.doliprosp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Show;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyShowAdapter extends RecyclerView.Adapter<MyShowAdapter.MyViewHolder> {

    private List<Show> showList;
    private OnItemClickListener onItemClickListener;

    // Constructeur pour initialiser la liste des shows et le listener
    public MyShowAdapter(List<Show> showList, OnItemClickListener onItemClickListener) {
        this.showList = showList;
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
        Show show = showList.get(position);
        holder.show_name.setText(show.getName());

        holder.show_delete.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    // Interface pour le gestionnaire de clics
    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView show_name;
        public ImageButton show_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            show_name = itemView.findViewById(R.id.show_name);
            show_delete = itemView.findViewById(R.id.show_delete);
        }
    }
}
