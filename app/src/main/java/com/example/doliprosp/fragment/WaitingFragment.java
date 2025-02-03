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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.SalonAttenteAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.MesSalonsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment affichant la liste des salons en attente.
 */
public class WaitingFragment extends Fragment {
    private RecyclerView salonAttenteRecyclerView;
    private SalonAttenteAdapter adapterSalons;
    private MesSalonsViewModel mesSalonsViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private ISalonService salonService;

    private List<Salon> salonsSelectionnes;

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
        mesSalonsViewModel = new ViewModelProvider(requireActivity()).get(MesSalonsViewModel.class);
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
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
        ((MainActivity) getActivity()).setColors(0);
        loadSalons(); // Rafraîchir la liste des salons à chaque retour sur la page
    }

    /**
     * Charge la liste des salons locaux et met à jour l'adaptateur du RecyclerView.
     */
    private void loadSalons() {
        ArrayList<Salon> salons = mesSalonsViewModel.getSalonListe();
        adapterSalons = new SalonAttenteAdapter(salons, mesSalonsViewModel,mesProspectViewModel);
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
                    Log.d("aaaaaaaaa", salonsSelectionnes.toString());
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
}
