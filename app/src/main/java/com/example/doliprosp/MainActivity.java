package com.example.doliprosp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.fragments.ConnexionFragment;
import com.example.doliprosp.fragments.ProjetFragment;
import com.example.doliprosp.fragments.ProspectFragment;
import com.example.doliprosp.fragments.SalonFragment;
import com.example.doliprosp.fragments.UtilisateurFragment;
import com.example.doliprosp.fragments.WaitingFragment;
import com.example.doliprosp.viewModels.MesProjetsViewModel;
import com.example.doliprosp.viewModels.MesProspectViewModel;
import com.example.doliprosp.viewModels.MesSalonsViewModel;
import com.example.doliprosp.viewModels.SalonsViewModel;
import com.example.doliprosp.viewModels.UtilisateurViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    // Déclaration des variables nécessaires
    private RequestQueue fileRequete;

    private ImageView statusConnection;  // Icône de statut de connexion
    private BroadcastReceiver networkReceiver;

    private TextView[] textViews; // Tableau de TextViews pour la navigation
    private ImageView[] imageViews; // Tableau d'ImageViews pour la navigation
    private SalonsViewModel salonsViewModel; // ViewModel pour gérer les salons
    private MesSalonsViewModel mesSalonsViewModel; // ViewModel pour gérer
    // les salons personnels
    private MesProspectViewModel mesProspectViewModel; // ViewModel pour
    // gérer les prospects
    private MesProjetsViewModel mesProjetsViewModel; // ViewModel pour gérer
    // les prospects


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusConnection = findViewById(R.id.status_connection);
        setupNetworkReceiver();


        // Initialisation du menu de navigation
        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);

        // Initialisation des TextViews et ImageViews pour la navigation
        textViews = new TextView[]{
                bottomNav.findViewById(R.id.texte_attente),
                bottomNav.findViewById(R.id.texte_salon),
                bottomNav.findViewById(R.id.texte_prospect),
                bottomNav.findViewById(R.id.texte_projet),
                bottomNav.findViewById(R.id.texte_utilisateur)
        };

        imageViews = new ImageView[]{
                bottomNav.findViewById(R.id.image_attente),
                bottomNav.findViewById(R.id.image_salon),
                bottomNav.findViewById(R.id.image_prospect),
                bottomNav.findViewById(R.id.image_project),
                bottomNav.findViewById(R.id.image_utilisateur)
        };

        // Chargement du fragments par défaut (Connexion) si c'est la première
        // fois que l'activité est lancée
        if (savedInstanceState == null) {
            bottomNav.setVisibility(View.GONE); // Cache la barre de
            // navigation pour la première connexion
            ConnexionFragment connexionFragment = new ConnexionFragment(); //
            // Fragment de connexion
            loadFragment(connexionFragment); // Chargement du fragments
        }

        // Configuration des actions au clic sur chaque élément du menu de
        // navigation
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            final int index = i;
            bottomNav.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(getFragmentByIndex(index)); // Charger le
                    // fragments correspondant
                    setColors(index, R.color.color_primary, true); // Changer
                    // la couleur de l'élément sélectionné
                }
            });
        }
    }

    private void setupNetworkReceiver() {
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected =
                        activeNetwork != null && activeNetwork.isConnected();
                updateConnectionStatus(isConnected);
            }
        };
    }

    private void updateConnectionStatus(boolean isConnected) {
        if (isConnected) {
            statusConnection.setImageResource(R.drawable.ic_plus); // Vert
            // (connecté)
        } else {
            statusConnection.setImageResource(R.drawable.round_button); //
            // Rouge (hors ligne)
        }
    }


    // Méthode pour récupérer le fragments à afficher selon l'index
    private Fragment getFragmentByIndex(int index) {
        switch (index) {
            case 0:
                return new WaitingFragment(); // Fragment d'attente
            case 1:
                return new SalonFragment(); // Fragment des salons
            case 2:
                return new ProspectFragment(); // Fragment des prospects
            case 3:
                return new ProjetFragment(); // Fragment des projets
            case 4:
                return new UtilisateurFragment(); // Fragment des
            // informations utilisateur
            default:
                return null; // Retourne null si index inconnu
        }
    }

    // Méthode pour charger un fragments dans le conteneur
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction(); // Commencer
        // la transaction du fragments
        transaction.replace(R.id.fragment_container, fragment); // Remplacer
        // le fragments actuellement affiché par le nouveau
        transaction.addToBackStack(null); // Ajouter à la pile arrière pour
        // pouvoir revenir
        transaction.commit(); // Valider la transaction
    }

    // Méthode pour mettre à jour les couleurs des éléments de la navigation
    public void setColors(int selectedIndex, int color, boolean reset) {
        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);
        // Réinitialiser les couleurs pour tous les éléments
        if (reset) {
            for (int i = 0; i < bottomNav.getChildCount(); i++) {
                textViews[i].setTextColor(Color.BLACK);
                imageViews[i].setColorFilter(Color.BLACK);
            }
        }

        // Appliquer la couleur de sélection pour l'élément choisi
        textViews[selectedIndex].setTextColor(getResources().getColor(color));
        imageViews[selectedIndex].setColorFilter(ContextCompat.getColor(MainActivity.this, color));
    }


    // Méthode pour obtenir le RequestQueue pour effectuer des requêtes
    // réseau (non utilisée ici)
    public RequestQueue getFileRequete() {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(this); // Initialisation de
            // la file de requêtes si elle n'existe pas
        }
        return fileRequete;
    }

    // Empêcher la navigation arrière de l'utilisateur (pas de retour à
    // l'écran précédent)
    @Override
    public void onBackPressed() {
        // Ne fait rien, empêche le retour arrière
    }

    // Méthode appelée quand l'activité reprend après avoir été suspendue
    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(networkReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        // Initialisation des ViewModels pour la gestion des salons,
        // prospects et utilisateurs
        salonsViewModel =
                new ViewModelProvider(this).get(SalonsViewModel.class);

        mesSalonsViewModel =
                new ViewModelProvider(this).get(MesSalonsViewModel.class);
        mesProspectViewModel =
                new ViewModelProvider(this).get(MesProspectViewModel.class);
        mesProjetsViewModel =
                new ViewModelProvider(this).get(MesProjetsViewModel.class);


        // recuperer les salons
        salonsViewModel.chargementSalons(this);
        mesSalonsViewModel.chargementSalons(this);

        UtilisateurViewModel utilisateurViewModel =
                new ViewModelProvider(this).get(UtilisateurViewModel.class);

        utilisateurViewModel.chargementUtilisateur(this);

        mesProspectViewModel.chargementProspect(this);
        mesProjetsViewModel.chargementProjet(this);
    }
}
