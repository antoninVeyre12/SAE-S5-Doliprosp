package com.example.doliprosp;

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
import com.example.doliprosp.viewModel.SalonsViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

/**
 * Activité principale de l'application gérant la navigation et l'affichage
 * des fragments.
 */
public class MainActivity extends AppCompatActivity {
    private RequestQueue fileRequete;
    private TextView[] textViews;
    private ImageView[] imageViews;
    private SalonsViewModel salonsViewModel;
    private MesSalonsViewModel mesSalonsViewModel;
    private MesProspectViewModel mesProspectViewModel;
    private MesProjetsViewModel mesProjetsViewModel;

    /**
     * Méthode appelée lors de la création de l'activité.
     *
     * @param savedInstanceState L'état précédent de l'activité, s'il existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);

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

        Log.d("MAIN ACTIVITY", "Retour dans le main");

        if (savedInstanceState == null) {
            bottomNav.setVisibility(View.GONE);
            loadFragment(new ConnexionFragment());
        }

        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            final int index = i;
            bottomNav.getChildAt(i).setOnClickListener(v -> {
                loadFragment(getFragmentByIndex(index));
                setColors(index);
            });
        }
    }

    /**
     * Retourne le fragment correspondant à l'index sélectionné.
     *
     * @param index L'index du fragment à récupérer.
     * @return Le fragment correspondant.
     */
    private Fragment getFragmentByIndex(int index) {
        switch (index) {
            case 0:
                return new WaitingFragment();
            case 1:
                return new SalonFragment();
            case 2:
                return new ProspectFragment();
            case 3:
                return new ProjetFragment();
            case 4:
                return new UtilisateurFragment();
            default:
                return null;
        }
    }

    /**
     * Charge un fragment dans le conteneur principal.
     *
     * @param fragment Le fragment à afficher.
     */
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Met à jour les couleurs des éléments de navigation.
     *
     * @param selectedIndex L'index de l'élément sélectionné.
     */
    public void setColors(int selectedIndex) {
        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            textViews[i].setTextColor(Color.BLACK);
            imageViews[i].setColorFilter(Color.BLACK);
        }

        textViews[selectedIndex].setTextColor(getResources().getColor(R.color.color_primary));
        imageViews[selectedIndex].setColorFilter(ContextCompat.getColor(this,
                R.color.color_primary));
    }

    /**
     * Retourne la file de requêtes réseau.
     *
     * @return La file de requêtes {@link RequestQueue}.
     */
    public RequestQueue getFileRequete() {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(this);
        }
        return fileRequete;
    }

    /**
     * Désactive le bouton retour pour éviter la navigation arrière.
     */
    @Override
    public void onBackPressed() {
        // Désactivation du retour en arrière
    }

    /**
     * Méthode appelée lors de la reprise de l'activité.
     */
    @Override
    protected void onResume() {
        super.onResume();

        salonsViewModel =
                new ViewModelProvider(this).get(SalonsViewModel.class);
        SharedPreferences sharedPreferencesSalon = getSharedPreferences(
                "user_prefs", MODE_PRIVATE);
        salonsViewModel.initSharedPreferences(sharedPreferencesSalon);

        mesSalonsViewModel =
                new ViewModelProvider(this).get(MesSalonsViewModel.class);
        SharedPreferences sharedPreferencesMesSalons = getSharedPreferences(
                "user_prefs", MODE_PRIVATE);
        mesSalonsViewModel.initSharedPreferences(sharedPreferencesMesSalons);

        mesProspectViewModel =
                new ViewModelProvider(this).get(MesProspectViewModel.class);
        SharedPreferences sharedPreferencesMesProspect =
                getSharedPreferences("user_prefs", MODE_PRIVATE);
        mesProspectViewModel.initSharedPreferences(sharedPreferencesMesProspect);

        mesProjetsViewModel =
                new ViewModelProvider(this).get(MesProjetsViewModel.class);
        SharedPreferences sharedPreferencesMesProjets = getSharedPreferences(
                "user_prefs", MODE_PRIVATE);
        mesProjetsViewModel.initSharedPreferences(sharedPreferencesMesProjets);

        salonsViewModel.chargementSalons();
        mesSalonsViewModel.chargementSalons();

        UtilisateurViewModel utilisateurViewModel =
                new ViewModelProvider(this).get(UtilisateurViewModel.class);
        utilisateurViewModel.initSharedPreferences(this);
        utilisateurViewModel.chargementUtilisateur();

        mesProspectViewModel.chargementProspect();
        mesProjetsViewModel.chargementProjet();

        Log.d("laaaaa", mesProspectViewModel.getProspectListe().toString());
    }
}
