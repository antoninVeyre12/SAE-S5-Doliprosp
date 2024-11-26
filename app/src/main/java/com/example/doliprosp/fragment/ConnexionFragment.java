package com.example.doliprosp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ConnexionFragment extends Fragment {
    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;

    private static final String URL = "http://dolibarr.iut-rodez.fr/G2023-42/htdocs/api/index.php/login?login=G42&password=3iFJWj26z";
    private TextView resultatJson;
    private TextView zoneResultat;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.connexion, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ApplicationViewModel viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        IApplication applicationManager = viewModel.getApplication();

        editTextUrl = view.findViewById(R.id.url);
        editTextUserName = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);

        Button buttonSubmit = view.findViewById(R.id.connexion);
        buttonSubmit.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString();
            String userName = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            User commercial = new User(url, userName, password);
            try {
                String apiKey = commercial.connexion(url,userName,password);
                commercial.chiffrementApiKey();
                commercial.setApiKey(apiKey);
                applicationManager.setUser(commercial);
                ShowFragment showFragment = new ShowFragment();
                ((MainActivity) getActivity()).loadFragment(showFragment);
            } catch(Exception e) {
                Log.d("text", e.getMessage());
            }
        });
    }

}
