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
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.ConnexionService;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ConnexionFragment extends Fragment {
    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private IConnexionService connexionService;
    private String urlConnexion;
    private ProgressBar chargement;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_connexion, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        connexionService = new ConnexionService();
        UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateurViewModel.initSharedPreferences(getContext());
        Utilisateur utilisateur = utilisateurViewModel.chargementUtilisateur();
        Log.d("mot de passe", utilisateur.getMotDePasse());

        //String urlConnexion;
        editTextUrl = view.findViewById(R.id.url);
        editTextUserName = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        chargement = view.findViewById(R.id.chargement);

        // Recupere la bottom nav bar de l'activité
        Activity activity = getActivity();
        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);

        if(utilisateur != null && utilisateur.informationutilisateurDejaRecupere()) {
            utilisateurViewModel.chargementUtilisateur();
            editTextUrl.setText(utilisateur.getUrl());
            editTextUserName.setText(utilisateur.getUserName());
            Log.d("mot depasse", String.valueOf(utilisateur.getMotDePasse().length()));
            Button buttonSubmit  = view.findViewById(R.id.connexion);
            buttonSubmit.setOnClickListener(v -> {
                String password = editTextPassword.getText().toString();
                Log.d("password", String.valueOf(password.length()));
                if(password.trim().equalsIgnoreCase(utilisateur.getMotDePasse())) {
                    ShowFragment showFragment = new ShowFragment();
                    ((MainActivity) getActivity()).loadFragment(showFragment);
                    ((MainActivity) getActivity()).setColors(1);
                    bottomNav.setVisibility(View.VISIBLE);
                    chargement.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(),R.string.mot_depasse_incorrect, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Button buttonSubmit  = view.findViewById(R.id.connexion);
            buttonSubmit.setOnClickListener(v -> {
                String url = editTextUrl.getText().toString();
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                if (url.trim().isEmpty() || userName.trim().isEmpty() || password.trim().isEmpty()) {
                    // Affiche un toast au lieu d'un log
                    Toast.makeText(getContext(), R.string.informations_invalide , Toast.LENGTH_LONG).show();
                } else if (!url.startsWith("http")) {
                    Toast.makeText(getContext(),R.string.url_invalide, Toast.LENGTH_LONG).show();
                } else {
                    String finalUrl = url;
                    chargement.setVisibility(View.VISIBLE);
                    connexionService.connexion(url, userName, password, getContext(), new ConnexionCallBack() {
                        public void onSuccess(Utilisateur utilisateur) {
                            String userName = editTextUserName.getText().toString();
                            String apiKeyChiffre = connexionService.chiffrementApiKey(utilisateur.getApiKey());
                            String urlUtilisateur = utilisateur.getUrl();
                            utilisateur.setApiKey(apiKeyChiffre);
                            UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
                            utilisateurViewModel.initSharedPreferences(getContext());
                            if(!utilisateur.informationutilisateurDejaRecupere()) {
                                try {
                                    String userNameEncoder = URLEncoder.encode(userName, "UTF-8");
                                    urlUtilisateur = String.format("%s/api/index.php/users/login/%s", urlUtilisateur, userNameEncoder);
                                } catch (UnsupportedEncodingException e) {
                                    Log.d("erreur url getCommercial", e.getMessage());
                                }

                                Outils.appelAPIGet(urlUtilisateur, utilisateurViewModel.getUtilisateur().getApiKey(), getContext(), new Outils.APIResponseCallback() {
                                    @Override
                                    public void onSuccess(JSONObject response) {
                                        Log.d("APIIII", "passage API compte");
                                        // Cela s'exécutera lorsque l'API renvoie une réponse valide
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
                            }

                            // Navigation vers ShowFragment
                            ShowFragment showFragment = new ShowFragment();
                            ((MainActivity) getActivity()).loadFragment(showFragment);
                            ((MainActivity) getActivity()).setColors(1);
                            bottomNav.setVisibility(View.VISIBLE);
                            chargement.setVisibility(View.GONE);
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
                }
            });
        }
    }
}