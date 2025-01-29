package com.example.doliprosp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;

import androidx.fragment.app.Fragment;

public class WaitingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    /**
     * Méthode appellée lors du retour sur l'applicationa fin de restaurer l'état
     * précédemment enregistré
     */
    public void onResume() {
        super.onResume();
        // Met en primaryColor l'icone et le texte du fragment
        ((MainActivity) getActivity()).setColors(0);
    }

}
