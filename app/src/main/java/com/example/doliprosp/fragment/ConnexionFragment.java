package com.example.doliprosp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.User;

public class ConnexionFragment extends Fragment {
    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.connexion, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        editTextUrl = view.findViewById(R.id.url);
        editTextUserName = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);

        Button buttonSubmit = view.findViewById(R.id.connexion);
        buttonSubmit.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString();
            String userName = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();
            User commercial = new User(url, userName, password);

            ApplicationViewModel viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
            IApplication applicationManager = viewModel.getApplication();

            Boolean userConnected = commercial.connexion();
            if (userConnected) {
                commercial.chiffrementApiKey();
                applicationManager.setUser(commercial);
                ShowFragment showFragment = new ShowFragment();
                ((MainActivity) getActivity()).loadFragment(showFragment);
            }
        });
    }
}
