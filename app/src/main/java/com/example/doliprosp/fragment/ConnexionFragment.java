package com.example.doliprosp.fragment;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.doliprosp.viewModel.UtilisateurViewModel;


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

        //String urlConnexion;
        editTextUrl = view.findViewById(R.id.url);
        editTextUserName = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        chargement = view.findViewById(R.id.chargement);

        // Recupere la bottom nav bar de l'activité
        Activity activity = getActivity();
        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);

        Button buttonSubmit  = view.findViewById(R.id.connexion);
        buttonSubmit.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString();
            String userName = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();
            url = "http://dolibarr.iut-rodez.fr/G2023-42/htdocs";
            userName = "G42";
            password = "3iFJWj26z";
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
                        // Traitez l'utilisateur récupéré ici
                        String apiKeyChiffre = connexionService.chiffrementApiKey(utilisateur.getApiKey());
                        utilisateur.setApiKey(apiKeyChiffre);
                        UtilisateurViewModel utilisateurViewModele = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
                        utilisateurViewModele.setUtilisateur(utilisateur, getContext());
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