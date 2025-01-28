package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.os.Bundle;
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

public class CreationProjetDialogFragment extends DialogFragment {
    private IProjetService projetService;
    private TextView erreur;
    private EditText editTextTitreProjet;
    private EditText editTextDescriptionProjet;
    private EditText editTextDateDebutProjet;
    private EditText editTextDateFinProjet;
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
        editTextDateFinProjet = view.findViewById(R.id.editTextDateFin);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);

        if (getArguments().containsKey("nomDuSalon")) {
            nomProspect = (String) getArguments().getSerializable("nomDuProspect");
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
            String dateDebutProjet = editTextDateDebutProjet.getText().toString().trim();
            String dateFinProjet = editTextDateFinProjet.getText().toString().trim();

            erreur.setVisibility(View.GONE); // Cacher l'erreur au début

            if (titreProjet.length() <= 2 || titreProjet.length() >= 50) {
                erreur.setText(R.string.erreur_titre_projet_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (descriptionProjet.length() <= 2 ) {
                erreur.setText(R.string.erreur_description_projet_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }



            if (dateDebutProjet.isEmpty() || dateDebutProjet.length() != 10) {
                erreur.setText(R.string.erreur_date_projet);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (dateFinProjet.isEmpty() || dateFinProjet.length() != 10) {
                erreur.setText(R.string.erreur_date_projet);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Tout est valide, créer le prospect
            Projet projet = new Projet(nomProspect, titreProjet, descriptionProjet, dateDebutProjet, dateFinProjet);
            mesProjetsViewModel.addProjet(projet);

            if (adapterProjet != null) {
            }
            dismiss();
        });

        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });
        adapterProjet.notifyDataSetChanged();
    }
}

