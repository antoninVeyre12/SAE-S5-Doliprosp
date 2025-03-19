package com.example.doliprosp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.adapters.MesSalonsAdapter;
import com.example.doliprosp.adapters.SalonsAdapter;
import com.example.doliprosp.interfaces.IProjetService;
import com.example.doliprosp.interfaces.IProspectService;
import com.example.doliprosp.interfaces.ISalonService;
import com.example.doliprosp.modeles.Projet;
import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.services.Outils;
import com.example.doliprosp.services.ProjetService;
import com.example.doliprosp.services.ProspectService;
import com.example.doliprosp.services.SalonService;
import com.example.doliprosp.viewmodels.MesProjetsViewModel;
import com.example.doliprosp.viewmodels.MesProspectViewModel;
import com.example.doliprosp.viewmodels.MesSalonsViewModel;
import com.example.doliprosp.viewmodels.SalonsViewModel;
import com.example.doliprosp.viewmodels.UtilisateurViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.doliprosp.fragments.ProjetFragment.dernierProspectSelectionne;
import static com.example.doliprosp.fragments.ProspectFragment.dernierSalonSelectione;

/**
 * Classe comprenant l'ensemble des méthodes de gestion et d'utilisation du fragments salon
 *
 * @author Parcours D IUT de Rodez
 * @version 1.0
 */
public class SalonFragment extends Fragment implements MesSalonsAdapter.OnItemClickListener, SalonsAdapter.OnItemClickListener {

    private ISalonService salonService;
    private IProspectService prospectService;
    private IProjetService projetService;
    private SalonsAdapter adapterSalons;
    private MesSalonsAdapter adapterMesSalons;
    private MesSalonsViewModel mesSalonsViewModel;
    private SalonsViewModel salonsViewModel;
    private UtilisateurViewModel utilisateurViewModel;
    private ImageButton boutonRecherche;
    private TextView erreur;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewMesSalons;
    private Button boutonCreerSalon;
    private EditText texteRecherche;
    private ProgressBar chargement;
    private MesProspectViewModel mesProspectViewModel;
    private MesProjetsViewModel mesProjetViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    /**
     * Méthode appellée au démarrage de l'application pour créer la page Salon
     *
     * @param view               La vue retournée par {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState Si non-null, le fragement est re-construit
     *                           depuis une sauvegarde précédente
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        prospectService = new ProspectService();
        projetService = new ProjetService();
        mesSalonsViewModel = new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        salonsViewModel = new ViewModelProvider(requireActivity()).get(SalonsViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        mesProjetViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        boutonCreerSalon = view.findViewById(R.id.buttonCreateShow);
        recyclerView = view.findViewById(R.id.showRecyclerView);
        recyclerViewMesSalons = view.findViewById(R.id.myShowRecyclerView);
        boutonRecherche = view.findViewById(R.id.bouton_recherche);
        texteRecherche = view.findViewById(R.id.texte_recherche);
        erreur = view.findViewById(R.id.erreur_pas_de_salons);
        chargement = view.findViewById(R.id.chargement);


        // Set l'adapters des salons de l'utilisateur
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        // Set l'adapters des salons de l'utilisateur
        GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
        recyclerViewMesSalons.setLayoutManager(layoutManagerMyShow);

        recupererSalonsDolibarr();
        setupListeners();

    }

    /**
     * Méthode appellée lors du retour sur l'applicationa afin de restaurer l'état
     * précédemment enregistré
     */
    @Override
    public void onResume() {
        super.onResume();
        dernierProspectSelectionne = null;
        // Met en primaryColor l'icone et le texte du fragments
        ((MainActivity) getActivity()).setColors(1, R.color.color_primary, true);
        if (dernierSalonSelectione == null) {
            ((MainActivity) getActivity()).setColors(2, R.color.invalide, false);
        }
        if (ProjetFragment.dernierProspectSelectionne == null) {
            ((MainActivity) getActivity()).setColors(3, R.color.invalide, false);
        }

        adapterMesSalons = new MesSalonsAdapter(mesSalonsViewModel.getSalonListe(), SalonFragment.this, mesSalonsViewModel, salonsViewModel);
        recyclerViewMesSalons.setAdapter(adapterMesSalons);
        adapterMesSalons.notifyDataSetChanged();
        adapterSalons = new SalonsAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
        recyclerView.setAdapter(adapterSalons);
        adapterSalons.notifyDataSetChanged();

    }

