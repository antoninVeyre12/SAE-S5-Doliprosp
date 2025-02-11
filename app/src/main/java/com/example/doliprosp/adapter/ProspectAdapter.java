package com.example.doliprosp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.util.List;

/**
 * Adaptateur pour lier une liste de prospects à un RecyclerView.
 * Gère l'affichage et les interactions avec chaque élément de la liste.
 */
public class ProspectAdapter extends RecyclerView.Adapter<ProspectAdapter.MyViewHolder> implements Serializable {

    private final String REGEX_MAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\" +
            ".[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^(0[1-9])(\\s?\\d{2}){4}$";
    private final List<Prospect> prospectListe;
    private final OnItemClickListener onItemClickListener;

    /**
     * Constructeur de l'adaptateur.
     *
     * @param prospectListe       Liste des prospects à afficher.
     * @param onItemClickListener Interface de gestion des clics.
     */
    public ProspectAdapter(List<Prospect> prospectListe,
                           OnItemClickListener onItemClickListener) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                           int viewType) {
        View vue =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospect, parent, false);
        return new MyViewHolder(vue);
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

        holder.prospect_modifier.setOnClickListener(v -> afficherDialogModification(v, prospect, position));

        holder.prospect_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.confirmation_suppression_prospect)
                        .setPositiveButton("Oui",
                                (dialog, which) -> onItemClickListener.onDeleteClick(position))
                        .setNegativeButton("Non",
                                (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(prospectListe.size(), 15);
    }

    /**
     * Interface pour la gestion des événements de clics sur les éléments de
     * la liste.
     */
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Prospect> prospectListe);

        void onDeleteClick(int position);

        void OnModifyClick(int position, String nouveauNomPrenom,
                           String nouveauMail, String nouveauTel,
                           String nouvelleAdresse, String nouvelleVille,
                           String nouveauCodePostal);
    }

    /**
     * ViewHolder pour chaque élément du RecyclerView.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public ImageView icone;
        public ImageButton prospect_supprimer;
        public ImageButton prospect_modifier;

        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom_prenom);
            icone = itemView.findViewById(R.id.icone);
            prospect_supprimer = itemView.findViewById(R.id.prospect_supprimer);
            prospect_modifier = itemView.findViewById(R.id.prospect_modifier);
        }
    }

    /**
     * Affiche un dialogue de modification d'un prospect.
     *
     * @param v        Vue déclencheuse.
     * @param prospect Prospect à modifier.
     * @param position Position du prospect dans la liste.
     */
    @SuppressLint("SetTextI18n")
    private void afficherDialogModification(View v, Prospect prospect,
                                            int position) {
        if (onItemClickListener != null) {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_create_prospect,
                    null);

            EditText editTextNomPrenom =
                    layout.findViewById(R.id.editTextNomPrenom);
            EditText editTextMail = layout.findViewById(R.id.editTextMail);
            EditText editTextPhone = layout.findViewById(R.id.editTextPhone);
            EditText editTextAdresse =
                    layout.findViewById(R.id.editTextAdresse);
            EditText editTextVille = layout.findViewById(R.id.editTextVille);
            EditText editTextCodePostal =
                    layout.findViewById(R.id.editTextCodePostal);
            Button btnModifier = layout.findViewById(R.id.buttonSubmit);
            Button btnAnnuler = layout.findViewById(R.id.buttonCancel);
            TextView erreurProspect = layout.findViewById(R.id.erreur_prospect);

            editTextNomPrenom.setText(prospect.getNom());
            editTextMail.setText(prospect.getMail());
            editTextPhone.setText(prospect.getNumeroTelephone());
            editTextAdresse.setText(prospect.getAdresse());
            editTextVille.setText(prospect.getVille());
            editTextCodePostal.setText(prospect.getCodePostal());

            btnModifier.setText("Modifier");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Modifier le prospect")
                            .setView(layout)
                            .setCancelable(false);

            AlertDialog dialog = builder.create();
            dialog.show();

            btnAnnuler.setOnClickListener(v1 -> dialog.dismiss());

            btnModifier.setOnClickListener(v1 -> {
                String nouveauNomPrenom =
                        editTextNomPrenom.getText().toString();
                String nouveauMail = editTextMail.getText().toString();
                String nouveauTel = editTextPhone.getText().toString();
                String nouvelleAdresse = editTextAdresse.getText().toString();
                String nouvelleVille = editTextVille.getText().toString();
                String nouveauCodePostal =
                        editTextCodePostal.getText().toString();

                if (nouveauNomPrenom.length() <= 2) {
                    erreurProspect.setText(R.string.erreur_nom_prospect_longueur);
                    erreurProspect.setVisibility(View.VISIBLE);
                } else if (!nouveauMail.matches(REGEX_MAIL)) {
                    erreurProspect.setText(R.string.erreur_mail_prospect);
                    erreurProspect.setVisibility(View.VISIBLE);
                } else if (!nouveauTel.matches(REGEX_TEL)) {
                    erreurProspect.setText(R.string.erreur_tel_prospect);
                    erreurProspect.setVisibility(View.VISIBLE);
                } else {
                    erreurProspect.setVisibility(View.GONE);
                    dialog.dismiss();
                    onItemClickListener.OnModifyClick(position,
                            nouveauNomPrenom, nouveauMail, nouveauTel,
                            nouvelleAdresse, nouvelleVille, nouveauCodePostal);
                }
            });
        }
    }
}
