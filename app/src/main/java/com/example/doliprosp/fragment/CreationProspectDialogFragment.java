package com.example.doliprosp.fragment;

import android.app.Dialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.adapter.ProspectRechercheAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CreationProspectDialogFragment extends DialogFragment implements ProspectRechercheAdapter.OnItemClickListener {
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

    private AppCompatButton boutonPlus;
    private AppCompatButton boutonMoins;
    private EditText texteRecherche1;
    private EditText texteRecherche2;
    private ImageButton boutonRecherche1;
    private ImageButton boutonRecherche2;

    private RecyclerView prospectRecyclerView;
    private ProspectRechercheAdapter adapter;

    private String nomSalon;
    private final String REGEX_MAIl = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^(0[1-9])(\\s?\\d{2}){4}$";
    private ProspectAdapter adapterProspect;

    private ArrayList<Prospect> listProspectRecherche = new ArrayList<>();
    private ArrayList<Prospect> premiereListeProspect = new ArrayList<>();
    private ArrayList<Prospect> deuxiemeListeProspect = new ArrayList<>();


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

        adapter = new ProspectRechercheAdapter(listProspectRecherche, CreationProspectDialogFragment.this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        prospectRecyclerView.setLayoutManager(layoutManager);
        prospectRecyclerView.setAdapter(adapter);

        prospectService = new ProspectService();
        if (getArguments().containsKey("nomDuSalon")) {
            nomSalon = (String) getArguments().getSerializable("nomDuSalon");
            adapterProspect = (ProspectAdapter) getArguments().getSerializable("adapterProspect");
        }


        // Initialiser le ViewModel
        mesProspectViewModel = new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);

        initialisationBouton();

        boutonRecherche1.setOnClickListener(v -> {
            String valeurCritere = texteRecherche1.getText().toString();
            premiereListeProspect = prospectClientExiste(valeurCritere, "nom");
        });

        boutonRecherche2.setOnClickListener(v -> {
            String valeurCritere = texteRecherche1.getText().toString();
            deuxiemeListeProspect = prospectClientExiste(valeurCritere, "nom");
            Prospect prospectARetourner = checheProspectEnCommun(premiereListeProspect, deuxiemeListeProspect);
            if (prospectARetourner != null) {
                afficherValeurs(prospectARetourner);
            }
        });

        return vue;
    }

    private void afficherValeurs(Prospect prospectARetourner) {
        nomPrenomProspect.setText(prospectARetourner.getNom());
        mailProspect.setText(prospectARetourner.getMail());
        telProspect.setText(prospectARetourner.getNumeroTelephone());
        adresseProspect.setText(prospectARetourner.getAdresse());
        villeProspect.setText(prospectARetourner.getVille());
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
        boutonPlus = vue.findViewById(R.id.bouton_plus);
        boutonMoins = vue.findViewById(R.id.bouton_moins);
        texteRecherche1 = vue.findViewById(R.id.texte_recherche_1);
        texteRecherche2 = vue.findViewById(R.id.texte_recherche_2);
        boutonRecherche1 = vue.findViewById(R.id.bouton_recherche_1);
        boutonRecherche2 = vue.findViewById(R.id.bouton_recherche_2);
        prospectRecyclerView = vue.findViewById(R.id.prospectRecyclerView);

    }

    /**
     * Méthode pour vérifier si un prospect existe pour un client, basée sur les critères de recherche.
     *
     * @param recherche Le texte de recherche pour le prospect.
     * @param tri       La méthode de tri des prospects.
     */
    private ArrayList<Prospect> prospectClientExiste(String recherche, String tri) {
        Utilisateur utilisateur = utilisateurViewModel.getUtilisateur();
        ArrayList<Prospect> resultat = new ArrayList<>();

        chargement.setVisibility(View.VISIBLE);
        prospectService.prospectClientExiste(getContext(), recherche, tri, utilisateur, new Outils.APIResponseCallbackArrayProspect() {
            @Override
            public void onSuccess(ArrayList<Prospect> response) {
                resultat.addAll(response);
                listProspectRecherche.addAll(response);
                Log.d("nombre", String.valueOf(listProspectRecherche.size()));
                chargement.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("recherche", "aucun propsect trouvé");
            }
        });
        return resultat;
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

    @Override
    public void onSelectClick(Prospect prospect) {

        prospectRecyclerView.setVisibility(View.GONE);
        nomPrenomProspect.setText(prospect.getNom());
        mailProspect.setText(prospect.getMail());
        telProspect.setText(prospect.getNumeroTelephone());
        adresseProspect.setText(prospect.getAdresse());
        villeProspect.setText(prospect.getVille());
        codePostalProspect.setText(String.valueOf(prospect.getCodePostal()));

    }

    @Override
    public void onDeleteClick(int position) {

    }
}

