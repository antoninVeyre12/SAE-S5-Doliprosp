package com.example.doliprosp.fragment;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

public class UtilisateurFragment extends Fragment {
    private Utilisateur utilisateurActuel;
    private JSONObject objectJSON;

    private String nom;
    private String prenom;
    private String userName;
    private String mail;
    private String adresse;
    private String codePostal;
    private String numTelephone;
    private String ville;

    TextView textViewNom;
    TextView textViewPrenom;
    TextView textViewUserName;
    TextView textViewMail;
    TextView textViewAdresse;
    TextView textViewCodePostale;
    TextView textViewVille;
    TextView textViewNumTelephone;

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

        userName = utilisateurActuel.getUserName();
        Activity activity = getActivity();

        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);
        textViewNom = view.findViewById(R.id.id_nom);
        textViewPrenom = view.findViewById(R.id.id_prenom);
        textViewUserName = view.findViewById(R.id.id_userName);
        textViewMail = view.findViewById(R.id.id_mail);
        textViewAdresse = view.findViewById(R.id.id_adresse);
        textViewCodePostale = view.findViewById(R.id.id_codePostal);
        textViewVille = view.findViewById(R.id.id_ville);
        textViewNumTelephone = view.findViewById(R.id.id_numTelephone);


        if(utilisateurActuel.informationutilisateurDejaRecupere())
        {
            nom = utilisateurActuel.getNom();
            prenom = utilisateurActuel.getPrenom();
            mail = utilisateurActuel.getMail();
            adresse = utilisateurActuel.getAdresse();
            codePostal = String.valueOf(utilisateurActuel.getCodePostal());
            ville = utilisateurActuel.getVille();
            numTelephone = utilisateurActuel.getNumTelephone();
            afficherInformations();
        } else {
            String urlUtilisateur = utilisateurViewModel.getUtilisateur(getContext(), requireActivity()).getUrl();

            try {
                String userNameEncoder = URLEncoder.encode(userName, "UTF-8");
                urlUtilisateur = String.format("%s/api/index.php/users/login/%s", urlUtilisateur, userNameEncoder);
            } catch (UnsupportedEncodingException e) {
                Log.d("erreur url getCommercial", e.getMessage());
            }
            
            Outils.appelAPIGet(urlUtilisateur, utilisateurViewModel.getUtilisateur(getContext(), requireActivity()).getApiKey(), getContext(), new Outils.APIResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    // Cela s'exécutera lorsque l'API renvoie une réponse valide
                    Log.d("feoejf,oeakf", response.toString());
                    objectJSON = response;
                    Log.d("objectJSON", String.valueOf(objectJSON.length()));
                    try {
                        nom = objectJSON.getString("lastname");
                        utilisateurActuel.setNom(nom);
                        prenom = objectJSON.getString("firstname");
                        utilisateurActuel.setPrenom(prenom);
                        mail = objectJSON.getString("email");
                        utilisateurActuel.setMail(mail);
                        adresse = objectJSON.getString("address");
                        utilisateurActuel.setAdresse(adresse);
                        codePostal = objectJSON.getString("zip");
                        utilisateurActuel.setCodePostal(Integer.parseInt(codePostal));
                        ville = objectJSON.getString("town");
                        utilisateurActuel.setVille(ville);
                        numTelephone = objectJSON.getString("office_phone");
                        utilisateurActuel.setNumTelephone(numTelephone);
                        afficherInformations();
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
        }

        Button btnDeconnexion = view.findViewById(R.id.btnDeconnexion);
        btnDeconnexion.setOnClickListener(v -> {
            utilisateurViewModel.setUtilisateur(null, getContext());
            Log.d("SET UTILISATEUR", "utilisateur set a null");
            //System.gc(); //Supprime l'intsance de Utilisateur
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("users_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", null);
            editor.putString("url", null);
            editor.putString("motDePasse", null);
            editor.putString("apiKey", null);

            editor.apply();
            ConnexionFragment connexionFragment = new ConnexionFragment();
            ((MainActivity) getActivity()).loadFragment(connexionFragment);
            bottomNav.setVisibility(View.GONE);
        });
    }

    private void afficherInformations()
    {
        textViewNom.setText(nom);
        textViewPrenom.setText(prenom);
        textViewUserName.setText(userName);
        textViewMail.setText(mail);
        textViewAdresse.setText(adresse);
        textViewCodePostale.setText(codePostal);
        textViewVille.setText(ville);
        textViewNumTelephone.setText(numTelephone);
    }
}