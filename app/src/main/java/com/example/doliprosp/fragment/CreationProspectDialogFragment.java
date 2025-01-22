package com.example.doliprosp.fragment;

import android.app.Dialog;
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

import com.example.doliprosp.Interface.IProspectService;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.viewModel.ProspectViewModel;


import java.util.ArrayList;

public class CreationProspectDialogFragment extends DialogFragment{
    private IProspectService prospectService;
    private TextView erreurNom;
    private EditText nomProspect;
    private EditText prenomProspect;
    private EditText mailProspect;
    private EditText telProspect;
    private EditText adresseProspect;
    private EditText villeProspect;
    private EditText codePostalProspect;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;

    private ProspectViewModel mesProspectViewModel;
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

        erreurNom = view.findViewById(R.id.erreur_nom);

        nomProspect = view.findViewById(R.id.editTextNom);
        prenomProspect = view.findViewById(R.id.editTextPrenom);
        mailProspect = view.findViewById(R.id.editTextMail);
        telProspect = view.findViewById(R.id.editTextPhone);
        adresseProspect = view.findViewById(R.id.editTextAdresse);
        villeProspect = view.findViewById(R.id.editTextVille);
        codePostalProspect = view.findViewById(R.id.editTextCodePostal);

        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);


        boutonEnvoyer.setOnClickListener(v -> {
            String nom = nomProspect.getText().toString();
            String prenom = prenomProspect.getText().toString();
            String mail = mailProspect.getText().toString();
            String tel = telProspect.getText().toString();
            String adresse = adresseProspect.getText().toString();
            String ville = villeProspect.getText().toString();
            int codePostal = Integer.parseInt(codePostalProspect.getText().toString());
            boolean estClient = false;

            if (nom.length() <= 2 || nom.length() >= 50 ) {
                erreurNom.setText(R.string.erreur_nom_prospect_longueur);
                erreurNom.setVisibility(View.VISIBLE);
            } else {
                Log.d("info :","✅ Le prospect à bien été implémenté localement !");
                Prospect newProspect = new Prospect(nom, prenom, codePostal, ville, adresse, mail, tel, estClient);
                mesProspectViewModel.addProspect(newShow);
                if (adapterMesSalons != null) {
                    adapterMesSalons.notifyDataSetChanged();
                }
                dismiss();
        });

        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });



        return view;
    }
}
