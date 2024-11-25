package com.example.doliprosp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.User;

public class ConnexionActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        editTextUrl = findViewById(R.id.url);
        editTextUserName = findViewById(R.id.userName);
        editTextPassword = findViewById(R.id.password);

        Button buttonSubmit = view.findViewById(R.id.connexion);

    }

    public void connexion(View bouton) {
        String url = editTextUrl.GetText().ToString();
        String userName = editTextUserName.GetText().ToString();
        String password = editTextPassword.GetText().ToString();
        User commercial = new User(url, userName, password);

        ApplicationViewModel viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        IApplication applicationManager = viewModel.getApplication();

        Boolean userConnected = commercial.connexion();
        if (userConnected) {
            commercial.chiffrementApiKey();
            applicationManager.setUser(commercial);
            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
}
