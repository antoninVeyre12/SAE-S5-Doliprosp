package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.doliprosp.viewModel.SalonViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment implements MyShowAdapter.OnItemClickListener, ShowAdapter.OnItemClickListener {

    private ISalonService salonService;
    private ArrayList<Salon> showSavedList;
    private ShowAdapter adapterShow;
    private MyShowAdapter adapterMyShow;
    private SalonViewModel salonViewModel;
    private UtilisateurViewModel utilisateurViewModel;
    private ImageButton boutonRecherche;
    private TextView erreur;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewMyShow;
    private Button boutonCreerSalon;
    private EditText texteRecherche;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        salonViewModel = new ViewModelProvider(requireActivity()).get(SalonViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        showSavedList = new ArrayList<Salon>();

        boutonCreerSalon = view.findViewById(R.id.buttonCreateShow);
        recyclerView = view.findViewById(R.id.showRecyclerView);
        recyclerViewMyShow = view.findViewById(R.id.myShowRecyclerView);
        boutonRecherche = view.findViewById(R.id.bouton_recherche);
        texteRecherche = view.findViewById(R.id.texte_recherche);
        erreur = view.findViewById(R.id.erreur_pas_de_salons);

        // Salon existant
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        String rechercheVide = "";
        rechercheSalons(rechercheVide);

        //Lance la recherche mais ce coup ci avec le texte saisie
        boutonRecherche.setOnClickListener(v -> {
            String recherche = texteRecherche.getText().toString();
            // Les appels API ne supporte pas les espaces ont les remplace donc par des %20
            String rechercheEspace = recherche.replace(" ", "%20");
            rechercheSalons(rechercheEspace);
        });
    }

    private void rechercheSalons(String recherche){
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity());
        salonService.getSalonsEnregistres(getContext(),recherche, utilisateur, new Outils.APIResponseCallbackArrayTest() {
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                erreur.setVisibility(View.GONE);
                showSavedList = shows;

                // Set l'adapter des shows récupéré
                adapterShow = new ShowAdapter(showSavedList, ShowFragment.this);
                recyclerView.setAdapter(adapterShow);

                // Salon créer
                GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
                recyclerViewMyShow.setLayoutManager(layoutManagerMyShow);


                // Set l'adapter des shows de l'utilisateur
                adapterMyShow = new MyShowAdapter(salonViewModel.getSalonList(), ShowFragment.this);
                recyclerViewMyShow.setAdapter(adapterMyShow);

                boutonCreerSalon.setOnClickListener(v -> {
                    CreateShowDialogFragment dialog = new CreateShowDialogFragment();
                    dialog.show(getChildFragmentManager(), "CreateShowDialog");
                });
                Log.d("azzzzzzzzzzzzz",showSavedList.toString());
            }

            @Override
            public void onError(String error) {
                erreur.setVisibility(View.VISIBLE);
                showSavedList.clear();

            }
        });
    }

    @Override
    public void onDeleteClick(int position) {

        // mets a jour la liste des salons
        salonViewModel.getSalonList().remove(position);
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
        for (Salon salon : showSavedList) {
            // Vérification si le nom du salon correspond à nomRecherche
            if (salon.getNom().equals(nomRecherche)) {
                return true;
            }
        }
        for (Salon salon : salonViewModel.getSalonList()){
            if (salon.getNom().equals(nomRecherche)) {
                return true;
            }
        }
        return false;
    }


}