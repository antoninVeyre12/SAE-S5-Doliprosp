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

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class CreationSalonsDialogFragment extends DialogFragment {

    private MesSalonsViewModel mesSalonsViewModel;
    private SalonsViewModel salonsViewModel;
    private ISalonService salonService;
    private EditText titreEditText;
    private UtilisateurViewModel utilisateurViewModel;
    // private ArrayList<Salon> listeSalonsSauvegarder;
    private MyShowAdapter adapterMesSalons;

    private TextView erreurNom;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un salon");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_show, container, false);

        salonService = new SalonService();
        mesSalonsViewModel = new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        salonsViewModel = new ViewModelProvider(requireActivity()).get(SalonsViewModel.class);
        titreEditText = view.findViewById(R.id.editTextTitle);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);
        erreurNom = view.findViewById(R.id.erreur_nom);

        // Récupere les données de l'adapteur
        if (getArguments().containsKey("adapterMyShow")) {
            adapterMesSalons = (MyShowAdapter) getArguments().getSerializable("adapterMyShow");
        }

        boutonEnvoyer.setOnClickListener(v -> {
            String title = titreEditText.getText().toString();
            if (title.length() <= 2 || title.length() >= 50 ) {
                erreurNom.setText(R.string.erreur_nom_salon_longueur);
                erreurNom.setVisibility(View.VISIBLE);
            } else if(salonService.salonExiste(title, salonsViewModel, mesSalonsViewModel)){
                erreurNom.setText(R.string.erreur_nom_salon_existe);
                erreurNom.setVisibility(View.VISIBLE);

            } else {
                Salon newShow = new Salon(title);
                mesSalonsViewModel.addSalon(newShow);
                if (adapterMesSalons != null) {
                    adapterMesSalons.notifyDataSetChanged();
                }
                dismiss();
            }

        });

        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }


}
