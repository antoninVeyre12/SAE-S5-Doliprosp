package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.Application;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class CreateShowDialogFragment extends DialogFragment {

    private IApplication applicationManager;
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un salon");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_show, container, false);

        ApplicationViewModel viewModel = new ViewModelProvider(getActivity()).get(ApplicationViewModel.class);
        IApplication applicationManager = viewModel.getApplication();

        EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonSubmit.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();

            Show newShow = new Show(title);
            applicationManager.addLocalShow(newShow);

            dismiss();
        });

        buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }
}
