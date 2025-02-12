package com.example.doliprosp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.doliprosp.Interface.ConnexionCallBack;
import com.example.doliprosp.Interface.IConnexionService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ConnexionService;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Fragment permettant à un utilisateur de se connecter à l'application.
 * Il permet d'entrer les informations nécessaires pour la connexion, de vérifier ces informations
 * et de naviguer vers la salle de discussion une fois connecté.
 */
public class ConnexionFragment extends Fragment {
    private EditText urlEditText, nomUtilisateurEditText, motDePasseEditText;
    private Button buttonSubmit;
    private IConnexionService connexionService;
    private Utilisateur utilisateur;
    private ProgressBar chargement;
    private LinearLayout bottomNav;
    private UtilisateurViewModel utilisateurViewModel;
    private String url, nomUtilisateur, motDePasse;


    /**
     * Crée la vue du fragment de connexion.
     *
     * @param inflater           Le LayoutInflater pour inflater la vue
     * @param container          Le conteneur parent
     * @param savedInstanceState L'état sauvegardé de la vue
     * @return La vue du fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connexion, container, false);
    }

    /**
     * Initialise les éléments de la vue.
     *
     * @param vue La vue associée au fragment
     */

    private void setUp(View vue) {
        urlEditText = vue.findViewById(R.id.url);
        nomUtilisateurEditText = vue.findViewById(R.id.username);
        motDePasseEditText = vue.findViewById(R.id.password);
        chargement = vue.findViewById(R.id.chargement);
        buttonSubmit = vue.findViewById(R.id.connexion);
        Activity activity = getActivity();
        bottomNav = activity.findViewById(R.id.bottom_navigation);
        connexionService = new ConnexionService();
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateur = utilisateurViewModel.chargementUtilisateur(getContext());
    }

    /**
     * Méthode appelée après la création de la vue pour définir la logique de connexion.
     *
     * @param vue                La vue du fragment
     * @param savedInstanceState L'état sauvegardé
     */
    @Override
    public void onViewCreated(@NonNull View vue, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp(vue);

        if (utilisateur != null) {
            Log.d("vfbjsvbjsvdj", "info user deja recup");
            recupereSaisieChamps();
            configurerUtilisateurExistant();
        } else {
            configurerNouvelUtilisateur();
        }
    }

    /**
     * Configure la connexion pour un nouvel utilisateur.
     */

    private void configurerNouvelUtilisateur() {
        buttonSubmit.setOnClickListener(v -> {
            recupereSaisieChamps();
            chargement.setVisibility(View.VISIBLE);
            connexionService.connexion(url, nomUtilisateur, motDePasse, getContext(), new ConnexionCallBack() {
                public void onSuccess(Utilisateur utilisateur) {
                    gererConnexionUtilisateur(utilisateur, nomUtilisateur);
                }

                public void onError(String errorMessage) {
                    gererErreurConnexion(url);
                }
            });
        });
    }

