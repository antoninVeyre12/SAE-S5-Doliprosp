package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CreationProspectDialogFragment extends DialogFragment {
    private IProspectService prospectService;
    private UtilisateurViewModel utilisateurViewModel;
    private TextView erreur;
    private EditText nomPrenomProspect;
    private EditText mailProspect;
    private EditText telProspect;
    private EditText adresseProspect;
    private EditText villeProspect;
    private EditText codePostalProspect;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    private ProgressBar chargement;

    private String nomSalon;
    private final String REGEX_MAIl = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^(0[1-9])(\\s?\\d{2}){4}$";
    private ProspectAdapter adapterProspect;

    private MesProspectViewModel mesProspectViewModel;

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un prospect");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.dialog_create_prospect, container, false);

        intialiseVue(vue);

        prospectService = new ProspectService();
        if (getArguments().containsKey("nomDuSalon")) {
            nomSalon = (String) getArguments().getSerializable("nomDuSalon");
            adapterProspect = (ProspectAdapter) getArguments().getSerializable("adapterProspect");
        }


        // Initialiser le ViewModel
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);

        initialisationBouton();

        return vue;
    }

    private void intialiseVue(View vue) {
        erreur = vue.findViewById(R.id.erreur);
        nomPrenomProspect = vue.findViewById(R.id.editTextNomPrenom);
        mailProspect = vue.findViewById(R.id.editTextMail);
        telProspect = vue.findViewById(R.id.editTextPhone);
        adresseProspect = vue.findViewById(R.id.editTextAdresse);
        villeProspect = vue.findViewById(R.id.editTextVille);
        codePostalProspect = vue.findViewById(R.id.editTextCodePostal);
        boutonEnvoyer = vue.findViewById(R.id.buttonSubmit);
        boutonAnnuler = vue.findViewById(R.id.buttonCancel);
        chargement = vue.findViewById(R.id.chargement);
    }

    /**
     * Méthode pour vérifier si un prospect existe pour un client, basée sur les critères de recherche.
     *
     * @param recherche Le texte de recherche pour le prospect.
     * @param champ     Le champ sur lequel effectuer la recherche.
     * @param tri       La méthode de tri des prospects.
     */
    private void prospectClientExiste(String recherche, String champ, String tri) {
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur();

        chargement.setVisibility(View.VISIBLE);
        prospectService.prospectClientExiste(getContext(), recherche, tri, utilisateur, new Outils.APIResponseCallbackArrayProspect() {
            @Override
            public void onSuccess(ArrayList<Prospect> response) {
                // TODO: afficher la liste des prosepcts sous forme de liste
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("recherche", "aucun propsect trouvé");
                // TODO: Implémenter la gestion des erreurs
            }
        });
    }

    private Prospect checheProspectEnCommun(ArrayList<Prospect> premiereListe, ArrayList<Prospect> deuxiemeListe) {
        for (Prospect prospect : premiereListe) {
            if (deuxiemeListe.contains(prospect)) {
                return prospect;
            }
        }
        Toast.makeText(getContext(), getString(R.string.recherche_prospect_nul), Toast.LENGTH_LONG).show();
        return null;
    }

    private void initialisationBouton() {
        boutonEnvoyer.setOnClickListener(v -> {
            String nom = nomPrenomProspect.getText().toString().trim();
            String mail = mailProspect.getText().toString().trim();
            String tel = telProspect.getText().toString().trim();
            String adresse = adresseProspect.getText().toString().trim();
            String ville = villeProspect.getText().toString().trim();
            String estClient = "Prospect";

            erreur.setVisibility(View.GONE); // Cacher l'erreur au début

            // Vérification du nom
            if (nom.length() <= 2 || nom.length() >= 50) {
                erreur.setText(R.string.erreur_nom_prospect_longueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification de l'email
            if (!mail.matches(REGEX_MAIl)) {
                erreur.setText(R.string.erreur_mail_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification du téléphone
            if (!tel.matches(REGEX_TEL)) {
                erreur.setText(R.string.erreur_tel_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification de l'adresse
            if (adresse.length() >= 60) {
                erreur.setText(R.string.erreur_adresse_prospect_maxLongueur);
                erreur.setVisibility(View.VISIBLE);
                return;
            } else if (adresse.isEmpty()) {
                erreur.setText(R.string.erreur_adresse_prospect_vide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification de la ville
            if (ville.isEmpty()) {
                erreur.setText(R.string.erreur_ville_prospect_vide);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            int codePostal; // Déclarer le code postal comme un entier
            try {
                codePostal = Integer.parseInt(codePostalProspect.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Gérer le cas où le champ est vide ou contient une valeur non valide
                erreur.setText(R.string.erreur_codePostal_prospect_invalid);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification du code postal
            if (codePostal < 01000 || codePostal > 99999) {
                erreur.setText(R.string.erreur_codePostal_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification si estClient est activé
            /*if (estClient) {
                erreur.setText(R.string.erreur_estClient_prospect);
                erreur.setVisibility(View.VISIBLE);
                return;
            }*/

            // Tout est valide, créer le prospect
            Prospect prospect = new Prospect(nomSalon, nom, codePostal, ville, adresse, mail, tel, estClient, "image");
            mesProspectViewModel.addProspect(prospect);

            if (adapterProspect != null) {
            }
            dismiss();
            Bundle bundle = new Bundle();
            bundle.putSerializable("prospect", (Serializable) prospect);
            ProjetFragment projetFragment = new ProjetFragment();
            projetFragment.setArguments(bundle);
            ((MainActivity) getActivity()).loadFragment(projetFragment);
            ((MainActivity) getActivity()).setColors(3, R.color.color_primary, true);
        });

        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });
        adapterProspect.notifyDataSetChanged();
    }
}

