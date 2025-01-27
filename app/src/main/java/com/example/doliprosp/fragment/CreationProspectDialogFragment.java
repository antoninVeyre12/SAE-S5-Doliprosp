package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.ProspectViewModel;

import java.io.Serializable;

public class CreationProspectDialogFragment extends DialogFragment {
    private IProspectService prospectService;
    private TextView erreur;
    private EditText nomProspect;
    private EditText prenomProspect;
    private EditText mailProspect;
    private EditText telProspect;
    private EditText adresseProspect;
    private EditText villeProspect;
    private EditText codePostalProspect;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    private String nomSalon;
    private final String REGEX_MAIl = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private ProspectAdapter adapterProspect;

    private MesProspectViewModel mesProspectViewModel;

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un prospect");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_prospect, container, false);

        prospectService = new ProspectService();
        erreur = view.findViewById(R.id.erreur);
        nomProspect = view.findViewById(R.id.editTextNom);
        prenomProspect = view.findViewById(R.id.editTextPrenom);
        mailProspect = view.findViewById(R.id.editTextMail);
        telProspect = view.findViewById(R.id.editTextPhone);
        adresseProspect = view.findViewById(R.id.editTextAdresse);
        villeProspect = view.findViewById(R.id.editTextVille);
        codePostalProspect = view.findViewById(R.id.editTextCodePostal);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);

        if (getArguments().containsKey("nomDuSalon")) {
            nomSalon = (String) getArguments().getSerializable("nomDuSalon");
            adapterProspect = (ProspectAdapter) getArguments().getSerializable("adapterProspect");

        }


        // Initialiser le ViewModel
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        initialisationBouton();

        return view;
    }

    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String nom = nomProspect.getText().toString().trim();
            String prenom = prenomProspect.getText().toString().trim();
            String mail = mailProspect.getText().toString().trim();
            String tel = telProspect.getText().toString().trim();
            String adresse = adresseProspect.getText().toString().trim();
            String ville = villeProspect.getText().toString().trim();
            String estClient = "Prospect";

            erreur.setVisibility(View.GONE); // Cacher l'erreur au début

            // Vérification du nom
            if (nom.length() <= 2 || nom.length() >= 50) {
                erreur.setText(R.string.erreur_nom_prospect_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification du prénom
            if (prenom.length() <= 2 || prenom.length() >= 50) {
                erreur.setText(R.string.erreur_prenom_prospect_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification de l'email
            if (!mail.matches(REGEX_MAIl)) {
                erreur.setText(R.string.erreur_mail_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification du téléphone
            if (tel.matches(REGEX_TEL)) {
                erreur.setText(R.string.erreur_tel_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification de l'adresse
            if (adresse.isEmpty() || adresse.length() >= 60) {
                erreur.setText(R.string.erreur_adresse_prospect_maxLongueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification de la ville
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
            Prospect prospect = new Prospect(nomSalon,  nom, codePostal, ville, adresse, mail, tel, estClient, "image");
            mesProspectViewModel.addProspect(prospect);

            if (adapterProspect != null) {
            }
            dismiss();
            ProjetFragment projetFragment = new ProjetFragment();
            ((MainActivity) getActivity()).loadFragment(projetFragment);
            ((MainActivity) getActivity()).setColors(3);
        });

        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });
        adapterProspect.notifyDataSetChanged();
    }
}