    private void configurerUtilisateurExistant() {
        utilisateurViewModel.chargementUtilisateur(getContext());
        urlEditText.setText(utilisateur.getUrl());
        nomUtilisateurEditText.setText(utilisateur.getNomUtilisateur());

        buttonSubmit.setOnClickListener(v -> {
            recupereSaisieChamps();
            if (utilisateurEstValide(url, nomUtilisateur, motDePasse)) {
                naviguerVersSalon();
            } else {
                Toast.makeText(getContext(), R.string.mot_depasse_incorrect, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Gère le succès de la connexion de l'utilisateur.
     *
     * @param nouvelUtilisateur L'utilisateur connecté
     * @param nomUtilisateur    Le nom d'utilisateur
     */

    private void gererConnexionUtilisateur(Utilisateur nouvelUtilisateur, String nomUtilisateur) {
        utilisateur = nouvelUtilisateur;
        String apiKeyChiffre = connexionService.chiffrementApiKey(utilisateur.getCleApi());
        String urlUtilisateur = utilisateur.getUrl();
        utilisateur.setApiKey(apiKeyChiffre);

        if (!utilisateur.informationutilisateurDejaRecupere()) {
            recupereInfoCompteAvecAPI(urlUtilisateur, nomUtilisateur, apiKeyChiffre);
        } else {
            naviguerVersSalon();
        }
    }

    /**
     * Récupère les informations du compte de l'utilisateur via une API.
     *
     * @param urlUtilisateur L'URL de l'utilisateur
     * @param nomUtilisateur Le nom d'utilisateur
     * @param cleApiChiffree La clé API de l'utilisateur
     */

    private void recupereInfoCompteAvecAPI(String urlUtilisateur, String nomUtilisateur, String cleApiChiffree) {
        try {
            String userNameEncoder = URLEncoder.encode(nomUtilisateur, "UTF-8");
            urlUtilisateur = String.format("%s/api/index.php/users/login/%s", urlUtilisateur, userNameEncoder);
        } catch (UnsupportedEncodingException e) {
            Log.d("erreur url getCommercial", e.getMessage());
        }

        Outils.appelAPIGet(urlUtilisateur, cleApiChiffree, getContext(), new Outils.APIResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    setUtilisateur(response);
                    naviguerVersSalon();

                } catch (Exception e) {
                    Log.d("ERROR JSON EXCEPTION", e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("BAD APPEL API", errorMessage);
            }
        });
    }

    /**
     * Gère les erreurs lors de la connexion.
     *
     * @param url L'URL utilisée pour la connexion
     */

    private void gererErreurConnexion(String url) {
        if (url.endsWith("/")) {
            Toast.makeText(getContext(), R.string.url_invalide_2, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), R.string.informations_saisies_incorrecte, Toast.LENGTH_LONG).show();
        }
        chargement.setVisibility(View.GONE);
    }


    /**
     * Définit les informations de l'utilisateur à partir d'une réponse JSON.
     *
     * @param reponse L'objet JSON contenant les informations de l'utilisateur
     * @throws JSONException Si la réponse JSON est invalide
     */
    private void setUtilisateur(JSONObject reponse) throws JSONException {
        utilisateur.setNom(reponse.getString("lastname"));
        utilisateur.setPrenom(reponse.getString("firstname"));
        utilisateur.setMail(reponse.getString("email"));
        utilisateur.setAdresse(reponse.getString("address"));
        utilisateur.setCodePostal(Integer.parseInt(reponse.getString("zip")));
        utilisateur.setVille(reponse.getString("town"));
        utilisateur.setNumTelephone(reponse.getString("office_phone"));

        utilisateurViewModel.setUtilisateur(utilisateur, getContext());
    }

    /**
     * Vérifie que les informations de connexion sont valides.
     *
     * @param url            L'URL de connexion
     * @param nomUtilisateur Le nom d'utilisateur
     * @param motDePasse     Le mot de passe
     * @return true si les informations sont valides, false sinon
     */
    private boolean utilisateurEstValide(String url, String nomUtilisateur, String motDePasse) {
        return motDePasse.trim().equalsIgnoreCase(utilisateur.getMotDePasse())
                && nomUtilisateur.equalsIgnoreCase(utilisateur.getNomUtilisateur())
                && url.equalsIgnoreCase(utilisateur.getUrl());
    }

    /**
     * Récupère les données saisies dans les champs du formulaire.
     */
    private void recupereSaisieChamps() {

        motDePasse = motDePasseEditText.getText().toString();
        nomUtilisateur = nomUtilisateurEditText.getText().toString();
        url = urlEditText.getText().toString();
        url = "https://www.doliprosptest.go.yj.fr/dolibarr-17.0.3/htdocs";
        nomUtilisateur = "antonin";
        motDePasse = "antoninantonin";
        Log.d("url", url);
    }

    /**
     * Navigue vers le fragment des salons.
     */
    private void naviguerVersSalon() {
        SalonFragment salonFragment = new SalonFragment();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.loadFragment(salonFragment);
            mainActivity.setColors(1, R.color.color_primary, true);
        }
        bottomNav.setVisibility(View.VISIBLE);
        chargement.setVisibility(View.GONE);
    }

}