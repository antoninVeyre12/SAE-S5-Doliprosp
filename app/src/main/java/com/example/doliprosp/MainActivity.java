package com.example.doliprosp;

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
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.fragment.ConnexionFragment;
import com.example.doliprosp.fragment.ProjectFragment;
import com.example.doliprosp.fragment.ProspectFragment;
import com.example.doliprosp.fragment.ShowFragment;
import com.example.doliprosp.fragment.UserFragment;
import com.example.doliprosp.fragment.WaitingFragment;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Show;

public class MainActivity extends AppCompatActivity {
    private RequestQueue fileRequete;
    private TextView[] textViews;
    private ImageView[] imageViews;

    private IApplication applicationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ApplicationViewModel viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        IApplication applicationManager = viewModel.getApplication();

        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);

        textViews = new TextView[]{
                bottomNav.findViewById(R.id.text_waiting),
                bottomNav.findViewById(R.id.text_show),
                bottomNav.findViewById(R.id.text_prospect),
                bottomNav.findViewById(R.id.text_project),
                bottomNav.findViewById(R.id.text_user)
        };

        imageViews = new ImageView[]{
                bottomNav.findViewById(R.id.image_waiting),
                bottomNav.findViewById(R.id.image_show),
                bottomNav.findViewById(R.id.image_prospect),
                bottomNav.findViewById(R.id.image_project),
                bottomNav.findViewById(R.id.image_user)
        };

        // Chargement du fragment par défaut (Salon)
        if (savedInstanceState == null) {
            ConnexionFragment connexionFragment = new ConnexionFragment();
            loadFragment(connexionFragment);
        }

        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            final int index = i;
            bottomNav.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(getFragmentByIndex(index));
                    setColors(index);
                }
            });
        }
    }

    // Méthode pour récupérer le fragment approprié
    private Fragment getFragmentByIndex(int index) {
        switch (index) {
            case 0: return new WaitingFragment();
            case 1: return new ShowFragment();
            case 2: return new ProspectFragment();
            case 3: return new ProjectFragment();
            case 4: return new UserFragment();
            default: return null;
        }
    }

    // Méthode pour charger un fragment
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Méthode pour changer les couleurs
    private void setColors(int selectedIndex) {
        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            textViews[i].setTextColor(Color.BLACK);
            imageViews[i].setColorFilter(Color.BLACK);
        }

        // Changer la couleur pour l'élément sélectionné
        textViews[selectedIndex].setTextColor(getResources().getColor(R.color.color_primary));
        imageViews[selectedIndex].setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.color_primary));
    }

    public RequestQueue getFileRequete() {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(this);
        }
        return fileRequete;
    }

}
