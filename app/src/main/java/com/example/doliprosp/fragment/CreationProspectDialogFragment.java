package com.example.doliprosp.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

/**
 * Fragment de dialogue permettant la création d'un prospect.
 */
public class CreationProspectDialogFragment extends DialogFragment {
    private IProspectService prospectService;
    private TextView erreur;
    private EditText nomPrenomProspect;
    private EditText mailProspect;
    private EditText telProspect;
    private EditText adresseProspect;
    private EditText villeProspect;
    private EditText codePostalProspect;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    private String nomSalon;
    private final String REGEX_MAIl = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\" +
            ".[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^(0[1-9])(\\s?\\d{2}){4}$";
    private ProspectAdapter adapterProspect;
    private MesProspectViewModel mesProspectViewModel;
    private UtilisateurViewModel utilisateurViewModel;

    /**
     * Crée une boîte de dialogue pour la création d'un prospect.
     *
     * @param savedInstanceState État de l'instance sauvegardée.
     * @return Le dialogue créé.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un prospect");
        return dialog;
    }

    /**
     * Initialise la vue du fragment de dialogue.
     *
     * @param inflater           Le gestionnaire de mise en page.
     * @param container          Le conteneur parent.
     * @param savedInstanceState État de l'instance sauvegardée.
     * @return La vue initialisée.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_prospect,
                container, false);

        prospectService = new ProspectService();
        erreur = view.findViewById(R.id.erreur_prospect);
        nomPrenomProspect = view.findViewById(R.id.editTextNomPrenom);
        mailProspect = view.findViewById(R.id.editTextMail);
        telProspect = view.findViewById(R.id.editTextPhone);
        adresseProspect = view.findViewById(R.id.editTextAdresse);
        villeProspect = view.findViewById(R.id.editTextVille);
        codePostalProspect = view.findViewById(R.id.editTextCodePostal);
        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);

        assert getArguments() != null;
        if (getArguments().containsKey("nomDuSalon")) {
            nomSalon = (String) getArguments().getSerializable("nomDuSalon");
            adapterProspect =
                    (ProspectAdapter) getArguments().getSerializable(
                            "adapterProspect");
        }


        // Initialiser le ViewModel
        mesProspectViewModel =
                new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        utilisateurViewModel =
                new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);

        initialisationBouton();

        return view;
    }

    /**
     * Initialise les boutons d'action et gère les interactions utilisateur.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String nom = nomPrenomProspect.getText().toString().trim();
            String mail = mailProspect.getText().toString().trim();
            String tel = telProspect.getText().toString().trim();
            String adresse = adresseProspect.getText().toString().trim();
            String ville = villeProspect.getText().toString().trim();
            String codePostal = codePostalProspect.getText().toString().trim();
            String estClient = "Prospect";

            erreur.setVisibility(View.GONE);

            // Validation des champs
            if (nom.length() <= 2 || nom.length() >= 50) {
                erreur.setText(R.string.erreur_nom_prospect_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (!mail.matches(REGEX_MAIl)) {
                erreur.setText(R.string.erreur_mail_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (!tel.startsWith("0")) {
                erreur.setText(R.string.erreur_tel_prospect_zero);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (!tel.matches(REGEX_TEL)) {
                erreur.setText(R.string.erreur_tel_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (adresse.isEmpty() || adresse.length() >= 60) {
                erreur.setText(adresse.isEmpty() ?
                        R.string.erreur_adresse_prospect_vide :
                        R.string.erreur_adresse_prospect_maxLongueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (ville.isEmpty()) {
                erreur.setText(R.string.erreur_ville_prospect_vide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            if (codePostal.length() < 5) {
                erreur.setText(R.string.erreur_codePostal_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification si le prospect existe déjà
            prospectService.prospectDejaExistantDolibarr(getContext(), tel,
                    utilisateurViewModel.getUtilisateur(),
                    mesProspectViewModel, new Outils.CallbackProspectExiste() {
                        @Override
                        public void onResponse(boolean existeDeja) {
                            if (existeDeja) {
                                erreur.setText(R.string.erreur_prospect_existant);
                                erreur.setVisibility(View.VISIBLE);
                            } else {
                                Prospect prospect = new Prospect(nomSalon,
                                        nom, codePostal, ville, adresse, mail
                                        , tel, estClient, "image");
                                mesProspectViewModel.addProspect(prospect);
                                dismiss();
                            }
                        }
                    });
        });
        boutonAnnuler.setOnClickListener(v -> dismiss());
        adapterProspect.notifyDataSetChanged();
    }
}

