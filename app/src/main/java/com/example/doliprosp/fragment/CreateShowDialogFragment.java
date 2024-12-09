package com.example.doliprosp.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CreateShowDialogFragment extends DialogFragment {
    private EditText editTextTitle;
    private TextView erreurNom;
    private Button buttonSubmit;
    private Button buttonCancel;
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

        editTextTitle = view.findViewById(R.id.editTextTitle);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        erreurNom = view.findViewById(R.id.erreur_nom);



        buttonSubmit.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            if (title.length() <= 2 || title.length() >= 50 ) {
                erreurNom.setText(R.string.erreur_nom_salon_longeur);
                erreurNom.setVisibility(View.VISIBLE);
            } else if(ShowFragment.salonExiste(title)){
                Log.d("aaaa","aaaaaaaaaaaaaaaaaaa");
                erreurNom.setText(R.string.erreur_nom_salon_existe);
                erreurNom.setVisibility(View.VISIBLE);

            } else {
                Salon newShow = new Salon(title);
                ShowFragment.ajouterSalonLocal(newShow);
                dismiss();
            }

        });

        buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }


}
