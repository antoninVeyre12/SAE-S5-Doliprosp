package com.example.doliprosp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.doliprosp.R;
import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.services.ChiffrementVigenere;
import com.example.doliprosp.viewmodels.UtilisateurViewModel;

public class UtilisateurFragment extends Fragment {
    private Utilisateur utilisateurActuel;

    private String nom;
    private String prenom;
    private String userName;
    private String mail;
    private String adresse;
    private String codePostal;
    private String numTelephone;
    private String ville;

    private TextView textViewNom;
    private TextView textViewPrenom;
    private TextView textViewUserName;
    private TextView textViewMail;
    private TextView textViewAdresse;
    private TextView textViewCodePostale;
    private TextView textViewVille;
    private TextView textViewNumTelephone;
    private LinearLayout bottomNav;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    /**
     * Méthode pour gérer l'affichage des éléments de la vue et le clic sur
     * le bouton deconnecter
     *
     * @param view               The View returned by
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragments is being
     *                           re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        initialiserVue(view, activity);

        UtilisateurViewModel utilisateurViewModel =
                new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);

        String key = utilisateurActuel.getClePremierChiffrement();
        nom =
                ChiffrementVigenere.dechiffrement(utilisateurActuel.getNom().toLowerCase(), key);
        prenom =
                ChiffrementVigenere.dechiffrement(utilisateurActuel.getPrenom(), key);
        prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);
        mail = utilisateurActuel.getMail();
        adresse = utilisateurActuel.getAdresse();
        codePostal = String.valueOf(utilisateurActuel.getCodePostal());
        ville =
                ChiffrementVigenere.dechiffrement(utilisateurActuel.getVille(), key);
        ville = ville.substring(0, 1).toUpperCase() + ville.substring(1);
        numTelephone = utilisateurActuel.getNumTelephone();
        afficherInformations();


        Button btnDeconnexion = view.findViewById(R.id.btnDeconnexion);
        btnDeconnexion.setOnClickListener(v -> {
            // Afficher la boîte de dialogue de confirmation
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View dialogView =
                    inflater.inflate(R.layout.dialog_confirm_deconnexion, null);

            AlertDialog.Builder dialogBuilder =
                    new AlertDialog.Builder(getContext());
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            // Récupération des boutons de la boîte de dialogue
            Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
            Button btnLogout = dialogView.findViewById(R.id.btn_logout);

            // Action du bouton Annuler
            btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());

            // Action du bouton Se Déconnecter
            btnLogout.setOnClickListener(view1 -> {
                utilisateurViewModel.supprimerDonnerUtilisateur(requireActivity());
                ConnexionFragment connexionFragment = new ConnexionFragment();
                ((MainActivity) getActivity()).loadFragment(connexionFragment);
                bottomNav.setVisibility(View.GONE);
                alertDialog.dismiss(); // Fermer la boîte de dialogue après
                // l'action
            });
        });

    }

    private void initialiserVue(View vue, Activity activity) {
        UtilisateurViewModel utilisateurViewModel =
                new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        utilisateurActuel = utilisateurViewModel.getUtilisateur();

        userName = utilisateurActuel.getNomUtilisateur();

        bottomNav = activity.findViewById(R.id.bottom_navigation);
        textViewNom = vue.findViewById(R.id.id_nom);
        textViewPrenom = vue.findViewById(R.id.id_prenom);
        textViewUserName = vue.findViewById(R.id.id_userName);
        textViewMail = vue.findViewById(R.id.id_mail);
        textViewAdresse = vue.findViewById(R.id.id_adresse);
        textViewCodePostale = vue.findViewById(R.id.id_codePostal);
        textViewVille = vue.findViewById(R.id.id_ville);
        textViewNumTelephone = vue.findViewById(R.id.id_numTelephone);
    }

    /**
     * Méthode permettant d'afficher les informations de l'utilisateur
     */
    private void afficherInformations() {
        textViewNom.setText(nom.toUpperCase());
        textViewPrenom.setText(prenom);
        textViewUserName.setText(userName);
        textViewMail.setText(mail);
        textViewAdresse.setText(adresse);
        textViewCodePostale.setText(codePostal);
        textViewVille.setText(ville);
        textViewNumTelephone.setText(numTelephone);
    }

    /**
     * Méthode appellée lors d'une seconde utilisation du fragments
     */
    @Override
    public void onResume() {
        super.onResume();
        // Met en primaryColor l'icone et le texte du fragments
        ((MainActivity) getActivity()).setColors(4, R.color.color_primary,
                true);
        if (ProspectFragment.dernierSalonSelectione == null) {
            ((MainActivity) getActivity()).setColors(2, R.color.invalide,
                    false);
        }
        if (ProjetFragment.dernierProspectSelectionne == null) {
            ((MainActivity) getActivity()).setColors(3, R.color.invalide,
                    false);
        }
    }
}