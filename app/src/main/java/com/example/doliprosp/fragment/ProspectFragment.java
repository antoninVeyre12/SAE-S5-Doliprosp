package com.example.doliprosp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class  ProspectFragment extends Fragment implements ProspectAdapter.OnItemClickListener{

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            salonActuel = (Salon) bundle.getSerializable("salon");
            dernierSalonSelectione = salonActuel;
        } else {
            if (dernierSalonSelectione != null) {
                salonActuel = dernierSalonSelectione;
            } else {
            }
        }
        return inflater.inflate(R.layout.fragment_prospect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        prospectService = new ProspectService();
        boutonCreerProspect = view.findViewById(R.id.buttonCreateProspect);
        salonActuelEditText = view.findViewById(R.id.salonActuel);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        prospectRecyclerView = view.findViewById(R.id.prospectRecyclerView);
        chargement = view.findViewById(R.id.chargement);
        // recherche = view.findViewById(R.id.recherche).toString();
        // champ = view.findViewById(R.id.champ).toString();
        // Set l'adapter des salons de l'utilisateur
        // tri = view.findViewById(R.id.tri).toString();
        //prospectClientExiste(recherche, champ, tri);
        salonActuelEditText.setText(salonActuel.getNom());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        prospectRecyclerView.setLayoutManager(layoutManager);
        setupListeners();
    }

    private void setupListeners() {
        // Ajouter un prospect
        boutonCreerProspect.setOnClickListener(v -> {
            CreationProspectDialogFragment dialog = new CreationProspectDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("nomDuSalon", (Serializable) salonActuel.getNom());
            bundle.putSerializable("adapterProspect", (Serializable) adapterProspect);
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

    public void onResume() {
        super.onResume();
        Log.d("ffffff", String.valueOf(mesProspectViewModel.getProspectListe()));
        Log.d("ggggg", String.valueOf(prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(),salonActuel.getNom())));
        adapterProspect = new ProspectAdapter(prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(),salonActuel.getNom()),ProspectFragment.this);
        prospectRecyclerView.setAdapter(adapterProspect);
        adapterProspect.notifyDataSetChanged();
    }


    private void prospectClientExiste(String recherche, String champ, String tri) {
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur();
        chargement.setVisibility(View.VISIBLE);
        prospectService.prospectClientExiste(getContext(), recherche, champ, tri, utilisateur, new Outils.APIResponseCallbackArrayProspect() {

            @Override
            public void onSuccess(ArrayList<Prospect> response) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void onSelectClick(int position, List<Prospect> prospectListe) {
        Prospect prospect = prospectListe.get(position);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("prospect", (Serializable) prospect);
        ProjetFragment projetFragment = new ProjetFragment();
        //projetFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(projetFragment);
        ((MainActivity) getActivity()).setColors(3);
    }
}