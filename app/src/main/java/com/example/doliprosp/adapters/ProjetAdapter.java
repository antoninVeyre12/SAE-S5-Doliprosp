package com.example.doliprosp.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.modeles.Projet;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.doliprosp.fragments.CreationProjetDialogFragment.convertirEnTimestamp;

/**
 * ProjetAdapter est un adaptateur pour le RecyclerView qui affiche une liste
 * de projets.
 * Chaque élément de la liste permet de modifier ou supprimer un projet.
 */
public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.MyViewHolder> implements Serializable {

    // Liste des projets qui sera affichée dans le RecyclerView
    private List<Projet> projetListe;
    private ProjetAdapter.OnItemClickListener onItemClickListener;

    private transient EditText editTextTitreProjet;
    private transient EditText editTextDescription;
    private transient DatePicker datePickerDateDebut;
    private transient Button btnModifier, btnAnnuler;
    private transient TextView erreurChamp;

    /**
     * Constructeur de l'adaptateur.
     *
     * @param projetListe         Liste des projets à afficher dans le
     *                            RecyclerView.
     * @param onItemClickListener Listener pour gérer les événements de clic
     *                            (suppression et modification).
     */
    public ProjetAdapter(List<Projet> projetListe,
                         ProjetAdapter.OnItemClickListener onItemClickListener) {
        this.projetListe = projetListe;
        this.onItemClickListener = onItemClickListener;

    }

    /**
     * Crée un nouveau ViewHolder pour chaque élément de la liste.
     *
     * @param parent   Le parent dans lequel la vue sera insérée.
     * @param viewType Le type de vue.
     * @return Un nouveau ViewHolder contenant la vue de l'élément.
     */
    @NonNull
    @Override
    public ProjetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjetAdapter.MyViewHolder(view); // Retourne un
        // ViewHolder qui contient la vue gonflée
    }

    /**
     * Lie les données du projet à la vue de l'élément (le ViewHolder).
     *
     * @param holder   Le ViewHolder contenant les vues de l'élément.
     * @param position La position de l'élément dans la liste.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Projet projet = projetListe.get(position);
        holder.titre.setText(projet.getTitre());

        holder.projet_supprimer.setOnClickListener(v -> afficherDialogConfirmation(v, position));

        holder.projet_modifier.setOnClickListener(v -> afficherDialogModification(v, projet, position));
    }

    /**
     * Affiche une boîte de dialogue de confirmation avant de supprimer un
     * projet.
     *
     * @param v        La vue qui a déclenché l'action.
     * @param position La position de l'élément dans la liste.
     */
    private void afficherDialogConfirmation(View v, int position) {
        if (onItemClickListener != null) {
            new AlertDialog.Builder(v.getContext())
                    .setMessage(R.string.confirmation_suppresion_projet)
                    .setPositiveButton("Oui",
                            (dialog, which) -> onItemClickListener.onDeleteClick(position))
                    .setNegativeButton("Non",
                            (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }


    /**
     * Affiche une boîte de dialogue permettant à l'utilisateur de modifier
     * un projet.
     *
     * @param v        La vue qui a déclenché l'action.
     * @param projet   L'objet Projet contenant les informations à afficher
     *                 dans la boîte de dialogue.
     * @param position La position de l'élément dans la liste.
     */
    private void afficherDialogModification(View v, Projet projet,
                                            int position) {
        if (onItemClickListener != null) {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_create_project,
                    null);

            configurerElementsVue(layout);
            remplirChamps(projet);

            // Créer et afficher l'alerte
            AlertDialog dialog = creationDialogModification(v.getContext(),
                    layout);
            dialog.show();

            // Actions des boutons
            btnAnnuler.setOnClickListener(v1 -> dialog.dismiss());
            btnModifier.setOnClickListener(v1 -> {
                handleModification(position, dialog);

            });
        }
    }

    public void setProjetListe(List<Projet> nouveauProjet) {
        this.projetListe = nouveauProjet;
        notifyDataSetChanged();
    }

    private void configurerElementsVue(View vue) {
        editTextTitreProjet = vue.findViewById(R.id.editTextTitre);
        editTextDescription = vue.findViewById(R.id.editTextDescription);
        datePickerDateDebut = vue.findViewById(R.id.datePickerDateDebut);
        btnModifier = vue.findViewById(R.id.buttonSubmit);
        btnModifier.setText("Modifier");
        btnAnnuler = vue.findViewById(R.id.buttonCancel);
        erreurChamp = vue.findViewById(R.id.erreur);
    }


    private void remplirChamps(Projet projet) {
        editTextTitreProjet.setText(projet.getTitre());
        editTextDescription.setText(projet.getDescription());
        setDatePickerValue(datePickerDateDebut, projet.getDateDebut());

        Log.d("Date debut : ", projet.getDateDebut());
    }


    /**
     * Définit la date sélectionnée dans le DatePicker à partir d'une chaîne
     * de caractères
     * représentant une date au format "YYYY-MM-DD".
     * <p>
     * Cette méthode vérifie d'abord que la date passée respecte ce format,
     * puis extrait les
     * informations nécessaires (année, mois, jour) pour mettre à jour le
     * DatePicker.
     * </p>
     *
     * @param datePicker L'objet DatePicker dont la date doit être mise à jour.
     * @param date       La date sous forme de chaîne de caractères (format
     *                   "YYYY-MM-DD").
     * @throws IllegalArgumentException si la date ne respecte pas le format
     *                                  attendu.
     */
    private void setDatePickerValue(DatePicker datePicker, String date) {
        if (date != null) {
            try {
                int year = 0, month = 0, day = 0;

                // Vérifie si la date est au format YYYY-MM-DD
                if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    String[] dateParts = date.split("-");
                    year = Integer.parseInt(dateParts[0]);
                    month = Integer.parseInt(dateParts[1]) - 1; // Le mois
                    // commence à 0
                    day = Integer.parseInt(dateParts[2]);
                }
                // Vérifie si la date est au format dd/MM/yyyy
                else if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    String[] dateParts = date.split("/");
                    day = Integer.parseInt(dateParts[0]);
                    month = Integer.parseInt(dateParts[1]) - 1; // Le mois
                    // commence à 0
                    year = Integer.parseInt(dateParts[2]);
                }

                // Applique la date au DatePicker
                if (year != 0 && month != 0 && day != 0) {
                    datePicker.updateDate(year, month, day);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log l'erreur pour le debug
            }
        }
    }


    /**
     * Traite la modification du projet et vérifie la validité des données
     * saisies.
     *
     * @param position La position du projet dans la liste.
     * @param dialog   La boîte de dialogue de modification.
     */
    private void handleModification(int position, Dialog dialog) {
        String nouveauTitre = editTextTitreProjet.getText().toString();
        String nouvelleDescription = editTextDescription.getText().toString();
        String nouvelleDateDebut = getDateFromDatePicker(datePickerDateDebut);
        long nouveauTimestamp =
                convertirEnTimestamp(datePickerDateDebut.getDayOfMonth(),
                        datePickerDateDebut.getMonth() + 1,
                        datePickerDateDebut.getYear());

        Log.d("Données : ",
                nouveauTitre + ", " + nouvelleDescription + ", " + nouvelleDateDebut);

        // Vérification des champs
        if (champsValides(nouveauTitre, nouvelleDescription,
                nouvelleDateDebut, erreurChamp)) {
            // Mettre à jour l'objet Projet dans la liste
            Projet projetModifie = projetListe.get(position);
            projetModifie.setTitre(nouveauTitre);
            projetModifie.setDescription(nouvelleDescription);
            projetModifie.setDateDebut(nouvelleDateDebut);

            // Notifier l'adaptateur de la modification
            notifyItemChanged(position);

            // Appeler le listener pour toute action supplémentaire (ex: mise
            // à jour BD)
            onItemClickListener.onUpdateClick(position, nouveauTitre,
                    nouvelleDescription, nouvelleDateDebut, nouveauTimestamp);

            // Fermer la boîte de dialogue
            dialog.dismiss();
        }
    }


    /**
     * Récupère la date sélectionnée dans le DatePicker au format YYYY-MM-DD.
     *
     * @param datePicker date sélectionnée par l'utilisateur
     */
    private String getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year,
                month, day);
    }

    /**
     * Crée et retourne un AlertDialog pour la modification du projet.
     *
     * @param context Le contexte pour la création de l'alerte.
     * @param layout  Le layout à afficher dans la boîte de dialogue.
     * @return L'AlertDialog créé.
     */
    private AlertDialog creationDialogModification(Context context,
                                                   View layout) {
        return new AlertDialog.Builder(context)
                .setTitle("Modifier le projet")
                .setView(layout)
                .setCancelable(false)
                .create();
    }


    /**
     * Vérifie la validité des champs de saisie pour le projet.
     *
     * @param titre       Le titre du projet.
     * @param description La description du projet.
     * @param dateDebut   La date de début du projet.
     * @param erreurChamp Le champ où afficher les erreurs.
     * @return True si tous les champs sont valides, sinon false.
     */
    private boolean champsValides(String titre, String description,
                                  String dateDebut, TextView erreurChamp) {
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
        } else if (!saisieDateValide(dateDebut)) {
            erreurChamp.setText(R.string.erreur_date_fin_projet_longueur);
            erreurChamp.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    /**
     * Vérifie que le titre du projet est valide (longueur entre 3 et 50
     * caractères).
     *
     * @param nouveauTitre Le titre du projet à vérifier.
     * @return True si le titre est valide, sinon false.
     */
    private boolean saisieTitreValide(String nouveauTitre) {
        return nouveauTitre.length() > 2 && nouveauTitre.length() <= 50;
    }

    /**
     * Vérifie que la description du projet est valide (longueur supérieure à
     * 2 caractères).
     *
     * @param nouvelleDescription La description du projet à vérifier.
     * @return True si la description est valide, sinon false.
     */
    private boolean saisieDescriptionValide(String nouvelleDescription) {
        return nouvelleDescription.length() < 1500;
    }

    /**
     * Vérifie que la date est valide (longueur exactement de 10 caractères,
     * format attendu : YYYY-MM-DD).
     *
     * @param date La date à vérifier.
     * @return True si la date est valide, sinon false.
     */

    private boolean saisieDateValide(String date) {

        // Récupération de la date actuelle
        Calendar dateDuJour = Calendar.getInstance();

        // Extraction de l'année, du mois et du jour depuis la date saisie
        try {
            String[] dateParts = date.split("-");
            int annee = Integer.parseInt(dateParts[0]);
            int mois = Integer.parseInt(dateParts[1]) - 1;
            int jour = Integer.parseInt(dateParts[2]);

            Calendar dateSaisie = Calendar.getInstance();
            dateSaisie.set(annee, mois, jour);

            // Vérifie si la date est aujourd'hui ou dans le futur
            return !dateSaisie.before(dateDuJour);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }


    /**
     * Retourne le nombre d'éléments à afficher dans le RecyclerView. Limité
     * à un maximum de 15 projets.
     *
     * @return Le nombre d'éléments à afficher.
     */
    @Override
    public int getItemCount() {
        return Math.min(projetListe.size(), 15); // Affiche au maximum 15
        // projets
    }

    /**
     * interfaces pour gérer les actions de clic (suppression et modification)
     * sur les éléments de la liste.
     */
    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onUpdateClick(int position, String titre, String description,
                           String dateDebut, long nouveauTimestamp);
    }

    /**
     * ViewHolder représentant un élément de la liste de projets.
     * Permet de maintenir les vues nécessaires pour chaque élément afin
     * d'optimiser les performances.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titre;
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
