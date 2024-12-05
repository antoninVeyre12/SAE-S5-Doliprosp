
package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doliprosp.R;
import com.example.doliprosp.Model.Salon;

public class ProspectFragment extends Fragment {

    private TextView salonActuelEditText;
    private Salon salonActuel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        if (bundle != null) {
            salonActuel = (Salon) bundle.getSerializable("salon");
        } else {
            // Creer un dialog qui demande de choisir dans une liste
        }
        return inflater.inflate(R.layout.fragment_prospect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        salonActuelEditText = view.findViewById(R.id.salonActuel);
        salonActuelEditText.setText(salonActuel.getNom());

    }

}