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

import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Show;
import com.example.doliprosp.treatment.User;

public class UserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
}