
package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doliprosp.R;
import com.example.doliprosp.Modele.Salon;

import java.io.Serializable;

public class ProspectFragment extends Fragment {

    private TextView salonActuelEditText;
    private Salon salonActuel;
    private static Salon dernierSalonSelectione;
    private Button boutonCreerProspect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        if (bundle != null) {
            salonActuel = (Salon) bundle.getSerializable("salon");
            dernierSalonSelectione = salonActuel;
        } else {
            if (dernierSalonSelectione != null) {
                salonActuel = dernierSalonSelectione;
            } else {
            }
        }
        return inflater.inflate(R.layout.fragment_prospect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        boutonCreerProspect = view.findViewById(R.id.buttonCreateProspect);
        setupListeners();
    }

    private void setupListeners() {
        // TODO Lancer la recherche avec le texte saisi

        // Ajouter un salon
        boutonCreerProspect.setOnClickListener(v -> {
            CreationProspectDialogFragment dialog = new CreationProspectDialogFragment();
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });
    }

}