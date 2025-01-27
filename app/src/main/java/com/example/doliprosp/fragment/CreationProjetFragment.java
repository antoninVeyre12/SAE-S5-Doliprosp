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
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;

import java.util.Date;

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
        editTextTitreProjet = view.findViewById(R.id.editTextTitreProjet);
        editTextDescriptionProjet = view.findViewById(R.id.editTextDescriptionProjet);
        editTextDateDebutProjet = view.findViewById(R.id.editTextDateDebutProjet);
        editTextDateFinProjet = view.findViewById(R.id.editTextDateFinProjet);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);

        if (getArguments().containsKey("nomDuSalon")) {
            nomSalon = (String) getArguments().getSerializable("nomDuSalon");
            adapterProspect = (ProspectAdapter) getArguments().getSerializable("adapterProspect");
        }


        // Initialiser le ViewModel
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        initialisationBouton();

        return view;
    }

    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String titreProjet = editTextTitreProjet.getText().toString().trim();
            String descriptionProjet = editTextDescriptionProjet.getText().toString().trim();
            String dateDebutProjet = editTextDateDebutProjet.getText().toString().trim();
            Date dateFinProjet = new Date(editTextDateFinProjet.getText().toString().trim());

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
                erreur.setText(R.string.erreur_adresse_prospect_maxLongueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (ville.isEmpty()) {
                erreur.setText(R.string.erreur_ville_prospect_vide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            int codePostal; // Déclarer le code postal comme un entier
            try {
                codePostal = Integer.parseInt(codePostalProspect.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Gérer le cas où le champ est vide ou contient une valeur non valide
                erreur.setText(R.string.erreur_codePostal_prospect_invalid);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification du code postal
            if (codePostal < 10000 || codePostal > 99999) {
                erreur.setText(R.string.erreur_codePostal_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification si estClient est activé
            /*if (estClient) {
                erreur.setText(R.string.erreur_estClient_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }*/

            // Tout est valide, créer le prospect
            Projet projet = new Projet(titreProjet,  descriptionProjet, dateDebutProjet, dateFinProjet);
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

