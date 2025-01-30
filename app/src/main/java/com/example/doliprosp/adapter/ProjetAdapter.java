package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.R;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Déclaration de la classe ProjetAdapter qui hérite de RecyclerView.Adapter<ProjetAdapter.MyViewHolder>
// et implémente Serializable pour permettre la sérialisation de l'adaptateur (utile si vous voulez passer
// cet objet entre différentes activités ou fragments).
public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.MyViewHolder> implements Serializable {

    // Liste des projets qui sera affichée dans le RecyclerView
    private List<Projet> projetListe;
    private ProjetAdapter.OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur qui prend la liste des projets à afficher
    public ProjetAdapter(List<Projet> projetListe, ProjetAdapter.OnItemClickListener onItemClickListener) {
        this.projetListe = projetListe;
        this.onItemClickListener = onItemClickListener;

    }

    // Cette méthode est appelée pour créer une nouvelle vue (item) à afficher dans le RecyclerView.
    @NonNull
    @Override
    public ProjetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_project qui sera utilisé pour chaque item de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjetAdapter.MyViewHolder(view); // Retourne un ViewHolder qui contient la vue gonflée
    }

    // Cette méthode est appelée pour lier les données aux éléments de la vue (d'un item spécifique).
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Récupère l'objet Projet à la position donnée dans la liste
        Projet projet = projetListe.get(position);

        // Remplit le TextView avec le titre du projet
        holder.titre.setText(projet.getTitre());

        holder.projet_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.confirmation_suppresion_projet)
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

        // Clic sur le bouton modifier
        holder.projet_modifier.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View layout = inflater.inflate(R.layout.dialog_create_project, null);

                // Obtenir les références des EditText et TextView
                EditText editTextTitreProjet = layout.findViewById(R.id.editTextTitre);
                EditText editTextDescription = layout.findViewById(R.id.editTextDescription);
                EditText editTextDateDebut = layout.findViewById(R.id.editTextDateDebut);
                EditText editTextDateFin = layout.findViewById(R.id.editTextDateFin);
                Button btnModifier = layout.findViewById(R.id.buttonSubmit);
                Button btnAnnuler = layout.findViewById(R.id.buttonCancel);

                TextView erreurChamp = layout.findViewById(R.id.erreur);

                // Remplir les EditText avec les valeurs actuelles
                editTextTitreProjet.setText(projet.getTitre());
                editTextDescription.setText(projet.getDescription());
                editTextDateDebut.setText(projet.getDateDebut());
                editTextDateFin.setText(projet.getDateFin());

                btnModifier.setText("Modifier");

                // Créer une alerte pour confirmer la modification
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Modifier le projet")
                        .setView(layout)
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();

                btnAnnuler.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                // Validation du nom lorsque l'utilisateur appuie sur "Confirmer"
                btnModifier.setOnClickListener(v1 -> {
                    traitementModificationProjet(editTextTitreProjet, editTextDescription, editTextDateDebut, editTextDateFin, erreurChamp, position, dialog);

                });
            }
        });
    }


    private void traitementModificationProjet(EditText titreProjet, EditText description, EditText dateDebut, EditText dateFin, TextView erreurChamp, int position, Dialog dialog) {
        String nouveauTitre = titreProjet.getText().toString();
        String nouvelleDescription = description.getText().toString();
        String nouvelleDateDebut = dateDebut.getText().toString();
        String nouvelleDatFin = dateFin.getText().toString();

        erreurChamp.setTextColor(Color.RED);
        erreurChamp.setVisibility(View.GONE);
        // Vérification des champs saisies
        if (!saisieTitreValide(nouveauTitre)) {
            erreurChamp.setText(R.string.erreur_titre_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
        } else if(!saisieDescriptionValide(nouvelleDescription)) {
            erreurChamp.setText(R.string.erreur_description_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
        } else if(!saisieDateValide(nouvelleDateDebut)) {
            erreurChamp.setText(R.string.erreur_date_fin_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
        } else if(!saisieDateValide(nouvelleDatFin)) {
            erreurChamp.setText(R.string.erreur_date_fin_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
        } else {
            // Si tout est valide, on modifie le nom du salon
            onItemClickListener.onUpdateClick(position, nouveauTitre, nouvelleDescription, nouvelleDateDebut, nouvelleDatFin);
            dialog.dismiss();
        }
    }

    private boolean saisieTitreValide(String nouveauTitre) {
        return nouveauTitre.length() > 2 && nouveauTitre.length() <= 50;
    }


    private boolean saisieDescriptionValide(String nouvelleDescription) {
        return nouvelleDescription.length() > 2 ;
    }


    private boolean saisieDateValide(String date) {
        return date.length() == 10;
    }

    // Retourne le nombre d'éléments à afficher dans le RecyclerView.
    // Ici, on limite le nombre d'éléments affichés à 15 (5 lignes, donc 3 éléments par ligne).
    @Override
    public int getItemCount() {
        return Math.min(projetListe.size(), 15); // Affiche au maximum 15 projets
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onUpdateClick(int position, String titre, String description, String dateDebut, String dateFin);

    }
    // Déclaration du ViewHolder qui représente chaque item de la liste
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titre;
        public ImageView icone;
        public ImageButton projet_supprimer;
        public ImageButton projet_modifier;
        public MyViewHolder(View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.projet_nom);
            projet_supprimer = itemView.findViewById(R.id.projet_supprimer);
            projet_modifier = itemView.findViewById(R.id.projet_modifier);
        }
    }
}
