package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.ProspectService;
import com.example.doliprosp.adapter.ProspectAdapter;
import com.example.doliprosp.adapter.ProspectRechercheAdapter;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Fragment permettant de créer un prospect et de gérer la recherche et la
 * sélection de prospects existants.
 */
public class CreationProspectDialogFragment extends DialogFragment implements ProspectRechercheAdapter.OnItemClickListener {

    private IProspectService prospectService;
    private UtilisateurViewModel utilisateurViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private TextView erreur, erreurRechercheprospect;
    private EditText nomPrenomProspect, mailProspect, telProspect,
            adresseProspect, villeProspect, codePostalProspect;
    private Button boutonEnvoyer, boutonAnnuler;
    private ProgressBar chargement;
    private AppCompatButton boutonPlus, boutonMoins;
    private EditText texteRecherche1, texteRecherche2;
    private ImageButton boutonRecherche1, boutonRecherche2;
    private LinearLayout secondeBarreDeRecherche, triContainer;
    private Spinner listeTri;

    private RecyclerView prospectRecyclerView;
    private ProspectRechercheAdapter adapter;

    private String nomSalon, tri;
    private final String REGEX_MAIl = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\" +
            ".[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^(0[1-9])(\\s?\\d{2}){4}$";

    private ArrayList<Prospect> listProspectRecherche = new ArrayList<>();
    private ArrayList<Prospect> premiereListeProspect = new ArrayList<>();
    private ArrayList<Prospect> deuxiemeListeProspect = new ArrayList<>();
    private ProspectAdapter adapterProspect;

