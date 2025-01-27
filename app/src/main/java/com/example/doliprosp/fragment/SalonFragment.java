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

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalonFragment extends Fragment implements MyShowAdapter.OnItemClickListener, ShowAdapter.OnItemClickListener {

    private ISalonService salonService;
    private IProspectService prospectService;

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
    private MesProspectViewModel mesProspectViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        prospectService = new ProspectService();
        mesSalonsViewModel = new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        salonsViewModel = new ViewModelProvider(requireActivity()).get(SalonsViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        utilisateurViewModel.initSharedPreferences(getContext());
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
    public void onResume() {
        super.onResume();
        adapterMesSalons = new MyShowAdapter(mesSalonsViewModel.getSalonListe(), SalonFragment.this);
        recyclerViewMesSalons.setAdapter(adapterMesSalons);
        adapterMesSalons.notifyDataSetChanged();
        adapterSalons = new ShowAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
        recyclerView.setAdapter(adapterSalons);
        adapterSalons.notifyDataSetChanged();
    }

    private void rechercheSalons(String recherche){
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur();
        Log.d("urlll", utilisateur.getUrl());
        chargement.setVisibility(View.VISIBLE);

        salonService.getSalonsEnregistres(getContext(),recherche, utilisateur, new Outils.APIResponseCallbackArrayTest() {
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                erreur.setVisibility(View.GONE);
                // remet a 0 la liste des salons a afficher
                salonsViewModel.clear();
                // rajoute un a un les salons a afficher
                for (Salon salon : shows){
                    salonsViewModel.addSalon(salon);

                }
                // Set l'adapter des shows récupéré
                adapterSalons = new ShowAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
                recyclerView.setAdapter(adapterSalons);
                chargement.setVisibility(View.GONE);

            }

            @Override
            public void onError(String error) {
                adapterSalons = new ShowAdapter(salonsViewModel.getSalonListe(), SalonFragment.this);
                chargement.setVisibility(View.GONE);
                recyclerView.setAdapter(adapterSalons);
                //erreur.setVisibility(View.VISIBLE);
            }
        });
    }

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


    @Override
    public void onDeleteClick(int position) {

        // mets a jour la liste des salons
        Salon salonASupprimer = mesSalonsViewModel.getSalonListe().get(position);
        mesSalonsViewModel.removeSalon(salonASupprimer);
        ArrayList<Prospect> prospects = prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(),salonASupprimer.getNom());
        for (Prospect prospect : prospects) {
            mesProspectViewModel.removeProspect(prospect);
        }
        adapterMesSalons.notifyItemRemoved(position);
    }

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