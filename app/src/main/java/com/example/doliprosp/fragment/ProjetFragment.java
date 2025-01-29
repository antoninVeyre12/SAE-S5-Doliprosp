package com.example.doliprosp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProjetService;
import com.example.doliprosp.adapter.ProjetAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjetFragment extends Fragment {

    private IProjetService projetService;
    private TextView prospectActuelEditText;
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
            prospectActuel = (Prospect) bundle.getSerializable("prospect");
            dernierProspectSelectionne = prospectActuel;

        } else {
            if (dernierProspectSelectionne != null) {
                prospectActuel = dernierProspectSelectionne;
            } else {
                Toast.makeText(getActivity(), R.string.selection_prospect, Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                return null;
            }
        }
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        projetService = new ProjetService();
        boutonCreerProjet = view.findViewById(R.id.buttonCreateProject);
        prospectActuelEditText = view.findViewById(R.id.prospectActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        projetRecyclerView = view.findViewById(R.id.projetRecyclerView);
        chargement = view.findViewById(R.id.chargement);


        prospectActuelEditText.setText(prospectActuel.getNom());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        projetRecyclerView.setLayoutManager(layoutManager);
        setupListeners();
    }

    private void setupListeners() {
        // Ajouter un projet
        boutonCreerProjet.setOnClickListener(v -> {
            CreationProjetDialogFragment dialog = new CreationProjetDialogFragment();
            Bundle bundle = new Bundle();
            //bundle.putSerializable("nomDuSalon", (Serializable) Actuel.getNom());
            //bundle.putSerializable("nomDuProspect", (Serializable) prospectActuel.getNom());
            //bundle.putSerializable("adapterProjet", (Serializable) adapterProjet);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    public void onResume() {
        super.onResume();

        // Met en primaryColor l'icone et le texte du fragment
        ((MainActivity) getActivity()).setColors(3);

        if (prospectActuel != null) {
            adapterProjet = new ProjetAdapter(projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectActuel.getNom()));
            projetRecyclerView.setAdapter(adapterProjet);
            adapterProjet.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), R.string.selection_prospect, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).setColors(1);
        }
    }
    /*
    @Override
    public void onDeleteClick(int position) {
        Projet projetASupprimer = mesProjetsViewModel.getProjetListe().get(position);
        mesProjetsViewModel.removeProjet(projetASupprimer);
        adapterProjet.notifyItemRemoved(position);
    }*/
}