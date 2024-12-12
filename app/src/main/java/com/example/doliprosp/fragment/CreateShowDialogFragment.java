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
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.viewModel.MesSalonViewModel;
import com.example.doliprosp.viewModel.SalonViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class CreateShowDialogFragment extends DialogFragment {

    private MesSalonViewModel mesSalonViewModel;
    private SalonViewModel salonViewModel;
    private ISalonService salonService;
    private EditText editTextTitle;
    private UtilisateurViewModel utilisateurViewModel;
    private ArrayList<Salon> showSavedList;
    private MyShowAdapter adapterMesSalons;

    private TextView erreurNom;
    private Button buttonSubmit;
    private Button buttonCancel;
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
        mesSalonViewModel = new ViewModelProvider(requireActivity()).get(MesSalonViewModel.class);
        salonViewModel = new ViewModelProvider(requireActivity()).get(SalonViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        editTextTitle = view.findViewById(R.id.editTextTitle);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonCancel = view.findViewById(R.id.buttonCancel);


        editTextTitle = view.findViewById(R.id.editTextTitle);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        erreurNom = view.findViewById(R.id.erreur_nom);

        // Récupere les données de l'adapteur
        if (getArguments().containsKey("adapterMyShow")) {
            adapterMesSalons = (MyShowAdapter) getArguments().getSerializable("adapterMyShow");
        }

        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity());
        showSavedList = new ArrayList<Salon>();
        //salonViewModel
        /*salonService.getSalonsEnregistres(getContext(),"", utilisateur, new Outils.APIResponseCallbackArrayTest() {
            @Override
            public void onSuccess(ArrayList<Salon> shows) {
                showSavedList = shows;
            }
            @Override
            public void onError(String error) {
            }
        });*/

        buttonSubmit.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            if (title.length() <= 2 || title.length() >= 50 ) {
                erreurNom.setText(R.string.erreur_nom_salon_longeur);
                erreurNom.setVisibility(View.VISIBLE);
            } else if(salonService.salonExiste(title, salonViewModel, mesSalonViewModel)){
                erreurNom.setText(R.string.erreur_nom_salon_existe);
                erreurNom.setVisibility(View.VISIBLE);

            } else {
                Log.d("one","onestla");
                Salon newShow = new Salon(title);
                mesSalonViewModel.addSalon(newShow);
                if (adapterMesSalons != null) {
                    adapterMesSalons.notifyDataSetChanged();
                }
                dismiss();
            }

        });

        buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }


}