    /**
     * Crée le DialogFragment et définit son titre.
     *
     * @param savedInstanceState L'état de l'instance précédente.
     * @return Un dialog configuré.
     */
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un prospect");
        return dialog;
    }

    /**
     * Crée la vue du fragment.
     *
     * @param inflater           Le LayoutInflater pour gonfler la vue.
     * @param container          Le ViewGroup parent.
     * @param savedInstanceState L'état de l'instance précédente.
     * @return La vue gonflée.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.dialog_create_prospect,
                container, false);
        initialiserVue(vue);
        initialiserRecyclerView();
        initialiserServices();

        configurerRecherche();
        configurerTri();
        configurerRecherche();
        configurerBoutons();

        return vue;
    }

    /**
     * Initialise les vues du fragment.
     *
     * @param vue La vue du fragment.
     */
    private void initialiserVue(View vue) {
        erreur = vue.findViewById(R.id.erreur_prospect);
        erreurRechercheprospect =
                vue.findViewById(R.id.erreur_recherche_prospect);
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
        secondeBarreDeRecherche = vue.findViewById(R.id.deuxiemeBarreRecherche);
        listeTri = vue.findViewById(R.id.spinner_sort);
        triContainer = vue.findViewById(R.id.triContainer);
    }

    /**
     * Initialise et configure le RecyclerView pour afficher les prospects.
     */
    private void initialiserRecyclerView() {
        adapter = new ProspectRechercheAdapter(listProspectRecherche,
                CreationProspectDialogFragment.this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                1);
        prospectRecyclerView.setLayoutManager(layoutManager);
        prospectRecyclerView.setAdapter(adapter);
    }

    /**
     * Initialise les services nécessaires pour la gestion des prospects.
     */
    private void initialiserServices() {
        prospectService = new ProspectService();
        mesProspectViewModel =
                new ViewModelProvider(requireActivity()).get(MesProspectViewModel.class);
        utilisateurViewModel =
                new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);

        if (getArguments().containsKey("nomDuSalon")) {
            nomSalon = getArguments().getString("nomDuSalon");
            adapterProspect =
                    (ProspectAdapter) getArguments().getSerializable(
                            "adapterProspect");
        }
    }

    /**
     * Configure les boutons de recherche.
     */
    private void configurerRecherche() {
        boutonRecherche1.setOnClickListener(v -> {
            premiereListeProspect.clear();
            effectuerRecherche(texteRecherche1.getText().toString().trim(),
                    premiereListeProspect);
        });

        boutonRecherche2.setOnClickListener(v -> {
            deuxiemeListeProspect.clear();
            effectuerRecherche(texteRecherche2.getText().toString().trim(),
                    deuxiemeListeProspect);
        });

        texteRecherche1.setOnClickListener(v -> {
            premiereListeProspect.clear();
            effectuerRecherche(texteRecherche1.getText().toString().trim(),
                    premiereListeProspect);
        });

        texteRecherche2.setOnClickListener(v -> {
            deuxiemeListeProspect.clear();
            effectuerRecherche(texteRecherche2.getText().toString().trim(),
                    deuxiemeListeProspect);
        });
    }

    private void effectuerRecherche(String valeurRecherche,
                                    ArrayList<Prospect> listeProspect) {
        prospectRecyclerView.setVisibility(View.VISIBLE);
        chargement.setVisibility(View.VISIBLE);
        listProspectRecherche.clear();
        prospectService.prospectClientExiste(getContext(), valeurRecherche, "rowid",
                utilisateurViewModel.getUtilisateur(),
                new Outils.APIResponseCallbackArrayProspect() {
                    @Override
                    public void onSuccess(ArrayList<Prospect> response) {
                        triContainer.setVisibility(View.VISIBLE);
                        erreurRechercheprospect.setVisibility(View.GONE);
                        listeProspect.addAll(response);
                        if (deuxiemeListeProspect.isEmpty() || premiereListeProspect.isEmpty()) {
                            listProspectRecherche.addAll(listeProspect);
                        } else {
                            ArrayList<Prospect> prospectARetourner =
                                    chercheProspectEnCommun(premiereListeProspect,
                                            deuxiemeListeProspect);
                            listProspectRecherche.addAll(prospectARetourner);

                        }
                        chargement.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        chargement.setVisibility(View.GONE);
                        erreurRechercheprospect.setText(R.string.aucun_prospect_trouvee);
                        erreurRechercheprospect.setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * Recherche le prospect commun entre les deux listes de prospects.
     *
     * @param premiereListeProspect La première liste de prospects à comparer.
     * @param deuxiemeListeProspect La deuxième liste de prospects à comparer.
     * @return Le prospect en commun trouvé dans les deux listes, ou null si
     * aucun prospect
     * en commun n'est trouvé.
     */
    private ArrayList<Prospect> chercheProspectEnCommun(ArrayList<Prospect> premiereListeProspect, ArrayList<Prospect> deuxiemeListeProspect) {
        ArrayList<Prospect> result = new ArrayList<>();
        for (Prospect prospectPremiereListe : premiereListeProspect) {
            if (deuxiemeListeProspect.contains(prospectPremiereListe)) {
                Log.d("trouve", "prospect en commun trouve");
                result.add(prospectPremiereListe);
            }
        }
        return result;
    }

    /**
     * Configure le tri des prospects.
     */
    private void configurerTri() {
        listeTri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position,
                                       long id) {
                tri = GetChoixTri(parentView.getItemAtPosition(position).toString());
                effectuerTri();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                tri = "nom";
            }
        });
    }

    /**
     * Configure les actions des boutons.
     */
    private void configurerBoutons() {
        boutonPlus.setOnClickListener(v -> afficherSecondeBarreRecherche());
        boutonMoins.setOnClickListener(v -> masquerSecondeBarreRecherche());
        boutonEnvoyer.setOnClickListener(v -> validerProspect());
        boutonAnnuler.setOnClickListener(v -> dismiss());
    }

    /**
     * Affiche la seconde barre de recherche.
     */
    private void afficherSecondeBarreRecherche() {
        boutonPlus.setVisibility(View.GONE);
        boutonMoins.setVisibility(View.VISIBLE);
        secondeBarreDeRecherche.setVisibility(View.VISIBLE);
    }

    /**
     * Masque la seconde barre de recherche.
     */
    private void masquerSecondeBarreRecherche() {
        boutonMoins.setVisibility(View.GONE);
        boutonPlus.setVisibility(View.VISIBLE);
        secondeBarreDeRecherche.setVisibility(View.GONE);
    }

    /**
     * Valide et crée un prospect si les informations sont correctes.
     */
    private void validerProspect() {
        String nom = nomPrenomProspect.getText().toString().trim();
        String mail = mailProspect.getText().toString().trim();
        String tel = telProspect.getText().toString().trim();
        String adresse = adresseProspect.getText().toString().trim();
        String ville = villeProspect.getText().toString().trim();
        String codePostal = codePostalProspect.getText().toString().trim();
        String estClient = "Prospect";

        erreur.setVisibility(View.GONE);

        if (validerInformations(nom, mail, tel, adresse, ville) && validerCodePostal(codePostal)) {
            Prospect prospect = new Prospect(nomSalon, nom,
                    codePostal, ville, adresse, mail, tel,
                    estClient, "image");
            mesProspectViewModel.addProspect(prospect);
            dismiss();

            Bundle bundle = new Bundle();
            bundle.putSerializable("prospect", (Serializable) prospect);
            ProjetFragment projetFragment = new ProjetFragment();
            projetFragment.setArguments(bundle);
            ((MainActivity) getActivity()).loadFragment(projetFragment);
            ((MainActivity) getActivity()).setColors(2, R.color.color_primary
                    , true);
        }


    }

    /**
     * Valide les informations saisies par l'utilisateur.
     *
     * @param nom     Le nom du prospect.
     * @param mail    L'email du prospect.
     * @param tel     Le téléphone du prospect.
     * @param adresse L'adresse du prospect.
     * @param ville   La ville du prospect.
     * @return true si toutes les informations sont valides.
     */
    private boolean validerInformations(String nom, String mail, String tel,
                                        String adresse, String ville) {
        boolean valide = true;
        if (nom.isEmpty() || mail.isEmpty() || tel.isEmpty() || adresse.isEmpty() || ville.isEmpty()) {
            erreur.setVisibility(View.VISIBLE);
            erreur.setText(R.string.erreur_champ_vide);
            valide = false;
        } else if (!mail.matches(REGEX_MAIl)) {
            erreur.setVisibility(View.VISIBLE);
            erreur.setText(R.string.erreur_mail_prospect);
            valide = false;
        } else if (!tel.matches(REGEX_TEL)) {
            erreur.setVisibility(View.VISIBLE);
            erreur.setText(R.string.erreur_tel_prospect);
            valide = false;
        }

        return valide;
    }

    /**
     * Valide le code postal saisi.
     *
     * @return true si le code postal est correct et false sinon.
     */
    private boolean validerCodePostal(String codePostal) {
        if (codePostal.isEmpty() || codePostal.length() != 5) {
            erreur.setVisibility(View.VISIBLE);
            erreur.setText(R.string.erreur_codePostal_prospect);
            return false;
        }
        return true;
    }

    /**
     * Effectue le tri des prospects en fonction du critère sélectionné.
     */
    private void effectuerTri() {
        switch (tri) {
            case "nom":
                Collections.sort(listProspectRecherche, Prospect.compareNom);
                break;
            case "mail":
                Collections.sort(listProspectRecherche, Prospect.compareMail);
                break;
            case "numeroTelephone":
                Collections.sort(listProspectRecherche,
                        Prospect.compareTelephone);
                break;
            case "adresse":
                Collections.sort(listProspectRecherche,
                        Prospect.compareAdresse);
                break;
            case "codePostal":
                Collections.sort(listProspectRecherche,
                        Prospect.compareCodePostal);
                break;
            case "ville":
                Collections.sort(listProspectRecherche, Prospect.compareVille);
                break;
        }
    }


    /**
     * Retourne la valeur de tri correspondant à l'élément sélectionné.
     *
     * @param itemSelectionne L'élément sélectionné par l'utilisateur pour le
     *                        tri.
     * @return La valeur associée au critère de tri sélectionné. Par défaut,
     * renvoie le nom si aucun cas ne correspond.
     */
    private String GetChoixTri(String itemSelectionne) {
        String valeurTri = "";
        switch (itemSelectionne) {
            case "Aucun tri":
                valeurTri = "nom";
                break;
            case "Nom/Prénom":
                valeurTri = "nom";
                break;
            case "Email":
                valeurTri = "mail";
                break;
            case "Téléphone":
                valeurTri = "numeroTelephone";
                break;
            case "Adresse":
                valeurTri = "adresse";
                break;
            case "Code Postal":
                valeurTri = "codePostal";
                break;
            case "Ville":
                valeurTri = "ville";
                break;
        }
        return valeurTri;
    }

    /**
     * Méthode appelée lorsqu'un prospect est sélectionné dans la liste.
     * Cette méthode cache le RecyclerView, puis remplit les champs de texte
     * avec les informations du prospect sélectionné,
     * et bloque la saisie dans les champs pour empêcher toute modification
     * après la sélection.
     *
     * @param prospect Le prospect sélectionné à afficher dans les champs.
     */
    @Override
    public void onSelectClick(Prospect prospect) {
        prospect.setNomSalon(nomSalon);
        prospectRecyclerView.setVisibility(View.GONE);
        remplirChamp(nomPrenomProspect, prospect.getNom());
        remplirChamp(mailProspect, prospect.getMail());
        remplirChamp(telProspect, prospect.getNumeroTelephone());
        remplirChamp(adresseProspect, prospect.getAdresse());
        remplirChamp(codePostalProspect,
                String.valueOf(prospect.getCodePostal()));
        remplirChamp(villeProspect, prospect.getVille());
        bloquerSaisieEditText();
    }

    /**
     * Désactive la saisie dans tous les champs EditText de l'interface
     * utilisateur.
     * Cette méthode est appelée après la sélection d'un prospect pour
     * empêcher toute modification des informations affichées.
     */
    private void bloquerSaisieEditText() {
        nomPrenomProspect.setEnabled(false);
        mailProspect.setEnabled(false);
        telProspect.setEnabled(false);
        adresseProspect.setEnabled(false);
        codePostalProspect.setEnabled(false);
        villeProspect.setEnabled(false);
    }

    /**
     * Remplie un champ EditText avec une valeur donnée. Si la valeur est
     * "null", le champ est vidé.
     *
     * @param champ  Le champ EditText à remplir.
     * @param valeur La valeur à afficher dans le champ. Si la valeur est
     *               "null", le champ sera vidé.
     */
    private void remplirChamp(EditText champ, String valeur) {
        if (!valeur.equals("null")) {
            champ.setText(valeur);
        } else {
            champ.setText("");
        }
    }
}

