package com.example.doliprosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);

        TextView[] textViews = new TextView[]{
                bottomNav.findViewById(R.id.text_show),
                bottomNav.findViewById(R.id.text_prospect),
                bottomNav.findViewById(R.id.text_waiting),
                bottomNav.findViewById(R.id.text_project),
                bottomNav.findViewById(R.id.text_user)
        };

        ImageView[] imageViews = new ImageView[]{
                bottomNav.findViewById(R.id.image_show),
                bottomNav.findViewById(R.id.image_prospect),
                bottomNav.findViewById(R.id.image_waiting),
                bottomNav.findViewById(R.id.image_project),
                bottomNav.findViewById(R.id.image_user)
        };

        // Chargement du fragment par défaut
        if (savedInstanceState == null) {
            loadFragment(new ShowFragment());
        }
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            final int index = i;
            bottomNav.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (index) {
                        case 0:
                            textViews[index].setTextColor(getResources().getColor(R.color.color_primary));
                            //imageViews[index].setColorFilter(ContextCompat.getColor(this, R.color.color_primary));
                            loadFragment(new ProjectFragment());
                            break;
                        case 1:
                            textViews[index].setTextColor(getResources().getColor(R.color.color_primary));
                            loadFragment(new ProspectFragment());
                            break;
                        case 2:
                            textViews[index].setTextColor(getResources().getColor(R.color.color_primary));
                            loadFragment(new ShowFragment());
                            break;
                        case 3:
                            textViews[index].setTextColor(getResources().getColor(R.color.color_primary));
                            loadFragment(new ShowFragment());
                            break;
                        case 4:
                            textViews[index].setTextColor(getResources().getColor(R.color.color_primary));
                            loadFragment(new UserFragment());
                            break;
                    }
                }
            });
        }

    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
