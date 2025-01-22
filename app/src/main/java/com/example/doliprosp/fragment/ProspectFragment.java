
package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProspectFragment extends Fragment {

    private IProspectService prospectService;
    private TextView salonActuelEditText;
    private Salon salonActuel;
    private static Salon dernierSalonSelectione;
    private Button boutonCreerProspect;
    private UtilisateurViewModel utilisateurViewModel;
    private ProspectAdapter adapterProspect;
    private ProgressBar chargement;
    private String recherche;
    private String champ;
    private String tri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        if (bundle != null) {
            salonActuel = (Salon) bundle.getSerializable("salon");
            dernierSalonSelectione = salonActuel;
        } else {
            if (dernierSalonSelectione != null) {
                salonActuel = dernierSalonSelectione;
            } else {
            }
        }
        return inflater.inflate(R.layout.fragment_prospect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        prospectService = new ProspectService();
        boutonCreerProspect = view.findViewById(R.id.buttonCreateProspect);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        chargement = view.findViewById(R.id.chargement);
        recherche = view.findViewById(R.id.recherche).toString();
        champ = view.findViewById(R.id.champ).toString();
        tri = view.findViewById(R.id.tri).toString();
        prospectClientExiste(recherche,champ,tri);

        setupListeners();
    }

    private void setupListeners() {
        // TODO Lancer la recherche avec le texte saisi

        // Ajouter un salon
        boutonCreerProspect.setOnClickListener(v -> {
            CreationProspectDialogFragment dialog = new CreationProspectDialogFragment();
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }


    private void prospectClientExiste(String recherche, String champ, String tri) {
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity());
        chargement.setVisibility(View.VISIBLE);

        prospectService.prospectClientExiste(getContext(),recherche,champ,tri, utilisateur, new Outils.APIResponseCallbackArrayProspect() {

            @Override
            public void onSuccess(ArrayList<Prospect> response) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}