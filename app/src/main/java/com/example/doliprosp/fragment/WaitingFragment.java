package com.example.doliprosp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProjetService;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.SalonAttenteAdapter;
import com.example.doliprosp.viewModel.MesProjetsViewModel;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Fragment affichant la liste des salons en attente.
 */
public class WaitingFragment extends Fragment {
    private RecyclerView salonAttenteRecyclerView;
    private SalonAttenteAdapter adapterSalons;
    private MesSalonsViewModel mesSalonsViewModel;
    private SalonsViewModel salonsViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private MesProjetsViewModel mesProjetsViewModel;
    private UtilisateurViewModel utilisateurViewModel;
    private ISalonService salonService;
    private IProspectService prospectService;
    private IProjetService projetService;

    private List<Salon> salonsSelectionnes;
    private List<Prospect> prospectSelectionnes;
    private List<Projet> projetsSelectionnes;
    private Utilisateur utilisateur;

    private Button boutonEnvoyer;
    private Button boutonToutSelectionne;

    /**
     * Crée et retourne la vue associée à ce fragment.
     *
     * @param inflater           L'objet LayoutInflater qui peut être utilisé pour inflater des vues XML.
     * @param container          Le parent auquel la vue du fragment doit être attachée.
     * @param savedInstanceState L'état sauvegardé précédent du fragment.
     * @return La vue racine du fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salon_attente, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        prospectService = new ProspectService();
        projetService = new ProjetService();

        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateurViewModel.initSharedPreferences(getContext());
        utilisateur = utilisateurViewModel.getUtilisateur();
        mesSalonsViewModel = new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        salonsViewModel = new ViewModelProvider(requireActivity()).get(SalonsViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        mesProjetsViewModel = new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        salonAttenteRecyclerView = view.findViewById(R.id.salonAttenteRecyclerView);
        boutonToutSelectionne = view.findViewById(R.id.btn_tout_selectionner);
        salonAttenteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        boutonEnvoyer = view.findViewById(R.id.btn_envoyer_salon_attente);
        setupListeners();
        loadSalons();
    }

    /**
     * Méthode appelée lorsque le fragment reprend son activité.
     * Met à jour la liste des salons et applique le thème de couleur.
     */
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setColors(0, R.color.color_primary, true);
        if (ProspectFragment.dernierSalonSelectione == null) {
            ((MainActivity) getActivity()).setColors(2, R.color.invalide, false);
        }
        if (ProjetFragment.dernierProspectSelectionne == null) {
            ((MainActivity) getActivity()).setColors(3, R.color.invalide, false);
        }
        loadSalons(); // Rafraîchir la liste des salons à chaque retour sur la page
    }

    /**
     * Charge la liste des salons locaux et met à jour l'adaptateur du RecyclerView.
     */
    private void loadSalons() {
        ArrayList<Salon> salons = mesSalonsViewModel.getSalonListe();
        adapterSalons = new SalonAttenteAdapter(salons, mesSalonsViewModel, mesProspectViewModel);
        salonAttenteRecyclerView.setAdapter(adapterSalons);
    }

    private void setupListeners() {
        boutonEnvoyer.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_confirm_send, null);

            Button btnAnnuler = layout.findViewById(R.id.buttonCancel);
            Button btnEnvoyer = layout.findViewById(R.id.buttonSubmit);
            CheckBox checkboxConfirmation = layout.findViewById(R.id.checkbox_confirmation);
            TextView erreur = layout.findViewById(R.id.erreur);

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                    .setView(layout)
                    .setCancelable(false);

            AlertDialog dialog = builder.create();
            dialog.show();

            btnAnnuler.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

            btnEnvoyer.setOnClickListener(v1 -> {
                if (checkboxConfirmation.isChecked()) {
                    salonsSelectionnes = salonService.getListeSalonsSelectionnes(mesSalonsViewModel);
                    envoyerSalons();

                    erreur.setVisibility(View.GONE);
                    dialog.dismiss();

                } else {
                    erreur.setText(R.string.erreur_checkbox);
                    erreur.setVisibility(View.VISIBLE);
                }

            });
        });

        boutonToutSelectionne.setOnClickListener(v -> {
            // Appeler une méthode dans l'adaptateur pour sélectionner toutes les checkboxes
            adapterSalons.selectAllSalons();
        });
    }

    private void envoyerSalons() {
        Log.d("aaaaaaaaa", salonsSelectionnes.toString());
        for (Salon salonAEnvoyer : salonsSelectionnes) {
            salonService.recupererIdSalon(utilisateur, salonAEnvoyer.getNom(), getContext(), new Outils.APIResponseCallbackString() {
                @Override
                public void onSuccess(String response) {
                    prospectSelectionnes = prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonAEnvoyer.getNom());
                    int idSalon = Integer.parseInt(response);
                    envoyerProspects(prospectSelectionnes, idSalon);

                }

                @Override
                public void onError(String errorMessage) {
                    salonService.envoyerSalon(utilisateur, getContext(), salonAEnvoyer, new Outils.APIResponseCallbackString() {
                        @Override
                        public void onSuccess(String response) {
                            int idSalon = Integer.parseInt(response);
                            prospectSelectionnes = prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonAEnvoyer.getNom());
                            envoyerProspects(prospectSelectionnes, idSalon);


                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.d("aaaa", "aaaaaa");
                        }
                    });
                }
            });
            mesSalonsViewModel.removeSalon(salonAEnvoyer);
            adapterSalons.notifyDataSetChanged();
        }
    }

    private void envoyerProspects(List<Prospect> listeAEnvoyer, int idSalon) {
        for (Prospect prospectAEnvoyer : listeAEnvoyer) {
            prospectService.envoyerProspect(utilisateur, getContext(), prospectAEnvoyer, idSalon);
            projetsSelectionnes = projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), prospectAEnvoyer.getNom());
            envoyerProjets(projetsSelectionnes, 1);
        }
    }

    private void envoyerProjets(List<Projet> listeAEnvoyer, int idProspect) {
        for (Projet projetAEnvoyer : listeAEnvoyer) {
            projetService.envoyerProjet(utilisateur, getContext(), projetAEnvoyer, idProspect);
        }
    }
}

