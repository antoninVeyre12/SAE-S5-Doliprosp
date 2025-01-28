package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class MyShowAdapter extends RecyclerView.Adapter<MyShowAdapter.MyViewHolder> implements Serializable {

    private List<Salon> salonListe;
    private ISalonService salonService;

    private OnItemClickListener onItemClickListener;
    private MesSalonsViewModel mesSalonsViewModel;
    private SalonsViewModel salonsViewModel;

    // Constructeur pour initialiser la liste des shows et le listener
    public MyShowAdapter(List<Salon> salonList, OnItemClickListener onItemClickListener, MesSalonsViewModel mesSalonsViewModel, SalonsViewModel salonsViewModel) {
        this.salonListe = salonList;
        this.onItemClickListener = onItemClickListener;
        this.mesSalonsViewModel = mesSalonsViewModel;
        this.salonsViewModel = salonsViewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_show, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Salon salon = salonListe.get(position);
        holder.salon_nom.setText(salon.getNom());
        salonService = new SalonService();

        // Clic sur bouton supprimer
        holder.salon_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
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
                onItemClickListener.onSelectClick(position, salonListe);

            }
        });
        holder.salon_nom.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, salonListe);

            }
        });

        // Clic sur bouton modifier
        holder.salon_modifier.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                // Créer un conteneur pour les vues
                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 20, 50, 20);

                // Créer l'EditText
                EditText editText = new EditText(v.getContext());
                editText.setHint("Entrez le nouveau nom du salon");
                layout.addView(editText);

                TextView erreurNom = new TextView(v.getContext());
                erreurNom.setTextSize(14);
                layout.addView(erreurNom);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Modifier le salon")
                        .setView(layout)
                        .setCancelable(false)
                        .setPositiveButton("Confirmer", null)
                        .setNegativeButton("Annuler", (dialog, which) -> {
                            dialog.dismiss();
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                // Validation du nom quand l'utilisateur appuie sur "Confirmer"
                Button confirmButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                confirmButton.setOnClickListener(v1 -> {
                    String nouveauNom = editText.getText().toString();
                    erreurNom.setTextColor(Color.RED);
                    erreurNom.setVisibility(View.GONE);

                    if (nouveauNom.length() <= 2 || nouveauNom.length() >= 50) {
                        erreurNom.setText(R.string.erreur_nom_salon_longueur);
                        erreurNom.setVisibility(View.VISIBLE);
                    } else if (salonService.salonExiste(nouveauNom, salonsViewModel, mesSalonsViewModel)) {
                        erreurNom.setText(R.string.erreur_nom_salon_existe);
                        erreurNom.setVisibility(View.VISIBLE);
                    } else {
                        onItemClickListener.onModifyClick(position, nouveauNom);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return salonListe.size();
    }

    // Interface pour le gestionnaire de clics
    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onSelectClick(int position, List<Salon> salonList);
        void onModifyClick(int position, String nouveauNom);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView salon_nom;
        public ImageButton salon_supprimer;
        public ImageButton salon_modifier;

        public FrameLayout salon_case;


        public MyViewHolder(View itemView) {
            super(itemView);
            salon_nom = itemView.findViewById(R.id.salon_nom);
            salon_supprimer = itemView.findViewById(R.id.salon_supprimer);
            salon_modifier = itemView.findViewById(R.id.salon_modifier);
            salon_case = itemView.findViewById(R.id.salon_case);

        }
    }
}