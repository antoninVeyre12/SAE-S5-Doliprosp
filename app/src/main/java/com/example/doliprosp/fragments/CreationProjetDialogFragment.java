package com.example.doliprosp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.adapters.ProjetAdapter;
import com.example.doliprosp.interfaces.IProjetService;
import com.example.doliprosp.modeles.Projet;
import com.example.doliprosp.services.ProjetService;
import com.example.doliprosp.viewmodels.MesProjetsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class CreationProjetDialogFragment extends DialogFragment {
    private IProjetService projetService;
    private TextView erreur;
    private EditText editTextTitreProjet;
    private EditText editTextDescriptionProjet;
    private DatePicker datePickerDateDebutProjet;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    private String nomProspect;
    private ProjetAdapter adapterProjet;

    private MesProjetsViewModel mesProjetsViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un projet");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.dialog_create_project, container, false);

        projetService = new ProjetService();

        recupereChampsVue(vue);

        if (getArguments().containsKey("nomDuProspect")) {
            nomProspect = getArguments().getString("nomDuProspect");
            adapterProjet = (ProjetAdapter) getArguments().getSerializable("adapterProjet");
        }
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        initialisationBouton();

        return vue;
    }

    private void recupereChampsVue(View vue) {
        erreur = vue.findViewById(R.id.erreur);
        editTextTitreProjet = vue.findViewById(R.id.editTextTitre);
        editTextDescriptionProjet = vue.findViewById(R.id.editTextDescription);
        datePickerDateDebutProjet = vue.findViewById(R.id.datePickerDateDebut);
        boutonEnvoyer = vue.findViewById(R.id.buttonSubmit);
        boutonAnnuler = vue.findViewById(R.id.buttonCancel);
    }

    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String titreProjet = editTextTitreProjet.getText().toString().trim();
            String descriptionProjet = editTextDescriptionProjet.getText().toString().trim();
            int jour = datePickerDateDebutProjet.getDayOfMonth();
            int mois = datePickerDateDebutProjet.getMonth() + 1; // Les mois commencent à 0
            int annee = datePickerDateDebutProjet.getYear();
            long timestampDate = convertirEnTimestamp(jour, mois, annee);
            String dateDebutProjet = String.format(Locale.getDefault(), "%02d/%02d/%04d", jour, mois, annee);

            erreur.setVisibility(View.GONE);

            if(verificationValiditeChamps(titreProjet, descriptionProjet) && verificationValiditeDate(dateDebutProjet)) {
                Projet projet = new Projet(nomProspect, titreProjet, descriptionProjet, dateDebutProjet, timestampDate);
                mesProjetsViewModel.addProjet(projet, getContext());
                adapterProjet.setProjetListe(projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(),
                        nomProspect));

                dismiss();
            }
        });

        boutonAnnuler.setOnClickListener(v ->
            dismiss()
        );
    }


    private boolean verificationValiditeChamps(String titreProjet, String descriptionProjet) {
        boolean estValide = true;
        if (titreProjet.isEmpty()) {
            estValide = false;
            erreur.setText(R.string.erreur_titre_projet_vide);
            erreur.setVisibility(View.VISIBLE);
        }

        if (!Pattern.matches("^[a-zA-Z0-9\\s\\-]+$", titreProjet)) {
            estValide = false;
            erreur.setText(R.string.erreur_titre_projet_caracteres);
            erreur.setVisibility(View.VISIBLE);
        }

        // 3️⃣ Vérification de la description trop longue (max 1500 caractères)
        if (descriptionProjet.length() > 1500) {
            estValide = false;
            erreur.setText(R.string.erreur_description_projet_longueur);
            erreur.setVisibility(View.VISIBLE);
        }
        return estValide;
    }

    private boolean verificationValiditeDate(String dateDebutProjet) {
        // 4 Vérification des dates : existence réelle + logique + futur
        boolean estValide = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false); // Empêche les dates invalides comme 32/01/2024
        try {
            Date dateDebut = sdf.parse(dateDebutProjet);
            Date aujourdhui = new Date();
            // Vérifier que la date est bien dans le futur
            if (!dateDebut.after(aujourdhui)) {
                estValide = false;
                erreur.setText(R.string.erreur_date_debut_passee);
                erreur.setVisibility(View.VISIBLE);
            }

        } catch (ParseException e) {
            erreur.setText(R.string.erreur_date_invalide);
            erreur.setVisibility(View.VISIBLE);
        }
        return estValide;
    }


    public static long convertirEnTimestamp(int jour, int mois, int annee) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(annee, mois - 1, jour, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() / 1000;
    }

}

