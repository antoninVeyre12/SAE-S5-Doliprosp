package com.example.doliprosp.fragment;

import android.content.Context;
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
import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProjetService;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment représentant la gestion des prospects dans un salon.
 * Permet de lister, d'ajouter des prospects et de naviguer vers un projet associé à un prospect sélectionné.
 */
public class ProspectFragment extends Fragment implements ProspectAdapter.OnItemClickListener {

    private IProspectService prospectService;
    private IProjetService projetService;
    private TextView salonActuelEditText;
    private Salon salonActuel;
    static Salon dernierSalonSelectione;
    private Button boutonCreerProspect;
    private UtilisateurViewModel utilisateurViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private MesProjetsViewModel mesProjetsViewModel;

    private ProspectAdapter adapterProspect;
    private ProgressBar chargement;
    private String recherche;
    private RecyclerView prospectRecyclerView;
    private String champ;
    private String tri;
    private Bundle bundle;

    /**
     * Crée et retourne la vue du fragment, en initialisant les données du salon si disponibles.
     *
     * @param inflater           Le LayoutInflater pour inflater la vue du fragment.
     * @param container          Le conteneur dans lequel la vue sera ajoutée.
     * @param savedInstanceState L'état précédent sauvegardé du fragment.
     * @return La vue du fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            salonActuel = (Salon) bundle.getSerializable("salon");
            dernierSalonSelectione = salonActuel;
        } else {
            if (dernierSalonSelectione != null) {
                salonActuel = dernierSalonSelectione;
            } else {
                Toast.makeText(getActivity(), R.string.selection_salon, Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                return null;
            }
        }
        return inflater.inflate(R.layout.fragment_prospect, container, false);
    }

    /**
     * Méthode appelée après la création de la vue pour initialiser les éléments de l'interface et configurer les listeners.
     *
     * @param view               La vue du fragment.
     * @param savedInstanceState L'état sauvegardé de la vue.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prospectService = new ProspectService();
        projetService = new ProjetService();
        bundle = new Bundle();
        boutonCreerProspect = view.findViewById(R.id.buttonCreateProspect);
        salonActuelEditText = view.findViewById(R.id.salonActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        prospectRecyclerView = view.findViewById(R.id.prospectRecyclerView);
        chargement = view.findViewById(R.id.chargement);

        salonActuelEditText.setText(salonActuel.getNom());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        prospectRecyclerView.setLayoutManager(layoutManager);
        setupListeners();
    }

    /**
     * Initialise les listeners pour les boutons et interactions de l'interface.
     */
    private void setupListeners() {
        // Ajoute un prospect via un dialog de création
        boutonCreerProspect.setOnClickListener(v -> {
            CreationProspectDialogFragment dialog = new CreationProspectDialogFragment();
            bundle.putSerializable("nomDuSalon", (Serializable) salonActuel.getNom());
            bundle.putSerializable("adapterProspect", (Serializable) adapterProspect);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    /**
     * Méthode appelée lors de la reprise du fragment pour recharger la liste des prospects et mettre à jour les éléments visuels.
     */
    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setColors(2, R.color.color_primary, true);
        if (dernierSalonSelectione != null) {
            salonActuel = dernierSalonSelectione;
            adapterProspect = new ProspectAdapter(prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonActuel.getNom()), ProspectFragment.this, mesProspectViewModel);
            prospectRecyclerView.setAdapter(adapterProspect);
            adapterProspect.notifyDataSetChanged();
        } else {
            ((MainActivity) getActivity()).setColors(2, R.color.invalide, false);

            Toast.makeText(getActivity(), R.string.selection_salon, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).setColors(1, R.color.color_primary, true);
        }
        if (ProjetFragment.dernierProspectSelectionne == null) {
            ((MainActivity) getActivity()).setColors(3, R.color.invalide, false);
        }

    }


    /**
     * Méthode appelée lorsqu'un élément de la liste des prospects est sélectionné.
     *
     * @param position      La position de l'élément sélectionné.
     * @param prospectListe La liste des prospects.
     */
    @Override
    public void onSelectClick(int position, List<Prospect> prospectListe) {
        Prospect prospect = prospectListe.get(position);
        bundle.putSerializable("prospect", (Serializable) prospect);
        ProjetFragment projetFragment = new ProjetFragment();
        projetFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(projetFragment);
        ((MainActivity) getActivity()).setColors(3, R.color.color_primary, true);
    }

    @Override
    public void onDeleteClick(int position) {

        Prospect prospectASupprimer = mesProspectViewModel.getProspectListe().get(position);
        mesProspectViewModel.removeProspect(prospectASupprimer);
        Log.d("adapterProspect", mesProspectViewModel.getProspectListe().toString());
        adapterProspect.notifyItemRemoved(position);
        ArrayList<Projet> projets = (ArrayList<Projet>) projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectASupprimer.getNom());
        for (Projet projet : projets) {
            mesProjetsViewModel.removeProjet(projet);
        }

        //adapterProspect.notifyDataSetChanged();

    }
}