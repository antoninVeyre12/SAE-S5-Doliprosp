package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Outils;
import com.example.doliprosp.treatment.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserFragment extends Fragment {
    private String mail;
    private IApplication applicationManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        applicationManager = ApplicationViewModel.getApplication();

        TextView textViewUrl = view.findViewById(R.id.id_url);
        TextView textViewUserName = view.findViewById(R.id.id_userName);
        TextView textViewMail = view.findViewById(R.id.id_mail);

        String url = applicationManager.getUser().getUrl();
        textViewUrl.setText(url);
        String userName = applicationManager.getUser().getUserName();
        textViewUserName.setText(userName);

        String urlRequeteGetCommercial = "http://dolibarr.iut-rodez.fr/G2023-42/htdocs/api/index.php/users/5";
        Outils.appelAPIGet(urlRequeteGetCommercial, getContext(), new Outils.APIResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                // Cela s'exécutera lorsque l'API renvoie une réponse valide
                Log.d("feoejf,oeakf", response.toString());
            }

            @Override
            public void onError(String errorMessage) {
                // Cela s'exécutera en cas d'erreur dans l'appel API
                Log.d("BAD APPEL API", errorMessage);
            }
        });

        Button btnDeconnexion = view.findViewById(R.id.btnDeconnexion);
        btnDeconnexion.setOnClickListener(v -> {
            applicationManager.setUser(new User("","",""));
            ConnexionFragment connexionFragment = new ConnexionFragment();
            ((MainActivity) getActivity()).loadFragment(connexionFragment);
        });
    }
}