    /**
     * Méthode appellée lors de la recherche de salons par critères de l'utilisateur puis affiche
     * les salons correspondants aux critères
     */
    private void recupererSalonsDolibarr() {
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur();
        chargement.setVisibility(View.VISIBLE);

        salonService.getSalonsEnregistres(getContext(), utilisateur, new Outils.APIResponseCallbackArrayTest() {

            /**
             * Méthode appellée en cas de succès de recherche des salons avec le critère de
             * recherche de l'utilisateur pour afficher les salons trouvés suite à la recherche
             * @param shows
             */
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                erreur.setVisibility(View.GONE);
                // remet a 0 la liste des salons a afficher
                salonsViewModel.clear(getContext());

                // rajoute un a un les salons a afficher
                for (Salon salon : shows) {
                    salonsViewModel.addSalon(salon, getContext());

                }
                // Set l'adapters des salons récupéré
                adapterSalons = new SalonsAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
                recyclerView.setAdapter(adapterSalons);
                chargement.setVisibility(View.GONE);

            }

            /**
             * Méthode appellée dans le cas où la recherche ou l'affichage des salons ne se
             * dérouleraient pas comme prévu
             * @param error le message d'erreur affiché à l'utilisateur
             */
            @Override
            public void onError(String error) {
                chargement.setVisibility(View.GONE);
                recyclerView.setAdapter(adapterSalons);
            }
        });
    }

    /**
     * Méthode permettant d'initialiser les boutons lors de la création de la vue
     */
    private void setupListeners() {
        // Lancer la recherche avec le texte saisi
        boutonRecherche.setOnClickListener(v ->
            effectuerRechercheSalons()
        );
        texteRecherche.setOnClickListener(v ->
            effectuerRechercheSalons()
        );

        // Ajouter un salon
        boutonCreerSalon.setOnClickListener(v -> {
            CreationSalonsDialogFragment dialog = new CreationSalonsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("adapterMesSalons", adapterMesSalons);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    private void effectuerRechercheSalons() {
        String recherche = texteRecherche.getText().toString().trim();
        rechercheSalons(recherche);
    }


    private void rechercheSalons(String recherche) {
        chargement.setVisibility(View.VISIBLE);

        List<Salon> mesSalonsListe =
                salonService.rechercheMesSalons(recherche, mesSalonsViewModel);
        List<Salon> salonsListe =
                salonService.rechercheSalons(recherche, salonsViewModel);

        adapterSalons.setSalonsList(salonsListe);
        adapterMesSalons.setSalonsList(mesSalonsListe);

        chargement.setVisibility(View.GONE);

    }

    /**
     * Méthode appellée lors du click sur le bouton de suppression du salon pour le supprimer
     *
     * @param position la position du salon dans la liste
     */
    @Override
    public void onDeleteClick(int position) {
        // mets a jour la liste des salons

        Salon salonASupprimer = mesSalonsViewModel.getSalonListe().get(position);
        mesSalonsViewModel.removeSalon(salonASupprimer, getContext());

        ArrayList<Prospect> prospects = prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonASupprimer.getNom());
        for (Prospect prospect : prospects) {
            mesProspectViewModel.removeProspect(prospect, getContext());
            List<Projet> projets = projetService.getProjetDUnProspect(mesProjetViewModel.getProjetListe(), prospect.getNom());
            for (Projet projet : projets) {
                mesProjetViewModel.removeProjet(projet, getContext());
            }
        }

        adapterMesSalons.setSalonsList(mesSalonsViewModel.getSalonListe());

        // si c'est le dernier salon selectionné, emepche de retourner sur
        // la page
        if (salonASupprimer == dernierSalonSelectione) {
            dernierSalonSelectione = null;
            dernierProspectSelectionne = null;
            ((MainActivity) getActivity()).setColors(2, R.color.gray, false);
            ((MainActivity) getActivity()).setColors(3, R.color.gray, false);
        }
    }

    /**
     * Méthode appellée lors du click sur le l'icone de salonpour accéder à la page des prospects
     *
     * @param position   la position du salon dans la liste
     * @param nouveauNom La liste des salons
     */
    @Override
    public void onModifyClick(int position, String nouveauNom) {

        Salon salonAModifier = mesSalonsViewModel.getSalonListe().get(position);

        ArrayList<Prospect> prospects = prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonAModifier.getNom());
        // Pour chacun des prospects change le salon associé
        for (Prospect prospect : prospects) {
            prospect.setNomSalon(nouveauNom);
        }
        salonAModifier.setNom(nouveauNom);
        mesSalonsViewModel.enregistrerSalons(getContext());
        adapterMesSalons.notifyItemChanged(position);
    }

    /**
     * Méthode appellée lors d'un clic sur un salon
     *
     * @param position
     * @param salonList
     */
    @Override
    public void onSelectClick(int position, List<Salon> salonList) {
        Salon salon = salonList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("salon", salon);
        ProspectFragment prospectFragment = new ProspectFragment();
        prospectFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(prospectFragment);
        ((MainActivity) getActivity()).setColors(2, R.color.color_primary, true);
    }
}