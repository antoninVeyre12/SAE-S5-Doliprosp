package com.example.doliprosp.fragment;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Model.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UserFragment extends Fragment {
    private String mail;
    private Utilisateur utilisateurActuel;
    private JSONObject objectJSON;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateurActuel = utilisateurViewModel.getUtilisateur(getContext(), requireActivity());

        String userName = utilisateurViewModel.getUtilisateur(getContext(), requireActivity()).getUserName();
        Activity activity = getActivity();

        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);
        TextView textViewNom = view.findViewById(R.id.id_nom);
        TextView textViewPrenom = view.findViewById(R.id.id_prenom);
        TextView textViewUserName = view.findViewById(R.id.id_userName);
        TextView textViewMail = view.findViewById(R.id.id_mail);
        TextView textViewAdresse = view.findViewById(R.id.id_adresse);
        TextView textViewCodePostale = view.findViewById(R.id.id_codePostal);
        TextView textViewVille = view.findViewById(R.id.id_ville);
        TextView textViewNumTelephone = view.findViewById(R.id.id_numTelephone);

        String urlUtilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity()).getUrl();

        try {
            String userNameEncoder = URLEncoder.encode(userName, "UTF-8");
            urlUtilisateur = String.format("%s/api/index.php/users/login/%s", urlUtilisateur, userNameEncoder);
        } catch (UnsupportedEncodingException e) {
            Log.d("erreur url getCommercial", e.getMessage());
        }
        Log.d("urlllll", urlUtilisateur);
        Outils.appelAPIGet(urlUtilisateur, utilisateurViewModel.getUtilisateur(getContext(), requireActivity()).getApiKey(), getContext(), new Outils.APIResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                // Cela s'exécutera lorsque l'API renvoie une réponse valide
                Log.d("feoejf,oeakf", response.toString());
                objectJSON = response;
                Log.d("objectJSON", String.valueOf(objectJSON.length()));
                try {
                    String nom = objectJSON.getString("lastname");
                    textViewNom.setText(nom);
                    String prenom = objectJSON.getString("firstname");
                    textViewPrenom.setText(prenom);
                    String userName = objectJSON.getString("login");
                    textViewUserName.setText(userName);
                    String mail = objectJSON.getString("email");
                    textViewMail.setText(mail);
                    String adresse = objectJSON.getString("address");
                    textViewAdresse.setText(adresse);
                    String codePostale = objectJSON.getString("zip");
                    textViewCodePostale.setText(codePostale);
                    String ville = objectJSON.getString("town");
                    textViewVille.setText(ville);
                    String numTelephone = objectJSON.getString("office_phone");
                    textViewNumTelephone.setText(numTelephone);
                } catch(Exception e) {
                    Log.d("ERROR JSON EXCEPTION", e.getMessage());
                }

            }

            @Override
            public void onError(String errorMessage) {
                // Cela s'exécutera en cas d'erreur dans l'appel API
                Log.d("BAD APPEL API", errorMessage);
            }
        });

        Button btnDeconnexion = view.findViewById(R.id.btnDeconnexion);
        btnDeconnexion.setOnClickListener(v -> {
            utilisateurViewModel.setUtilisateur(null, getContext());
            System.gc(); //Supprime l'intsance de Utilisateur
            ConnexionFragment connexionFragment = new ConnexionFragment();
            ((MainActivity) getActivity()).loadFragment(connexionFragment);
            bottomNav.setVisibility(View.GONE);
        });
    }
}