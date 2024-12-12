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
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.viewModel.MesSalonViewModel;
import com.example.doliprosp.viewModel.SalonViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment implements MyShowAdapter.OnItemClickListener, ShowAdapter.OnItemClickListener {

    private ISalonService salonService;
    private ShowAdapter adapterShow;
    private MyShowAdapter adapterMyShow;
    private MesSalonViewModel mesSalonViewModel;
    private SalonViewModel salonViewModel;
    private UtilisateurViewModel utilisateurViewModel;
    private ImageButton boutonRecherche;
    private TextView erreur;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewMyShow;
    private Button boutonCreerSalon;
    private EditText texteRecherche;
    private ProgressBar chargement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        mesSalonViewModel = new ViewModelProvider(requireActivity()).get(MesSalonViewModel.class);
        salonViewModel = new ViewModelProvider(requireActivity()).get(SalonViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        boutonCreerSalon = view.findViewById(R.id.buttonCreateShow);
        recyclerView = view.findViewById(R.id.showRecyclerView);
        recyclerViewMyShow = view.findViewById(R.id.myShowRecyclerView);
        boutonRecherche = view.findViewById(R.id.bouton_recherche);
        texteRecherche = view.findViewById(R.id.texte_recherche);
        erreur = view.findViewById(R.id.erreur_pas_de_salons);
        chargement = view.findViewById(R.id.chargement);


        // Set l'adapter des salons de l'utilisateur
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        // Set l'adapter des salons de l'utilisateur
        GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
        recyclerViewMyShow.setLayoutManager(layoutManagerMyShow);


        String rechercheVide = "";
        rechercheSalons(rechercheVide);
        setupListeners();

    }
    public void onResume() {
        super.onResume();
        adapterMyShow = new MyShowAdapter(mesSalonViewModel.getSalonList(), ShowFragment.this);
        recyclerViewMyShow.setAdapter(adapterMyShow);
        adapterMyShow.notifyDataSetChanged();
        adapterShow = new ShowAdapter(salonViewModel.getSalonList(), ShowFragment.this);
        recyclerView.setAdapter(adapterShow);
        adapterShow.notifyDataSetChanged();
    }

    private void rechercheSalons(String recherche){
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity());
        chargement.setVisibility(View.VISIBLE);

        salonService.getSalonsEnregistres(getContext(),recherche, utilisateur, new Outils.APIResponseCallbackArrayTest() {
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                erreur.setVisibility(View.GONE);
                salonViewModel.clear();
                for (Salon salon : shows){
                    salonViewModel.addSalon(salon);
                    Log.d("aaa",salonViewModel.getSalonList().toString());

                }
                // Set l'adapter des shows récupéré
                adapterShow = new ShowAdapter(salonViewModel.getSalonList(), ShowFragment.this);
                recyclerView.setAdapter(adapterShow);
                chargement.setVisibility(View.GONE);

            }

            @Override
            public void onError(String error) {
                adapterShow = new ShowAdapter(salonViewModel.getSalonList(), ShowFragment.this);
                chargement.setVisibility(View.GONE);
                recyclerView.setAdapter(adapterShow);
                //erreur.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupListeners() {
        // Lancer la recherche avec le texte saisi
        boutonRecherche.setOnClickListener(v -> {
            String recherche = texteRecherche.getText().toString();
            salonViewModel.clear();
            // Remplacer les espaces par %20 pour les requêtes API
            String rechercheEspace = recherche.replace(" ", "%20");
            rechercheSalons(rechercheEspace);
        });

        // Ajouter un salon
        boutonCreerSalon.setOnClickListener(v -> {
            CreateShowDialogFragment dialog = new CreateShowDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("adapterMyShow", (Serializable) adapterMyShow);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }


    @Override
    public void onDeleteClick(int position) {

        // mets a jour la liste des salons
        Salon salonASupprimer = mesSalonViewModel.getSalonList().get(position);
        mesSalonViewModel.removeSalon(salonASupprimer);
        adapterMyShow.notifyItemRemoved(position);
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

    public boolean salonExiste(String nomRecherche) {
        for (Salon salon : salonViewModel.getSalonList()) {
            // Vérification si le nom du salon correspond à nomRecherche
            if (salon.getNom().equals(nomRecherche)) {
                return true;
            }
        }
        for (Salon salon : mesSalonViewModel.getSalonList()){
            if (salon.getNom().equals(nomRecherche)) {
                return true;
            }
        }
        return false;
    }


}