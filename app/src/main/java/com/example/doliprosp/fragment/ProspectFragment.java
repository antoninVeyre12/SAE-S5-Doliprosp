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

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.ProspectAdapter;
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
    private TextView salonActuelEditText;
    private Salon salonActuel;
    private static Salon dernierSalonSelectione;
    private Button boutonCreerProspect;
    private UtilisateurViewModel utilisateurViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private ProspectAdapter adapterProspect;
    private ProgressBar chargement;
    private String recherche;
    private RecyclerView prospectRecyclerView;
    private String champ;
    private String tri;

    /**
     * Crée et retourne la vue du fragment, en initialisant les données du salon si disponibles.
     *
     * @param inflater Le LayoutInflater pour inflater la vue du fragment.
     * @param container Le conteneur dans lequel la vue sera ajoutée.
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
     * @param view La vue du fragment.
     * @param savedInstanceState L'état sauvegardé de la vue.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prospectService = new ProspectService();
        boutonCreerProspect = view.findViewById(R.id.buttonCreateProspect);
        salonActuelEditText = view.findViewById(R.id.salonActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
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
            Bundle bundle = new Bundle();
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
        ((MainActivity) getActivity()).setColors(2);
        if (dernierSalonSelectione != null) {
            salonActuel = dernierSalonSelectione;
            adapterProspect = new ProspectAdapter(prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonActuel.getNom()), ProspectFragment.this);
            prospectRecyclerView.setAdapter(adapterProspect);
            adapterProspect.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), R.string.selection_salon, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).setColors(1);
        }
    }

    /**
     * Méthode pour vérifier si un prospect existe pour un client, basée sur les critères de recherche.
     *
     * @param recherche Le texte de recherche pour le prospect.
     * @param champ Le champ sur lequel effectuer la recherche.
     * @param tri La méthode de tri des prospects.
     */
    private void prospectClientExiste(String recherche, String champ, String tri) {
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur();
        chargement.setVisibility(View.VISIBLE);
        prospectService.prospectClientExiste(getContext(), recherche, champ, tri, utilisateur, new Outils.APIResponseCallbackArrayProspect() {
            @Override
            public void onSuccess(ArrayList<Prospect> response) {
                // TODO: Implémenter la gestion des succès
            }

            @Override
            public void onError(String errorMessage) {
                // TODO: Implémenter la gestion des erreurs
            }
        });
    }

    /**
     * Méthode appelée lorsqu'un élément de la liste des prospects est sélectionné.
     *
     * @param position La position de l'élément sélectionné.
     * @param prospectListe La liste des prospects.
     */
    @Override
    public void onSelectClick(int position, List<Prospect> prospectListe) {
        Prospect prospect = prospectListe.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("prospect", (Serializable) prospect);
        ProjetFragment projetFragment = new ProjetFragment();
        projetFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(projetFragment);
        ((MainActivity) getActivity()).setColors(3);
    }
}