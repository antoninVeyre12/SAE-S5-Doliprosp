package com.example.doliprosp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.adapters.SalonAttenteAdapter;
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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.doliprosp.fragments.ProjetFragment.dernierProspectSelectionne;
import static com.example.doliprosp.fragments.ProspectFragment.dernierSalonSelectione;

/**
 * Fragment affichant la liste des salons en attente.
 */
public class SalonAttenteFragment extends Fragment {
    private RecyclerView salonAttenteRecyclerView;
    private SalonAttenteAdapter adapterSalons;
    private MesSalonsViewModel mesSalonsViewModel;
    private SalonsViewModel salonsViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private MesProjetsViewModel mesProjetsViewModel;
    private UtilisateurViewModel utilisateurViewModel;
    private TextView erreurSalons;
    private ISalonService salonService;
    private IProspectService prospectService;
    private IProjetService projetService;

    private List<Salon> salonsSelectionnes;
    private List<Prospect> prospectSelectionnes;
    private List<Projet> projetsSelectionnes;
    private Utilisateur utilisateur;

    private Button boutonEnvoyer, boutonToutSelectionne;

    /**
     * Crée et retourne la vue associée à ce fragments.
     *
     * @param inflater           L'objet LayoutInflater qui peut être utilisé
     *                           pour inflater des vues XML.
     * @param container          Le parent auquel la vue du fragments doit
     *                           être attachée.
     * @param savedInstanceState L'état sauvegardé précédent du fragments.
     * @return La vue racine du fragments.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salon_attente,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        prospectService = new ProspectService();
        projetService = new ProjetService();

        utilisateurViewModel =
                new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateur = utilisateurViewModel.getUtilisateur();
        mesSalonsViewModel =
                new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        salonsViewModel =
                new ViewModelProvider(requireActivity()).get(SalonsViewModel.class);
        mesProspectViewModel =
                new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        mesProjetsViewModel =
                new ViewModelProvider(requireActivity()).get(MesProjetsViewModel.class);
        salonAttenteRecyclerView =
                view.findViewById(R.id.salonAttenteRecyclerView);
        boutonToutSelectionne = view.findViewById(R.id.btn_tout_selectionner);
        erreurSalons = view.findViewById(R.id.erreur_pas_de_salons);
        salonAttenteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        boutonEnvoyer = view.findViewById(R.id.btn_envoyer_salon_attente);
        setupListeners();
        loadSalons();
    }

    /**
     * Méthode appelée lorsque le fragments reprend son activité.
     * Met à jour la liste des salons et applique le thème de couleur.
     */
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setColors(0, R.color.color_primary,
                true);
        if (ProspectFragment.dernierSalonSelectione == null) {
            ((MainActivity) getActivity()).setColors(2, R.color.invalide,
                    false);
        }
        if (ProjetFragment.dernierProspectSelectionne == null) {
            ((MainActivity) getActivity()).setColors(3, R.color.invalide,
                    false);
        }
    }

    /**
     * Charge la liste des salons locaux et met à jour l'adaptateur du
     * RecyclerView.
     */
    private void loadSalons() {
        List<Salon> salons = new ArrayList<>(mesSalonsViewModel.getSalonListe());
        erreurSalons.setVisibility(View.GONE);
        //int compteurDeSalonAffiche = 0;
        for (Salon salonEnregistrer : salonsViewModel.getSalonListe()) {

            if (!prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonEnregistrer.getNom()).isEmpty()) {
                salons.add(salonEnregistrer);
                //compteurDeSalonAffiche++;
            }
        }
        if (salons.isEmpty()) {
            erreurSalons.setText(R.string.erreur_aucun_salon);
            erreurSalons.setVisibility(View.VISIBLE);
        }
        adapterSalons = new SalonAttenteAdapter(salons, mesProspectViewModel, mesProjetsViewModel);
        salonAttenteRecyclerView.setAdapter(adapterSalons);
    }

    private void setupListeners() {
        boutonEnvoyer.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_confirm_send, null);

            Button btnAnnuler = layout.findViewById(R.id.buttonCancel);
            Button btnEnvoyer = layout.findViewById(R.id.buttonSubmit);
            CheckBox checkboxConfirmation =
                    layout.findViewById(R.id.checkbox_confirmation);
            TextView erreur = layout.findViewById(R.id.erreur);

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(v.getContext())
                            .setView(layout)
                            .setCancelable(false);

            AlertDialog dialog = builder.create();
            dialog.show();

            btnAnnuler.setOnClickListener(v1 ->
                    dialog.dismiss()
            );
            btnEnvoyer.setOnClickListener(v1 -> {
                if (checkboxConfirmation.isChecked()) {
                    salonsSelectionnes =
                            salonService.getListeSalonsSelectionnes(mesSalonsViewModel, salonsViewModel);
                    envoyerSalons();
                    erreur.setVisibility(View.GONE);
                    dialog.dismiss();
                    erreurSalons.setVisibility(View.GONE);
                } else {
                    erreur.setText(R.string.erreur_checkbox);
                    erreur.setVisibility(View.VISIBLE);
                }

            });
        });

        boutonToutSelectionne.setOnClickListener(v ->
                adapterSalons.selectAllSalons()
        );
    }

    private void envoyerSalons() {
        erreurSalons.setVisibility(View.GONE);
        for (Salon salonAEnvoyer : salonsSelectionnes) {
            salonService.recupererIdSalon(utilisateur, salonAEnvoyer.getNom()
                    , getContext(), new Outils.APIResponseCallbackString() {
                        @Override
                        public void onSuccess(String response) {
                            erreurSalons.setVisibility(View.GONE);
                            prospectSelectionnes =
                                    prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonAEnvoyer.getNom());
                            int idSalon = Integer.parseInt(response);

                            empecheRetourApresEnvoie(salonAEnvoyer);


                            salonsViewModel.removeSalon(salonAEnvoyer,
                                    getContext());
                            mesSalonsViewModel.removeSalon(salonAEnvoyer, getContext());
                            loadSalons();

                            envoyerProspects(prospectSelectionnes, idSalon,
                                    salonAEnvoyer);


                        }

                        @Override
                        public void onError(String errorMessage) {
                            salonService.envoyerSalon(utilisateur, getContext(),
                                    salonAEnvoyer,
                                    new Outils.APIResponseCallbackString() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int idSalon =
                                                    Integer.parseInt(response);
                                            prospectSelectionnes =
                                                    prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(), salonAEnvoyer.getNom());

                                            mesSalonsViewModel.removeSalon(salonAEnvoyer, getContext());
                                            // Reload les salons
                                            loadSalons();

                                            empecheRetourApresEnvoie(salonAEnvoyer);
                                            envoyerProspects(prospectSelectionnes, idSalon,
                                                    salonAEnvoyer);

                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            erreurSalons.setVisibility(View.VISIBLE);
                                            erreurSalons.setText(R.string.erreur_connexion);
                                        }
                                    });
                        }
                    });
        }
    }

    private void envoyerProspects(List<Prospect> listeAEnvoyer, int idSalon,
                                  Salon salonAEnvoyer) {
        for (Prospect prospectAEnvoyer : listeAEnvoyer) {

            if (!prospectAEnvoyer.getIdDolibarr().equals("false")) {
                lieProspectAvecSalon(prospectAEnvoyer, idSalon, salonAEnvoyer);
            } else {
                prospectService.prospectDejaExistantDolibarr(getContext()
                        , prospectAEnvoyer.getNumeroTelephone(),
                        utilisateurViewModel.getUtilisateur(), mesProspectViewModel,
                        new Outils.CallbackProspectExiste() {
                            @Override
                            public void onResponse(String idDolibarr) {
                                Log.d("idDolibarr", idDolibarr);
                                prospectAEnvoyer.setIdDolibarr(idDolibarr);
                                lieProspectAvecSalon(prospectAEnvoyer, idSalon, salonAEnvoyer);

                            }

                            @Override
                            public void onError() {
                                prospectService.envoyerProspect(utilisateur, getContext(), prospectAEnvoyer, idSalon, new Outils.APIResponseCallbackString() {
                                    @Override
                                    public void onSuccess(String idProspect) throws JSONException {

                                        projetsSelectionnes = projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(),
                                                prospectAEnvoyer.getNom());

                                        envoyerProjets(projetsSelectionnes,
                                                Integer.parseInt(idProspect), salonAEnvoyer, prospectAEnvoyer);
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        envoyerVersModule(null, prospectAEnvoyer, salonAEnvoyer,
                                                0);
                                    }
                                });
                            }

                        });
            }
            mesProspectViewModel.removeProspect(prospectAEnvoyer, getContext());
        }
    }

    private void lieProspectAvecSalon(Prospect prospectAEnvoyer, int idSalon,
                                      Salon salonAEnvoyer) {
        int idProspect =
                Integer.parseInt(prospectAEnvoyer.getIdDolibarr());
        prospectService.lieProspectSalon(utilisateur, getContext(),
                idSalon, idProspect);

        modificationClient(utilisateur, prospectAEnvoyer, idProspect);

        projetsSelectionnes = projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(),
                prospectAEnvoyer.getNom());

        envoyerProjets(projetsSelectionnes,
                Integer.parseInt(prospectAEnvoyer.getIdDolibarr()), salonAEnvoyer,
                prospectAEnvoyer);
    }


    private void envoyerProjets(List<Projet> listeAEnvoyer, int idProspect,
                                Salon salonAEnvoyer,
                                Prospect prospectAEnvoyer) {
        for (Projet projetAEnvoyer : listeAEnvoyer) {
            projetService.envoyerProjet(utilisateur, getContext(),
                    projetAEnvoyer, idProspect);
            envoyerVersModule(projetAEnvoyer, prospectAEnvoyer, salonAEnvoyer,
                    idProspect);
            mesProjetsViewModel.removeProjet(projetAEnvoyer, getContext());
        }
    }

    private void envoyerVersModule(Projet projetAEnvoyer,
                                   Prospect prospectAEnvoyer,
                                   Salon salonAEnvoyer, int idProspect) {
        projetService.envoyerVersModule(utilisateur, getContext(),
                projetAEnvoyer, prospectAEnvoyer,
                salonAEnvoyer, idProspect);
    }

    private void modificationClient(Utilisateur utilisateur,
                                    Prospect prospectAEnvoyer,
                                    int idProspect) {
        if (prospectAEnvoyer.getModifier()) {
            prospectService.modifierClient(getContext(), utilisateur,
                    prospectAEnvoyer,
                    String.valueOf(idProspect));
        }

    }

    private void empecheRetourApresEnvoie(Salon salonAEnvoyer) {
        if (salonAEnvoyer == dernierSalonSelectione) {
            dernierSalonSelectione = null;
            dernierProspectSelectionne = null;
            ((MainActivity) getActivity()).setColors(2, R.color.gray, false);
            ((MainActivity) getActivity()).setColors(3, R.color.gray, false);
        }
    }

}

