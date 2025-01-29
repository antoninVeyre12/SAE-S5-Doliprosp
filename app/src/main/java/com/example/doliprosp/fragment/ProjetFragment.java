package com.example.doliprosp.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fragment représentant la gestion des projets pour un prospect donné.
 * Permet de lister les projets associés à un prospect et d'ajouter de nouveaux projets.
 */
public class ProjetFragment extends Fragment  implements ProjetAdapter.OnItemClickListener {

    private IProjetService projetService;
    private TextView prospectActuelEditText;
    private Prospect prospectActuel;
    private static Prospect dernierProspectSelectionne;
    private Button boutonCreerProjet;
    private UtilisateurViewModel utilisateurViewModel;
    private MesProjetsViewModel mesProjetsViewModel;
    private ProjetAdapter adapterProjet;
    private ProgressBar chargement;
    private RecyclerView projetRecyclerView;


    /**
     * Crée et retourne la vue du fragment, en initialisant les informations relatives au prospect sélectionné.
     *
     * @param inflater Le LayoutInflater pour inflater la vue du fragment.
     * @param container Le conteneur dans lequel la vue sera ajoutée.
     * @param savedInstanceState L'état précédent sauvegardé du fragment.
     * @return La vue du fragment.
     */
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

    /**
     * Méthode appelée après la création de la vue pour initialiser les éléments de l'interface et configurer les listeners.
     *
     * @param view La vue du fragment.
     * @param savedInstanceState L'état sauvegardé de la vue.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        projetService = new ProjetService();
        boutonCreerProjet = view.findViewById(R.id.buttonCreateProject);
        prospectActuelEditText = view.findViewById(R.id.prospectActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        projetRecyclerView = view.findViewById(R.id.projetRecyclerView);
        chargement = view.findViewById(R.id.chargement);

        // Affiche le nom du prospect actuellement sélectionné
        prospectActuelEditText.setText(prospectActuel.getNom());

        // Configure le RecyclerView avec un layout en grille
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        projetRecyclerView.setLayoutManager(layoutManager);

        // Configure les listeners pour les interactions utilisateur
        setupListeners();
    }

    /**
     * Initialise les listeners pour les boutons et interactions de l'interface.
     */
    private void setupListeners() {
        // Ajoute un projet
        boutonCreerProjet.setOnClickListener(v -> {
            CreationProjetDialogFragment dialog = new CreationProjetDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("nomDuProspect", (Serializable) prospectActuel.getNom());
            //bundle.putSerializable("adapterProjet", (Serializable) adapterProjet);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    /**
     * Méthode appelée lors de la reprise du fragment pour recharger la liste des projets et mettre à jour l'interface.
     */
    @Override
    public void onResume() {
        super.onResume();

        // Met à jour l'interface avec le style du fragment actif
        ((MainActivity) getActivity()).setColors(3);

        // Si un prospect est sélectionné, affiche la liste des projets associés
        if (prospectActuel != null) {
            adapterProjet = new ProjetAdapter(projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectActuel.getNom()), (ProjetAdapter.OnItemClickListener) ProjetFragment.this);
            ArrayList<Projet> listProjet = (ArrayList<Projet>) projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectActuel.getNom());
            Log.d("projet prospect", String.valueOf(listProjet.size()));
            projetRecyclerView.setAdapter(adapterProjet);
            adapterProjet.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), R.string.selection_prospect, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).setColors(1);
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Projet projetASupprimer = mesProjetsViewModel.getProjetListe().get(position);
        mesProjetsViewModel.removeProjet(projetASupprimer);
        adapterProjet.notifyItemRemoved(position);

    }
}