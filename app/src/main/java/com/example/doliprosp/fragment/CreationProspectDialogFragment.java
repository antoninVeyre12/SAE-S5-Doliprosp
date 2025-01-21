package com.example.doliprosp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.doliprosp.Interface.IProspectService;

import com.example.doliprosp.R;
import com.example.doliprosp.Services.ProspectService;


import java.util.ArrayList;

public class CreationProspectDialogFragment extends DialogFragment{
    private IProspectService prospectService;
    private Button boutonEnvoyer;
    private Button boutonAnnuler;
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Créer un prospect");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_prospect, container, false);

        prospectService = new ProspectService();

        boutonEnvoyer = view.findViewById(R.id.buttonSubmit);
        boutonAnnuler = view.findViewById(R.id.buttonCancel);



        boutonAnnuler.setOnClickListener(v -> {
            dismiss();
        });



        return view;
    }
}
