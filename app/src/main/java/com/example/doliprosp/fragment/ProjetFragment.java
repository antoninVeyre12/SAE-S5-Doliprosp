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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProjetService;
import com.example.doliprosp.adapter.ProjetAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;

public class ProjetFragment extends Fragment {

    private IProjetService projetService;
    private TextView salonActuelEditText;
    private Salon salonActuel;
    private Prospect prospectActuel;
    private static Salon dernierSalonSelectione;
    private static Prospect dernierProspectSelectionne;
    private Button boutonCreerProjet;
    private UtilisateurViewModel utilisateurViewModel;
    private MesProjetsViewModel mesProjetsViewModel;
    private ProjetAdapter adapterProjet;
    private ProgressBar chargement;
    private String recherche;
    private RecyclerView projetRecyclerView;
    private String champ;
    private String tri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            salonActuel = (Salon) bundle.getSerializable("salon");
            prospectActuel = (Prospect) bundle.getSerializable("prospect");
            dernierSalonSelectione = salonActuel;
        } else {
            if (dernierSalonSelectione != null) {
                salonActuel = dernierSalonSelectione;
            }
            if (dernierProspectSelectionne != null) {
                prospectActuel = dernierProspectSelectionne;
            }

        }
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        projetService = new ProjetService();
        boutonCreerProjet = view.findViewById(R.id.buttonCreateProject);
        salonActuelEditText = view.findViewById(R.id.salonActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        projetRecyclerView = view.findViewById(R.id.projetRecyclerView);
        chargement = view.findViewById(R.id.chargement);
        // recherche = view.findViewById(R.id.recherche).toString();
        // champ = view.findViewById(R.id.champ).toString();
        // Set l'adapter des salons de l'utilisateur
        // tri = view.findViewById(R.id.tri).toString();
        //prospectClientExiste(recherche, champ, tri);
        salonActuelEditText.setText(salonActuel.getNom());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        projetRecyclerView.setLayoutManager(layoutManager);
        setupListeners();
    }

    private void setupListeners() {
        // Ajouter un salon
        boutonCreerProjet.setOnClickListener(v -> {
            CreationProspectDialogFragment dialog = new CreationProspectDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("nomDuSalon", (Serializable) salonActuel.getNom());
            bundle.putSerializable("nomDuProspect", (Serializable) prospectActuel.getNom());
            bundle.putSerializable("adapterProjet", (Serializable) adapterProjet);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    public void onResume() {
        super.onResume();
        adapterProjet = new ProjetAdapter(projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), salonActuel.getNom(), prospectActuel.getNom()));
        projetRecyclerView.setAdapter(adapterProjet);
        adapterProjet.notifyDataSetChanged();
    }
}