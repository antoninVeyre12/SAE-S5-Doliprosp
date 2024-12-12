package com.example.doliprosp.Services;

import android.content.Context;

import com.example.doliprosp.Interface.ConnexionCallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.net.URLEncoder;

public class ConnexionServiceTest {
    private ConnexionService connexionService;

    @Mock
    private Context mockContext;

    @Mock
    private ConnexionCallBack mockCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        connexionService = new ConnexionService();
    }

    @Test
    public void testConnexion_successful() throws JSONException {
        // Préparer les données d'entrée
        String url = "http://example.com";
        String userName = "testUser";
        String password = "testPass";
        String encodedUserName = "testUserEncoded";
        String encodedPassword = "testPassEncoded";
        String apiKey = "fakeApiKey";

        // Simuler le comportement de URLEncoder
        mockStatic(URLEncoder.class);
        when(URLEncoder.encode(userName, "UTF-8")).thenReturn(encodedUserName);
        when(URLEncoder.encode(password, "UTF-8")).thenReturn(encodedPassword);

        // Simuler la réponse de l'API
        JSONObject fakeResponse = new JSONObject();
    }

    @Test
    public void connexion() {

    }

    @Test
    public void chiffrementApiKey() {
    }

    @Test
    public void getNouvelUtilisateur() {
    }
}