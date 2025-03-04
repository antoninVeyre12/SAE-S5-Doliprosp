package com.example.doliprosp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.adapters.ProjetAdapter;
import com.example.doliprosp.interfaces.IProjetService;
import com.example.doliprosp.modeles.Projet;
import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.services.ProjetService;
import com.example.doliprosp.viewModels.MesProjetsViewModel;
import com.example.doliprosp.viewModels.UtilisateurViewModel;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Fragment représentant la gestion des projets pour un prospect donné.
 * Permet de lister les projets associés à un prospect et d'ajouter de nouveaux projets.
 */
public class ProjetFragment extends Fragment implements ProjetAdapter.OnItemClickListener {

    private IProjetService projetService;
    private TextView prospectActuelTextView, salonActuelTextView;
    private Prospect prospectActuel;
    public static Prospect dernierProspectSelectionne;
    public static String dernierSalonSelectionne;
    private Button boutonCreerProjet;
    private UtilisateurViewModel utilisateurViewModel;
    private MesProjetsViewModel mesProjetsViewModel;
    private ProjetAdapter adapterProjet;
    private RecyclerView projetRecyclerView;
    private String salonActuel;


    /**
     * Crée et retourne la vue du fragments, en initialisant les informations relatives au prospect sélectionné.
     *
     * @param inflater           Le LayoutInflater pour inflater la vue du fragments.
     * @param container          Le conteneur dans lequel la vue sera ajoutée.
     * @param savedInstanceState L'état précédent sauvegardé du fragments.
     * @return La vue du fragments.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            prospectActuel = (Prospect) bundle.getSerializable("prospect");
            salonActuel = (String) bundle.getSerializable("salon");
            dernierProspectSelectionne = prospectActuel;
            dernierSalonSelectionne = salonActuel;

        } else {
            if (dernierSalonSelectionne != null) {
                salonActuel = dernierSalonSelectionne;
            }
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
     * @param view               La vue du fragments.
     * @param savedInstanceState L'état sauvegardé de la vue.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        projetService = new ProjetService();
        boutonCreerProjet = view.findViewById(R.id.buttonCreateProject);
        prospectActuelTextView = view.findViewById(R.id.prospectActuel);
        salonActuelTextView = view.findViewById(R.id.salonActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        projetRecyclerView = view.findViewById(R.id.projetRecyclerView);

        // Affiche le nom du prospect actuellement sélectionné
        prospectActuelTextView.setText(prospectActuel.getNom());
        salonActuelTextView.setText(salonActuel);

        // Configure le RecyclerView avec un layout en grille
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        projetRecyclerView.setLayoutManager(layoutManager);

        setupListeners();
    }

    /**
     * Initialise les listeners pour les boutons et interactions de l'interface.
     */
    //asa
    private void setupListeners() {
        // Ajoute un projet
        boutonCreerProjet.setOnClickListener(v -> {
            CreationProjetDialogFragment dialog = new CreationProjetDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("nomDuProspect", prospectActuel.getNom());
            bundle.putSerializable("adapterProjet", (Serializable) adapterProjet);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreerDialogueSalon");
        });
    }

    /**
     * Méthode appelée lors de la reprise du fragments pour recharger la liste des projets et mettre à jour l'interface.
     */
    @Override
    public void onResume() {
        super.onResume();

        // Met à jour l'interface avec le style du fragments actif
        ((MainActivity) getActivity()).setColors(3, R.color.color_primary, true);

        // Si un prospect est sélectionné, affiche la liste des projets associés
        if (prospectActuel != null) {
            adapterProjet = new ProjetAdapter(projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectActuel.getNom()), ProjetFragment.this);
            projetRecyclerView.setAdapter(adapterProjet);
            adapterProjet.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), R.string.selection_prospect, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).setColors(1, R.color.color_primary, true);
        }
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur un projet pour le supprimer.
     * Cette méthode supprime le projet à la position spécifiée de la liste des projets et met à jour l'adaptateur pour refléter
     * les modifications dans l'interface utilisateur.
     *
     * @param position La position du projet à supprimer dans la liste.
     */
    @Override
    public void onDeleteClick(int position) {
        Projet projetASupprimer = mesProjetsViewModel.getProjetListe().get(position);
        mesProjetsViewModel.removeProjet(projetASupprimer, getContext());
        adapterProjet.setProjetListe(mesProjetsViewModel.getProjetListe());
        adapterProjet.notifyItemRemoved(position);
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur un projet pour le modifier.
     * Cette méthode permet de mettre à jour les informations du projet
     * avec les nouvelles valeurs fournies pour le titre, la description, la date de début et la date de fin.
     * Après la mise à jour, l'adaptateur est notifié pour refléter les changements dans l'interface utilisateur.
     *
     * @param position    La position du projet à modifier dans la liste.
     * @param titre       Le nouveau titre du projet.
     * @param description La nouvelle description du projet.
     * @param dateDebut   La nouvelle date de début du projet.
     */
    @Override
    public void onUpdateClick(int position, String titre, String description, String dateDebut, long dateTimestamp) {
        Projet projetAModifier = mesProjetsViewModel.getProjetListe().get(position);
        projetService.updateProjet(projetAModifier, titre, description, dateDebut, dateTimestamp);

        // Mettre à jour l'objet dans la liste
        mesProjetsViewModel.getProjetListe().set(position, projetAModifier);

        // Notifier l'adapters du changement
        mesProjetsViewModel.enregistrerProjet(getContext());
        adapterProjet.notifyItemChanged(position);

        // Log.d("ModifierProjet", "Projet reçu: " + projetAModifier.getTitre() + ", " + projetAModifier.getDescription() + ", " + projetAModifier.getDateDebut());
    }

}