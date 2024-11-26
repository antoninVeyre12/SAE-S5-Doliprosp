package com.example.doliprosp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;

public class ConnexionActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private static final String URL = "http://dolibarr.iut-rodez.fr/G2023-42/htdocs/api/index.php/login?login=G42&password=3iFJWj26z";
    private RequestQueue fileRequete;
    private TextView resultatJson;
    private TextView zoneResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        editTextUrl = findViewById(R.id.url);
        editTextUserName = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        resultatJson     = findViewById(R.id.json);
        zoneResultat = findViewById(R.id.Zonejson);

        Button buttonSubmit = findViewById(R.id.connexion);
    }

    public void connexion(View bouton) {
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
            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    private RequestQueue getFileRequete() {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(this);
        }
        return fileRequete;
    }

    public void connexionTest(View bouton) throws JSONException {
        //l'URL de connexion
        String url = URL;
        /*
         * on crée une requête GET, paramètrée par l'url préparée ci-dessus,
         * Le résultat de cette requête sera une chaîne de caractères, donc la requête
         * est de type StringRequest
         */
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                // écouteur de la réponse renvoyée par la requête
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        resultatJson.setText("Début de la réponse obtenue"
                                + reponse.substring(0, Math.min(400, reponse.length())));
                    }
                },
                // écouteur du retour de la requête si aucun résultat n'est renvoyé
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        resultatJson.setText("T NUL");
                    }
                });
        // la requête est placée dans la file d'attente des requêtes
        getFileRequete().add(requeteVolley);

        JSONTokener tokenJSON = new JSONTokener(resultatJson.toString());
        JSONObject objectJSON = (JSONObject) tokenJSON.nextValue();
        StringBuilder resultatFormate = new StringBuilder();
        zoneResultat.setText(resultatFormate.append(objectJSON.getString("Token")));

    }
}
