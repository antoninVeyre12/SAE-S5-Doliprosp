package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private EditText editTextDateDebutProjet;
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
        editTextDateDebutProjet = view.findViewById(R.id.editTextDateDebut);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);

        setupDateInputMask(editTextDateDebutProjet);

        if (getArguments().containsKey("nomDuProspect")) {
            nomProspect = (String) getArguments().getSerializable("nomDuProspect");
            Log.d("vhbzhbvzfbkhv", nomProspect);
            adapterProjet = (ProjetAdapter) getArguments().getSerializable("adapterProspect");
        }


        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        initialisationBouton();

        return view;
    }

    // Fonction pour ajouter un TextWatcher à un EditText
    private void setupDateInputMask(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private int deletingHyphenIndex;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count > 0 && after == 0) {
                    char deletedChar = s.charAt(start);
                    if (deletedChar == '/') {
                        deletingHyphenIndex = start;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFormatting) return;

                isFormatting = true;
                String clean = s.toString().replaceAll("[^0-9]", "");

                StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < clean.length(); i++) {
                    if (i == 2 || i == 4) {
                        formatted.append('/');
                    }
                    formatted.append(clean.charAt(i));
                }

                editText.setText(formatted.toString());
                editText.setSelection(formatted.length() > 10 ? 10 : formatted.length());
                isFormatting = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Empêcher l'entrée de tout autre caractère que les chiffres
        InputFilter onlyDigitsFilter = (source, start, end, dest, dstart, dend) -> {
            if (source.toString().matches("[0-9/]*")) {
                return null;
            }
            return "";
        };
        editText.setFilters(new InputFilter[]{onlyDigitsFilter, new InputFilter.LengthFilter(10)});
    }

    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String titreProjet = editTextTitreProjet.getText().toString().trim();
            String descriptionProjet = editTextDescriptionProjet.getText().toString().trim();
            String dateDebutProjet = editTextDateDebutProjet.getText().toString().trim();

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

            // 4️⃣ Vérification que la date de début n’est pas vide
            if (dateDebutProjet.isEmpty()) {
                erreur.setText(R.string.erreur_date_debut_vide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }



            // 6️⃣ Vérification du format de la date de début (JJ/MM/AAAA)
            if (!dateDebutProjet.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                erreur.setText(R.string.erreur_date_debut_format);
                erreur.setVisibility(View.VISIBLE);
                return;
            }


            // 8️⃣ Vérification des dates : existence réelle + logique
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setLenient(false); // Empêche les dates invalides comme 32/01/2024
            try {
                Date dateDebut = sdf.parse(dateDebutProjet);
                Date today = new Date();

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

