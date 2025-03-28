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
import com.example.doliprosp.adapters.ProspectAdapter;
import com.example.doliprosp.interfaces.IProjetService;
import com.example.doliprosp.interfaces.IProspectService;
import com.example.doliprosp.modeles.Projet;
import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.services.ProjetService;
import com.example.doliprosp.services.ProspectService;
import com.example.doliprosp.viewmodels.MesProjetsViewModel;
import com.example.doliprosp.viewmodels.MesProspectViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.doliprosp.fragments.ProjetFragment.dernierProspectSelectionne;

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
    private MesProspectViewModel mesProspectViewModel;
    private MesProjetsViewModel mesProjetsViewModel;
    private ProspectAdapter adapterProspect;
    private RecyclerView prospectRecyclerView;
    private Bundle bundle;

    /**
     * Crée et retourne la vue du fragments, en initialisant les données du salon si disponibles.
     *
     * @param inflater           Le LayoutInflater pour inflater la vue du fragments.
     * @param container          Le conteneur dans lequel la vue sera ajoutée.
     * @param savedInstanceState L'état précédent sauvegardé du fragments.
     * @return La vue du fragments.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();
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
     * @param view               La vue du fragments.
     * @param savedInstanceState L'état sauvegardé de la vue.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initialiserVue(view);

        salonActuelEditText.setText(salonActuel.getNom());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        prospectRecyclerView.setLayoutManager(layoutManager);
        setupListeners();
    }

    private void initialiserVue(View view) {
        prospectService = new ProspectService();
        projetService = new ProjetService();
        bundle = new Bundle();
        boutonCreerProspect = view.findViewById(R.id.buttonCreateProspect);
        salonActuelEditText = view.findViewById(R.id.salonActuel);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        prospectRecyclerView = view.findViewById(R.id.prospectRecyclerView);
    }

    /**
     * Initialise les listeners pour les boutons et interactions de l'interface.
     */
    private void setupListeners() {
        // Ajoute un prospect via un dialog de création
        boutonCreerProspect.setOnClickListener(v -> {
            CreationProspectDialogFragment dialog = new CreationProspectDialogFragment();
            bundle.putSerializable("nomDuSalon", salonActuel.getNom());
            bundle.putSerializable("adapterProspect", adapterProspect);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    /**
     * Méthode appelée lors de la reprise du fragments pour recharger la liste des prospects et mettre à jour les éléments visuels.
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
        if (dernierProspectSelectionne == null) {
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
        bundle.putSerializable("prospect", prospect);
        bundle.putSerializable("salon", salonActuel.getNom());
        ProjetFragment projetFragment = new ProjetFragment();
        projetFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(projetFragment);
        ((MainActivity) getActivity()).setColors(3, R.color.color_primary, true);
    }


    @Override
    public void onDeleteClick(int position) {
        Prospect prospectASupprimer = mesProspectViewModel.getProspectListe().get(position);

        // Supprime le prospect et met à jour la liste dans l'Adapter
        mesProspectViewModel.removeProspect(prospectASupprimer, getContext());
        adapterProspect.setProspectList(mesProspectViewModel.getProspectListe());

        // Suppression des projets liés
        List<Projet> projets = projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectASupprimer.getNom());
        for (Projet projet : projets) {
            mesProjetsViewModel.removeProjet(projet, getContext());
        }

        // si c'est le dernier prospect selectionné, emepche de retourner sur
        // la page
        if (prospectASupprimer == dernierProspectSelectionne) {
            dernierProspectSelectionne = null;
            ((MainActivity) getActivity()).setColors(3, R.color.gray, false);
        }

    }

    @Override
    public void onModifyClick(int position, String nouveauNomPrenom, String nouveauMail, String nouveauTel, String nouvelleAdresse, String nouvelleVille, String nouveauCodePostal) {
        Prospect prospectAModifier = mesProspectViewModel.getProspectListe().get(position);

        prospectAModifier.setNom(nouveauNomPrenom);
        prospectAModifier.setMail(nouveauMail);
        prospectAModifier.setNumeroTelephone(nouveauTel);
        prospectAModifier.setAdresse(nouvelleAdresse);
        prospectAModifier.setVille(nouvelleVille);
        prospectAModifier.setCodePostal(nouveauCodePostal);
        prospectAModifier.setModifier(true);
        mesProspectViewModel.enregistrerProspect(getContext());
        adapterProspect.notifyItemChanged(position);
    }
}