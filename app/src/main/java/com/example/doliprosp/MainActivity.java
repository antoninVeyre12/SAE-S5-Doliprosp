package com.example.doliprosp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.fragment.ConnexionFragment;
import com.example.doliprosp.fragment.ProjetFragment;
import com.example.doliprosp.fragment.ProspectFragment;
import com.example.doliprosp.fragment.SalonFragment;
import com.example.doliprosp.fragment.UtilisateurFragment;
import com.example.doliprosp.fragment.WaitingFragment;
import com.example.doliprosp.viewModel.MesProjetsViewModel;
import com.example.doliprosp.viewModel.MesProspectViewModel;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.ProspectViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

public class MainActivity extends AppCompatActivity {
    // Déclaration des variables nécessaires
    private RequestQueue fileRequete;
    private TextView[] textViews; // Tableau de TextViews pour la navigation
    private ImageView[] imageViews; // Tableau d'ImageViews pour la navigation
    private SalonsViewModel salonsViewModel; // ViewModel pour gérer les salons
    private MesSalonsViewModel mesSalonsViewModel; // ViewModel pour gérer les salons personnels
    private MesProspectViewModel mesProspectViewModel; // ViewModel pour gérer les prospects
    private MesProjetsViewModel mesProjetsViewModel; // ViewModel pour gérer les prospects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Log.d("MAIN ACTIVITY", "retour dans le main");
        // Chargement du fragment par défaut (Connexion) si c'est la première fois que l'activité est lancée
        if (savedInstanceState == null) {
            bottomNav.setVisibility(View.GONE); // Cache la barre de navigation pour la première connexion
            ConnexionFragment connexionFragment = new ConnexionFragment(); // Fragment de connexion
            loadFragment(connexionFragment); // Chargement du fragment
        }

        // Configuration des actions au clic sur chaque élément du menu de navigation
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            final int index = i;
            bottomNav.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(getFragmentByIndex(index)); // Charger le fragment correspondant
                    setColors(index); // Changer la couleur de l'élément sélectionné
                }
            });
        }
    }

    // Méthode pour récupérer le fragment à afficher selon l'index
    private Fragment getFragmentByIndex(int index) {
        switch (index) {
            case 0: return new WaitingFragment(); // Fragment d'attente
            case 1: return new SalonFragment(); // Fragment des salons
            case 2: return new ProspectFragment(); // Fragment des prospects
            case 3: return new ProjetFragment(); // Fragment des projets
            case 4: return new UtilisateurFragment(); // Fragment des informations utilisateur
            default: return null; // Retourne null si index inconnu
        }
    }

    // Méthode pour charger un fragment dans le conteneur
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // Commencer la transaction du fragment
        transaction.replace(R.id.fragment_container, fragment); // Remplacer le fragment actuellement affiché par le nouveau
        transaction.addToBackStack(null); // Ajouter à la pile arrière pour pouvoir revenir
        transaction.commit(); // Valider la transaction
    }

    // Méthode pour mettre à jour les couleurs des éléments de la navigation
    public void setColors(int selectedIndex) {
        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);
        // Réinitialiser les couleurs pour tous les éléments
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            textViews[i].setTextColor(Color.BLACK);
            imageViews[i].setColorFilter(Color.BLACK);
        }

        // Appliquer la couleur de sélection pour l'élément choisi
        textViews[selectedIndex].setTextColor(getResources().getColor(R.color.color_primary));
        imageViews[selectedIndex].setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.color_primary));
    }

    // Méthode pour obtenir le RequestQueue pour effectuer des requêtes réseau (non utilisée ici)
    public RequestQueue getFileRequete() {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(this); // Initialisation de la file de requêtes si elle n'existe pas
        }
        return fileRequete;
    }

    // Empêcher la navigation arrière de l'utilisateur (pas de retour à l'écran précédent)
    @Override
    public void onBackPressed() {
        // Ne fait rien, empêche le retour arrière
    }

    // Méthode appelée quand l'activité reprend après avoir été suspendue
    @Override
    protected void onResume() {
        super.onResume();
        // Initialisation des ViewModels pour la gestion des salons, prospects et utilisateurs
        salonsViewModel = new ViewModelProvider(this).get(SalonsViewModel.class);
        SharedPreferences sharedPreferencesSalon = getSharedPreferences("user_prefs", MODE_PRIVATE);
        salonsViewModel.initSharedPreferences(sharedPreferencesSalon);

        mesSalonsViewModel = new ViewModelProvider(this).get(MesSalonsViewModel.class);
        SharedPreferences sharedPreferencesMesSalons = getSharedPreferences("user_prefs", MODE_PRIVATE);
        mesSalonsViewModel.initSharedPreferences(sharedPreferencesMesSalons);

        mesProspectViewModel = new ViewModelProvider(this).get(MesProspectViewModel.class);
        SharedPreferences sharedPreferencesMesProspect = getSharedPreferences("user_prefs", MODE_PRIVATE);
        mesProspectViewModel.initSharedPreferences(sharedPreferencesMesProspect);
        mesProjetsViewModel = new ViewModelProvider(this).get(MesProjetsViewModel.class);
        SharedPreferences sharedPreferencesMesProjets = getSharedPreferences("user_prefs", MODE_PRIVATE);
        mesProjetsViewModel.initSharedPreferences(sharedPreferencesMesProjets);


        // recuperer les salons
        salonsViewModel.chargementSalons();
        mesSalonsViewModel.chargementSalons();

        UtilisateurViewModel utilisateurViewModel = new ViewModelProvider(this).get(UtilisateurViewModel.class);
        utilisateurViewModel.initSharedPreferences(this);

        utilisateurViewModel.chargementUtilisateur();

        mesProspectViewModel.chargementProspect();
        mesProjetsViewModel.chargementProjet();
        Log.d("laaaaa", mesProspectViewModel.getProspectListe().toString());
    }
}
