package com.example.doliprosp.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

/**
 * ProjetAdapter est un adaptateur pour le RecyclerView qui affiche une liste de projets.
 * Chaque élément de la liste permet de modifier ou supprimer un projet.
 *
 */
public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.MyViewHolder> implements Serializable {

    // Liste des projets qui sera affichée dans le RecyclerView
    private List<Projet> projetListe;
    private ProjetAdapter.OnItemClickListener onItemClickListener;

    /**
     * Constructeur de l'adaptateur.
     *
     * @param projetListe Liste des projets à afficher dans le RecyclerView.
     * @param onItemClickListener Listener pour gérer les événements de clic (suppression et modification).
     */
    public ProjetAdapter(List<Projet> projetListe, ProjetAdapter.OnItemClickListener onItemClickListener) {
        this.projetListe = projetListe;
        this.onItemClickListener = onItemClickListener;

    }

    /**
     * Crée un nouveau ViewHolder pour chaque élément de la liste.
     *
     * @param parent Le parent dans lequel la vue sera insérée.
     * @param viewType Le type de vue.
     * @return Un nouveau ViewHolder contenant la vue de l'élément.
     */
    @NonNull
    @Override
    public ProjetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_project qui sera utilisé pour chaque item de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjetAdapter.MyViewHolder(view); // Retourne un ViewHolder qui contient la vue gonflée
    }

    /**
     * Lie les données du projet à la vue de l'élément (le ViewHolder).
     *
     * @param holder Le ViewHolder contenant les vues de l'élément.
     * @param position La position de l'élément dans la liste.
     */    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Projet projet = projetListe.get(position);
        holder.titre.setText(projet.getTitre());

        // Gestion du bouton de suppression
        holder.projet_supprimer.setOnClickListener(v -> afficherDialogConfirmation(v, position));

        // Gestion du bouton de modification
        holder.projet_modifier.setOnClickListener(v -> afficherDialogModification(v, projet, position));


    }
    /**
     * Affiche une boîte de dialogue de confirmation avant de supprimer un projet.
     *
     * @param v La vue qui a déclenché l'action.
     * @param position La position de l'élément dans la liste.
     */
    private void afficherDialogConfirmation(View v, int position) {
        if (onItemClickListener != null) {
            new AlertDialog.Builder(v.getContext())
                    .setMessage(R.string.confirmation_suppresion_projet)
                    .setPositiveButton("Oui", (dialog, which) -> onItemClickListener.onDeleteClick(position))
                    .setNegativeButton("Non", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }


    /**
     * Affiche une boîte de dialogue permettant à l'utilisateur de modifier un projet.
     *
     * @param v La vue qui a déclenché l'action.
     * @param projet L'objet Projet contenant les informations à afficher dans la boîte de dialogue.
     * @param position La position de l'élément dans la liste.
     */
    private void afficherDialogModification(View v, Projet projet, int position) {
        if (onItemClickListener != null) {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_create_project, null);

            // Références des EditText et TextView
            EditText editTextTitreProjet = layout.findViewById(R.id.editTextTitre);
            EditText editTextDescription = layout.findViewById(R.id.editTextDescription);
            EditText editTextDateDebut = layout.findViewById(R.id.editTextDateDebut);
            EditText editTextDateFin = layout.findViewById(R.id.editTextDateFin);
            Button btnModifier = layout.findViewById(R.id.buttonSubmit);
            btnModifier.setText("Modifier");
            Button btnAnnuler = layout.findViewById(R.id.buttonCancel);
            TextView erreurChamp = layout.findViewById(R.id.erreur);

            // Remplir les EditText avec les valeurs actuelles
            saisirChamps(projet, editTextTitreProjet, editTextDescription, editTextDateDebut, editTextDateFin);

            // Créer et afficher l'alerte pour la modification
            AlertDialog dialog = creationDialogModification(v.getContext(), layout);
            dialog.show();

            // Actions des boutons dans le dialogue
            btnAnnuler.setOnClickListener(v1 -> dialog.dismiss());
            btnModifier.setOnClickListener(v1 -> handleModification(editTextTitreProjet, editTextDescription, editTextDateDebut, editTextDateFin, erreurChamp, position, dialog));
        }
    }


    /**
     * Remplir les champs de saisie avec les valeurs actuelles du projet.
     *
     * @param projet L'objet Projet dont les informations sont à afficher.
     * @param editTextTitreProjet Le champ de texte pour le titre du projet.
     * @param editTextDescription Le champ de texte pour la description du projet.
     * @param editTextDateDebut Le champ de texte pour la date de début du projet.
     * @param editTextDateFin Le champ de texte pour la date de fin du projet.
     */
    private void saisirChamps(Projet projet, EditText editTextTitreProjet, EditText editTextDescription, EditText editTextDateDebut, EditText editTextDateFin) {
        editTextTitreProjet.setText(projet.getTitre());
        editTextDescription.setText(projet.getDescription());
        editTextDateDebut.setText(projet.getDateDebut());
        editTextDateFin.setText(projet.getDateFin());
    }


    /**
     * Crée et retourne un AlertDialog pour la modification du projet.
     *
     * @param context Le contexte pour la création de l'alerte.
     * @param layout Le layout à afficher dans la boîte de dialogue.
     * @return L'AlertDialog créé.
     */
    private AlertDialog creationDialogModification(Context context, View layout) {
        return new AlertDialog.Builder(context)
                .setTitle("Modifier le projet")
                .setView(layout)
                .setCancelable(false)
                .create();
    }


    /**
     * Traite la modification du projet et vérifie la validité des données saisies.
     *
     * @param titreProjet Le champ de texte pour le titre du projet.
     * @param description Le champ de texte pour la description du projet.
     * @param dateDebut Le champ de texte pour la date de début du projet.
     * @param dateFin Le champ de texte pour la date de fin du projet.
     * @param erreurChamp Le champ de texte où afficher les messages d'erreur.
     * @param position La position du projet dans la liste.
     * @param dialog La boîte de dialogue de modification.
     */
    private void handleModification(EditText titreProjet, EditText description, EditText dateDebut, EditText dateFin, TextView erreurChamp, int position, Dialog dialog) {
        String nouveauTitre = titreProjet.getText().toString();
        String nouvelleDescription = description.getText().toString();
        String nouvelleDateDebut = dateDebut.getText().toString();
        String nouvelleDatFin = dateFin.getText().toString();

        // Vérification des champs
        if (champsValides(nouveauTitre, nouvelleDescription, nouvelleDateDebut, nouvelleDatFin, erreurChamp)) {
            onItemClickListener.onUpdateClick(position, nouveauTitre, nouvelleDescription, nouvelleDateDebut, nouvelleDatFin);
            dialog.dismiss();
        }
    }



    /**
     * Vérifie la validité des champs de saisie pour le projet.
     *
     * @param titre Le titre du projet.
     * @param description La description du projet.
     * @param dateDebut La date de début du projet.
     * @param dateFin La date de fin du projet.
     * @param erreurChamp Le champ où afficher les erreurs.
     * @return True si tous les champs sont valides, sinon false.
     */
    private boolean champsValides(String titre, String description, String dateDebut, String dateFin, TextView erreurChamp) {
        erreurChamp.setTextColor(Color.RED);
        erreurChamp.setVisibility(View.GONE);

        if (!saisieTitreValide(titre)) {
            erreurChamp.setText(R.string.erreur_titre_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
            return false;
        } else if (!saisieDescriptionValide(description)) {
            erreurChamp.setText(R.string.erreur_description_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
            return false;
        } else if (!saisieDateValide(dateDebut) || !saisieDateValide(dateFin)) {
            erreurChamp.setText(R.string.erreur_date_fin_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    /**
     * Vérifie que le titre du projet est valide (longueur entre 3 et 50 caractères).
     *
     * @param nouveauTitre Le titre du projet à vérifier.
     * @return True si le titre est valide, sinon false.
     */
    private boolean saisieTitreValide(String nouveauTitre) {
        return nouveauTitre.length() > 2 && nouveauTitre.length() <= 50;
    }

    /**
     * Vérifie que la description du projet est valide (longueur supérieure à 2 caractères).
     *
     * @param nouvelleDescription La description du projet à vérifier.
     * @return True si la description est valide, sinon false.
     */
    private boolean saisieDescriptionValide(String nouvelleDescription) {
        return nouvelleDescription.length() > 2 ;
    }

    /**
     * Vérifie que la date est valide (longueur exactement de 10 caractères, format attendu : YYYY-MM-DD).
     *
     * @param date La date à vérifier.
     * @return True si la date est valide, sinon false.
     */
    private boolean saisieDateValide(String date) {
        return date.length() == 10;
    }

    /**
     * Retourne le nombre d'éléments à afficher dans le RecyclerView. Limité à un maximum de 15 projets.
     *
     * @return Le nombre d'éléments à afficher.
     */
    @Override
    public int getItemCount() {
        return Math.min(projetListe.size(), 15); // Affiche au maximum 15 projets
    }

    /**
     * Interface pour gérer les actions de clic (suppression et modification) sur les éléments de la liste.
     */
    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onUpdateClick(int position, String titre, String description, String dateDebut, String dateFin);
    }

    /**
     * ViewHolder représentant un élément de la liste de projets.
     * Permet de maintenir les vues nécessaires pour chaque élément afin d'optimiser les performances.
     */    public static class MyViewHolder extends RecyclerView.ViewHolder {

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
