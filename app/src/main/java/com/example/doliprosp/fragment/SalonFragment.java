package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe comprenant l'ensemble des méthodes de gestion et d'utilisation du fragment salon
 * @author Parcours D IUT de Rodez
 * @version 1.0
 */
public class SalonFragment extends Fragment implements MyShowAdapter.OnItemClickListener, ShowAdapter.OnItemClickListener {

    private ISalonService salonService;
    private ShowAdapter adapterSalons;
    private MyShowAdapter adapterMesSalons;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    /**
     * Méthode appellée au démarrage de l'application pour créer la page Salon
     * @param view La vue retournée par {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState Si non-null, le fragement est re-construit
     * depuis une sauvegarde précédente
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        mesSalonsViewModel = new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        salonsViewModel = new ViewModelProvider(requireActivity()).get(SalonsViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        boutonCreerSalon = view.findViewById(R.id.buttonCreateShow);
        recyclerView = view.findViewById(R.id.showRecyclerView);
        recyclerViewMesSalons = view.findViewById(R.id.myShowRecyclerView);
        boutonRecherche = view.findViewById(R.id.bouton_recherche);
        texteRecherche = view.findViewById(R.id.texte_recherche);
        erreur = view.findViewById(R.id.erreur_pas_de_salons);
        chargement = view.findViewById(R.id.chargement);


        // Set l'adapter des salons de l'utilisateur
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        // Set l'adapter des salons de l'utilisateur
        GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
        recyclerViewMesSalons.setLayoutManager(layoutManagerMyShow);


        String rechercheVide = "";
        rechercheSalons(rechercheVide);
        setupListeners();

    }

    /**
     * Méthode appellée lors du retour sur l'applicationa fin de restaurer l'état
     * précédemment enregistré
     */
    public void onResume() {
        super.onResume();
        adapterMesSalons = new MyShowAdapter(mesSalonsViewModel.getSalonListe(), SalonFragment.this);
        recyclerViewMesSalons.setAdapter(adapterMesSalons);
        adapterMesSalons.notifyDataSetChanged();
        adapterSalons = new ShowAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
        recyclerView.setAdapter(adapterSalons);
        adapterSalons.notifyDataSetChanged();
    }

    /**
     * Méthode appellée lors de la recherche de salons par critères de l'utilisateur puis affiche
     * les salons correspondants aux critères
     * @param recherche la recherche sur critères de l'utilisateur
     */
    private void rechercheSalons(String recherche){
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity());
        chargement.setVisibility(View.VISIBLE);

        salonService.getSalonsEnregistres(getContext(),recherche, utilisateur, new Outils.APIResponseCallbackArrayTest() {

            /**
             * Méthode appellée en cas de succès de recherche des salons avec le critère de
             * recherche de l'utilisateur pour afficher les salons trouvés suite à la recherche
             * @param shows
             */
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                erreur.setVisibility(View.GONE);
                // remet a 0 la liste des salons a afficher
                salonsViewModel.clear();
                // rajoute un a un les salons a afficher
                for (Salon salon : shows){
                    salonsViewModel.addSalon(salon);
                    Log.d("aaa", salonsViewModel.getSalonListe().toString());

                }
                // Set l'adapter des salons récupéré
                adapterSalons = new ShowAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
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
                adapterSalons = new ShowAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
                chargement.setVisibility(View.GONE);
                recyclerView.setAdapter(adapterSalons);
                //erreur.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     *
     */
    private void setupListeners() {
        // Lancer la recherche avec le texte saisi
        boutonRecherche.setOnClickListener(v -> {
            String recherche = texteRecherche.getText().toString();
            salonsViewModel.clear();
            // Remplacer les espaces par %20 pour les requêtes API
            String rechercheEspace = recherche.replace(" ", "%20");
            rechercheSalons(rechercheEspace);
        });

        // Ajouter un salon
        boutonCreerSalon.setOnClickListener(v -> {
            CreationSalonsDialogFragment dialog = new CreationSalonsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("adapterMesSalons", (Serializable) adapterMesSalons);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    /**
     * Méthode appellée lors du click sur le bouton de suppression du salon pour le supprimer
     * @param position la position du salon dans la liste
     */
    @Override
    public void onDeleteClick(int position) {

        // mets a jour la liste des salons
        Salon salonASupprimer = mesSalonsViewModel.getSalonListe().get(position);
        mesSalonsViewModel.removeSalon(salonASupprimer);
        adapterMesSalons.notifyItemRemoved(position);
    }

    /**
     * Méthode appellée lors du click sur le l'icone de salonpour accéder à la page des prospects
     * @param position la position du salon dans la liste
     * @param salonList La liste des salons
     */
    @Override
    public void onSelectClick(int position, List<Salon> salonList) {
        Salon salon = salonList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("salon", (Serializable) salon);
        ProspectFragment prospectFragment = new ProspectFragment();
        prospectFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(prospectFragment);
        ((MainActivity) getActivity()).setColors(2);
    }
}