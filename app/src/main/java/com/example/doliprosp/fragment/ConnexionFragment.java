package com.example.doliprosp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConnexionFragment extends Fragment {
    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;

    private String urlConnexion;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.connexion, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        IApplication applicationManager = ApplicationViewModel.getApplication();
        //String urlConnexion;
        editTextUrl = view.findViewById(R.id.url);
        editTextUserName = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);

        // Recupere la bottom nav bar de l'activité
        Activity activity = getActivity();
        LinearLayout bottomNav = activity.findViewById(R.id.bottom_navigation);

        Button buttonSubmit  = view.findViewById(R.id.connexion);
        buttonSubmit.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString();
            String userName = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            if (url.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Log.d("TEST VIDE", "OK");
            }

            try {
                String userNameEncoder = URLEncoder.encode(userName, "UTF-8");
                String passwordEncoder = URLEncoder.encode(password, "UTF-8");
                urlConnexion = String.format("%s?login=%s&password=%s", url, userNameEncoder, passwordEncoder);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


            User commercial = new User(url, userName, password);
            try {
                String apiKey = commercial.connexion(urlConnexion,getContext());
                Log.d("APIKEY", apiKey);
                //commercial.chiffrementApiKey();
                //commercial.setApiKey(apiKey);
                applicationManager.setUser(commercial);
                // Rend visible la bottom nav bar
                bottomNav.setVisibility(View.VISIBLE);
                ShowFragment showFragment = new ShowFragment();
                ((MainActivity) getActivity()).loadFragment(showFragment);
                ((MainActivity) getActivity()).setColors(1);
            } catch(Exception e) {
                Log.d("text", e.getMessage());
            }
        });

    }
}
