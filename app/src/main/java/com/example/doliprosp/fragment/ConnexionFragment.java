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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doliprosp.Interface.ConnexionCallBack;
import com.example.doliprosp.Interface.IConnexionService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Service.ConnexionService;

public class ConnexionFragment extends Fragment {
    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private IConnexionService connexionService;

    private String urlConnexion;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.connexion, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        connexionService = new ConnexionService();

        //String urlConnexion;
        editTextUrl = view.findViewById(R.id.url);
        editTextUserName = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);

        // Recupere la bottom nav bar de l'activité
        Activity activity = getActivity();
        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);

        Button buttonSubmit  = view.findViewById(R.id.connexion);
        buttonSubmit.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString();
            String userName = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            if (url.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Log.d("TEST VIDE", "OK");
            }
            try {
                connexionService.connexion(url, userName, password, getContext(), new ConnexionCallBack() {
                    public void onSuccess(Utilisateur utilisateur) {
                        // Traitez l'utilisateur récupéré ici
                        String apiKeyChiffre = connexionService.chiffrementApiKey(utilisateur.getApiKey());
                        utilisateur.setApiKey(apiKeyChiffre);
                        UserFragment.nouvelUtilisateur(utilisateur);

                        // Navigation vers ShowFragment
                        ShowFragment showFragment = new ShowFragment();
                        ((MainActivity) getActivity()).loadFragment(showFragment);
                        ((MainActivity) getActivity()).setColors(1);
                        bottomNav.setVisibility(View.VISIBLE);
                    }


                    public void onError(String errorMessage) {
                        Log.d("Error Connexion", errorMessage);
                        Toast.makeText(getContext(), "Erreur de connexion : " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch(Exception e) {
                Log.d("text", e.getMessage());
            }
        });
    }
}