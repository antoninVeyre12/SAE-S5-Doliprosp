package com.example.doliprosp.fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UserFragment extends Fragment {
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

        ApplicationViewModel viewModel = new ViewModelProvider(getActivity()).get(ApplicationViewModel.class);
        applicationManager = viewModel.getApplication();

        EditText editTextUrl = view.findViewById(R.id.id_url);
        EditText editTextUserName = view.findViewById(R.id.id_userName);

        String url = applicationManager.getUser().getUrl();
        editTextUrl.setText(url);
        String userName = applicationManager.getUser().getUserName();
        editTextUserName.setText(userName);
    }

    public void deconnexion(View bouton)
    {
        ApplicationViewModel viewModel = new ViewModelProvider(getActivity()).get(ApplicationViewModel.class);
        applicationManager = viewModel.getApplication();

        applicationManager.setUser(new User("","",""));

        ConnexionFragment connexionFragment = new ConnexionFragment();
        ((MainActivity) getActivity()).loadFragment(connexionFragment);
    }
}