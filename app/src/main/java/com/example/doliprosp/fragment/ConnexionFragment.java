package com.example.doliprosp.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.Interface.ConnexionCallBack;
import com.example.doliprosp.Interface.IConnexionService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ConnexionService;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ConnexionFragment extends Fragment {
    private EditText urlEditText;
    private EditText nomUtilisateurEditText;
    private EditText motDePasseEditText;
    private IConnexionService connexionService;
    private ProgressBar chargement;
    private LinearLayout bottomNav;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connexion, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        connexionService = new ConnexionService();
        UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateurViewModel.initSharedPreferences(getContext());
        Utilisateur utilisateur = utilisateurViewModel.chargementUtilisateur();

        //String urlConnexion;
        urlEditText = view.findViewById(R.id.url);
        nomUtilisateurEditText = view.findViewById(R.id.username);
        motDePasseEditText = view.findViewById(R.id.password);
        chargement = view.findViewById(R.id.chargement);
        Button buttonSubmit  = view.findViewById(R.id.connexion);

        // Recupere la bottom nav bar de l'activité
        Activity activity = getActivity();
        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);

        /*String password = motDePasseEditText.getText().toString();
        String nomUtilisateur = nomUtilisateurEditText.getText().toString();
        String url = urlEditText.getText().toString();*/

        String url = "http://www.doliprosptest.go.yj.fr/dolibarr-17.0.3/htdocs";
        String nomUtilisateur = "antonin";
        String password = "antoninantonin";
        //Lors d'une seconde connexion
        if(utilisateur != null && utilisateur.informationutilisateurDejaRecupere()) {
            utilisateurViewModel.chargementUtilisateur();
            urlEditText.setText(utilisateur.getUrl());
            nomUtilisateurEditText.setText(utilisateur.getNomUtilisateur());
            buttonSubmit.setOnClickListener(v -> {
                if(password.trim().equalsIgnoreCase(utilisateur.getMotDePasse()) && nomUtilisateur.equalsIgnoreCase(utilisateur.getNomUtilisateur()) && url.equalsIgnoreCase(utilisateur.getUrl())) {
                    SalonFragment salonFragment = new SalonFragment();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.loadFragment(salonFragment);
                        mainActivity.setColors(1, R.color.color_primary,true);
                    }
                    bottomNav.setVisibility(View.VISIBLE);
                    chargement.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(),R.string.mot_depasse_incorrect, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            buttonSubmit.setOnClickListener(v -> {
                    String finalUrl = url;
                    chargement.setVisibility(View.VISIBLE);
                    connexionService.connexion(url, nomUtilisateur, password, getContext(), new ConnexionCallBack() {
                        public void onSuccess(Utilisateur utilisateur) {
                            String apiKeyChiffre = connexionService.chiffrementApiKey(utilisateur.getCleApi());
                            String urlUtilisateur = utilisateur.getUrl();
                            utilisateur.setApiKey(apiKeyChiffre);
                            UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
                            utilisateurViewModel.initSharedPreferences(getContext());
                            if(!utilisateur.informationutilisateurDejaRecupere()) {
                                try {
                                    String userNameEncoder = URLEncoder.encode(nomUtilisateur, "UTF-8");
                                    urlUtilisateur = String.format("%s/api/index.php/users/login/%s", urlUtilisateur, userNameEncoder);
                                } catch (UnsupportedEncodingException e) {
                                    Log.d("erreur url getCommercial", e.getMessage());
                                }
                                Outils.appelAPIGet(urlUtilisateur, apiKeyChiffre, getContext(), new Outils.APIResponseCallback() {
                                    @Override
                                    public void onSuccess(JSONObject response) {
                                        JSONObject objectJSON = response;
                                        try {
                                            String nom = objectJSON.getString("lastname");
                                            utilisateur.setNom(nom);
                                            String prenom = objectJSON.getString("firstname");
                                            utilisateur.setPrenom(prenom);
                                            Log.d("prenomm", utilisateur.getPrenom());
                                            String mail = objectJSON.getString("email");
                                            utilisateur.setMail(mail);
                                            String adresse = objectJSON.getString("address");
                                            utilisateur.setAdresse(adresse);
                                            String codePostal = objectJSON.getString("zip");
                                            utilisateur.setCodePostal(Integer.parseInt(codePostal));
                                            String ville = objectJSON.getString("town");
                                            utilisateur.setVille(ville);
                                            String numTelephone = objectJSON.getString("office_phone");
                                            utilisateur.setNumTelephone(numTelephone);
                                            utilisateurViewModel.setUtilisateur(utilisateur);

                                            // Navigation vers ShowFragment
                                            SalonFragment salonFragment = new SalonFragment();
                                            ((MainActivity) getActivity()).loadFragment(salonFragment);
                                            ((MainActivity) getActivity()).setColors(1, R.color.color_primary,true);
                                            bottomNav.setVisibility(View.VISIBLE);
                                            chargement.setVisibility(View.GONE);
                                        } catch (Exception e) {
                                            Log.d("ERROR JSON EXCEPTION", e.getMessage());
                                        }
                                    }
                                    @Override
                                    public void onError(String errorMessage) {
                                        // Cela s'exécutera en cas d'erreur dans l'appel API
                                        Log.d("BAD APPEL API", errorMessage);
                                    }
                                });
                            } else {
                                // Navigation vers ShowFragment
                                SalonFragment salonFragment = new SalonFragment();
                                MainActivity mainActivity = (MainActivity) getActivity();
                                if (mainActivity != null) {
                                    mainActivity.loadFragment(salonFragment);
                                    mainActivity.setColors(1, R.color.color_primary,true);
                                }
                                bottomNav.setVisibility(View.VISIBLE);
                                chargement.setVisibility(View.GONE);
                            }
                        }

                        public void onError(String errorMessage) {
                            if (finalUrl.endsWith("/")) {
                                Toast.makeText(getContext(),R.string.url_invalide_2, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(),R.string.informations_saisies_incorrecte, Toast.LENGTH_LONG).show();
                            }
                            chargement.setVisibility(View.GONE);

                        }
                    });
            });
        }
    }

    private void naviguerVersSalon() {
        SalonFragment salonFragment = new SalonFragment();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.loadFragment(salonFragment);
            mainActivity.setColors(1, R.color.color_primary,true);
        }
        bottomNav.setVisibility(View.VISIBLE);
        chargement.setVisibility(View.GONE);
    }

}