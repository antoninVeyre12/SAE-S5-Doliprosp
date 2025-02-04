package com.example.doliprosp.fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProjetService;
import com.example.doliprosp.adapter.ProjetAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class CreationProjetDialogFragment extends DialogFragment {
    private IProjetService projetService;
    private TextView erreur;
    private EditText editTextTitreProjet;
    private EditText editTextDescriptionProjet;
    private DatePicker datePickerDateDebutProjet;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    private String nomSalon;
    private String nomProspect;
    private ProjetAdapter adapterProjet;

    private MesProjetsViewModel mesProjetsViewModel;

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un projet");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_project, container, false);

        projetService = new ProjetService();

        erreur = view.findViewById(R.id.erreur);
        editTextTitreProjet = view.findViewById(R.id.editTextTitre);
        editTextDescriptionProjet = view.findViewById(R.id.editTextDescription);
        datePickerDateDebutProjet = view.findViewById(R.id.datePickerDateDebut);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);

        if (getArguments().containsKey("nomDuProspect")) {
            nomProspect = (String) getArguments().getSerializable("nomDuProspect");
            Log.d("vhbzhbvzfbkhv", nomProspect);
            adapterProjet = (ProjetAdapter) getArguments().getSerializable("adapterProspect");
        }


        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        initialisationBouton();

        return view;
    }

    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String titreProjet = editTextTitreProjet.getText().toString().trim();
            String descriptionProjet = editTextDescriptionProjet.getText().toString().trim();
            int day = datePickerDateDebutProjet.getDayOfMonth();
            int month = datePickerDateDebutProjet.getMonth() + 1; // Les mois commencent à 0
            int year = datePickerDateDebutProjet.getYear();
            String dateDebutProjet = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);

            erreur.setVisibility(View.GONE); // Cacher le message d'erreur par défaut

            // 1️⃣ Vérification du titre vide
            if (titreProjet.isEmpty()) {
                erreur.setText(R.string.erreur_titre_projet_vide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // 2️⃣ Vérification du titre non conforme (lettres, chiffres, espaces et tirets seulement)
            if (!Pattern.matches("^[a-zA-Z0-9\\s\\-]+$", titreProjet)) {
                erreur.setText(R.string.erreur_titre_projet_caracteres);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // 3️⃣ Vérification de la description trop longue (max 1500 caractères)
            if (descriptionProjet.length() > 1500) {
                erreur.setText(R.string.erreur_description_projet_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // 4 Vérification des dates : existence réelle + logique + futur
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setLenient(false); // Empêche les dates invalides comme 32/01/2024
            try {
                Date dateDebut = sdf.parse(dateDebutProjet);
                Date aujourdhui = new Date();

                // Vérifier que la date est bien dans le futur
                if (!dateDebut.after(aujourdhui)) {
                    erreur.setText(R.string.erreur_date_debut_passee);
                    erreur.setVisibility(View.VISIBLE);
                    return;
                }

            } catch (ParseException e) {
                erreur.setText(R.string.erreur_date_invalide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Tout est valide, création du projet
            Projet projet = new Projet(nomProspect, titreProjet, descriptionProjet, dateDebutProjet);
            mesProjetsViewModel.addProjet(projet);

            dismiss();
        });

        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });
    }

}

