package com.example.doliprosp.adapters;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.interfaces.ISalonService;
import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.services.SalonService;
import com.example.doliprosp.viewmodels.MesSalonsViewModel;
import com.example.doliprosp.viewmodels.SalonsViewModel;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// Adapter pour la liste des salons dans un RecyclerView
public class MesSalonsAdapter extends RecyclerView.Adapter<MesSalonsAdapter.MyViewHolder> implements Serializable {

    // Liste des salons à afficher
    private List<Salon> salonListe;
    private ISalonService salonService;

    // Listener pour gérer les actions sur chaque item de la liste
    private transient OnItemClickListener onItemClickListener;
    private MesSalonsViewModel mesSalonsViewModel;
    private SalonsViewModel salonsViewModel;

    // Constructeur pour initialiser la liste des salons et le listener
    public MesSalonsAdapter(List<Salon> salonList, OnItemClickListener onItemClickListener, MesSalonsViewModel mesSalonsViewModel, SalonsViewModel salonsViewModel) {
        this.salonListe = salonList;
        this.onItemClickListener = onItemClickListener;
        this.mesSalonsViewModel = mesSalonsViewModel;
        this.salonsViewModel = salonsViewModel;
    }

    // Crée une nouvelle vue pour un item de la liste
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_show, parent, false);
        return new MyViewHolder(view);
    }

    // Remplit l'item de la vue avec les données du salon
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Salon salon = salonListe.get(position);
        holder.salonNom.setText(salon.getNom());
        salonService = new SalonService();

        // Clic sur le bouton supprimer
        holder.salonSupprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.confirmation_suppresion_salon)
                        .setPositiveButton("Oui", (dialog, which) ->
                            // Si l'utilisateur confirme, appeler la méthode de suppression
                            onItemClickListener.onDeleteClick(position)
                        )
                        .setNegativeButton("Non", (dialog, which) ->
                            // L'utilisateur annule la suppression
                            dialog.dismiss()
                        )
                        .show();
            }
        });

        // Clic sur la case ou le nom du salon
        holder.salonCase.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, salonListe);
            }
        });
        holder.salonNom.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, salonListe);
            }
        });

        // Clic sur le bouton modifier
        holder.salonModifier.setOnClickListener(v ->
            afficherDialogModification(v, salon, position)
        );
    }

    private void afficherDialogModification(View v, Salon salon, int position) {
        if (onItemClickListener != null) {

            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_create_show, null);

            EditText editTextSalon = layout.findViewById(R.id.editTextTitle);
            Button btnModifier = layout.findViewById(R.id.buttonSubmit);
            Button btnAnnuler = layout.findViewById(R.id.buttonCancel);
            TextView erreurNom = layout.findViewById(R.id.erreur_nom);

            // Remplir les EditText avec les valeurs actuelles
            editTextSalon.setText(salon.getNom());

            btnModifier.setText("Modifier");

            // Créer une alerte pour confirmer la modification
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                    .setTitle("Modifier le salon")
                    .setView(layout)
                    .setCancelable(false);


            AlertDialog dialog = builder.create();
            dialog.show();

            btnAnnuler.setOnClickListener(v1 -> dialog.dismiss());
            // Validation du nom lorsque l'utilisateur appuie sur "Confirmer"
            btnModifier.setOnClickListener(v1 -> {
                String nouveauNom = editTextSalon.getText().toString();
                erreurNom.setTextColor(Color.RED);
                erreurNom.setVisibility(View.GONE);

                // Vérification de la longueur du nom et de son existence
                if (nouveauNom.length() <= 2 || nouveauNom.length() >= 50) {
                    erreurNom.setText(R.string.erreur_nom_salon_longueur);
                    erreurNom.setVisibility(View.VISIBLE);
                } else if (salonService.salonExiste(nouveauNom, salonsViewModel, mesSalonsViewModel) && !nouveauNom.equals(salon.getNom())) {
                    erreurNom.setText(R.string.erreur_nom_salon_existe);
                    erreurNom.setVisibility(View.VISIBLE);
                } else {
                    // Si tout est valide, on modifie le nom du salon
                    onItemClickListener.onModifyClick(position, nouveauNom);
                    dialog.dismiss();
                }
            });

        }
    }

    public void setSalonsList(List<Salon> salonsList) {
        this.salonListe = salonsList;
        notifyDataSetChanged();
    }

    // Retourne le nombre d'items dans la liste
    @Override
    public int getItemCount() {
        return salonListe.size();
    }

    // interfaces pour gérer les actions des items (supprimer, sélectionner, modifier)
    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onSelectClick(int position, List<Salon> salonList);

        void onModifyClick(int position, String nouveauNom);
    }

    // Vue qui représente un item de la liste des salons
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView salonNom;
        private ImageButton salonSupprimer;
        private ImageButton salonModifier;
        private FrameLayout salonCase;

        public MyViewHolder(View itemView) {
            super(itemView);
            salonNom = itemView.findViewById(R.id.salon_nom);
            salonSupprimer = itemView.findViewById(R.id.salon_supprimer);
            salonModifier = itemView.findViewById(R.id.salon_modifier);
            salonCase = itemView.findViewById(R.id.salon_case);
        }
    }
}