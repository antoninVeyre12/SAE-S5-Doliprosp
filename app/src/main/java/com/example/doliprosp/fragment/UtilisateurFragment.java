package com.example.doliprosp.fragment;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.R;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONObject;

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
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    /**
     * Méthode pour gérer l'affichage des éléments de la vue et le clic sur le bouton deconnecter
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateurActuel = utilisateurViewModel.getUtilisateur();

        userName = utilisateurActuel.getNomUtilisateur();
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


        if (utilisateurActuel.informationutilisateurDejaRecupere()) {
            nom = utilisateurActuel.getNom();
            prenom = utilisateurActuel.getPrenom();
            mail = utilisateurActuel.getMail();
            adresse = utilisateurActuel.getAdresse();
            codePostal = String.valueOf(utilisateurActuel.getCodePostal());
            ville = utilisateurActuel.getVille();
            numTelephone = utilisateurActuel.getNumTelephone();
            afficherInformations();
        }

        Button btnDeconnexion = view.findViewById(R.id.btnDeconnexion);
        btnDeconnexion.setOnClickListener(v -> {
            utilisateurViewModel.supprimerDonnerUtilisateur(getContext());
            ConnexionFragment connexionFragment = new ConnexionFragment();
            ((MainActivity) getActivity()).loadFragment(connexionFragment);
            bottomNav.setVisibility(View.GONE);
        });
    }

    /**
     * Méthode permettant d'afficher les informations de l'utilisateur
     */
    private void afficherInformations() {
        textViewNom.setText(nom);
        textViewPrenom.setText(prenom);
        textViewUserName.setText(userName);
        textViewMail.setText(mail);
        textViewAdresse.setText(adresse);
        textViewCodePostale.setText(codePostal);
        textViewVille.setText(ville);
        textViewNumTelephone.setText(numTelephone);
    }

    /**
     * Méthode appellée lors d'une seconde utilisation du fragment
     */
    public void onResume() {
        super.onResume();
        // Met en primaryColor l'icone et le texte du fragment
        ((MainActivity) getActivity()).setColors(4, R.color.color_primary, true);
        if (ProspectFragment.dernierSalonSelectione == null) {
            ((MainActivity) getActivity()).setColors(2, R.color.invalide, false);
        }
        if (ProjetFragment.dernierProspectSelectionne == null) {
            ((MainActivity) getActivity()).setColors(3, R.color.invalide, false);
        }
    }
}