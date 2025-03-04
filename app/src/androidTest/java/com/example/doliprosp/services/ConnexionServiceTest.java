package com.example.doliprosp.services;

import com.example.doliprosp.modeles.Utilisateur;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ConnexionServiceTest {
    private Utilisateur nouvelUtilisateur;

    @Test
    public void testOnSuccess() {
        // Créer une simulation pour le callback
        Outils.APIResponseCallback callback = Mockito.mock(Outils.APIResponseCallback.class);
        // JSON de test à simuler comme réponse
        String jsonResponse = "{\"success\": {\"token\": \"abcd1234\"}}";

        try {
            // Convertir la chaîne en JSONObject
            JSONObject mockResponse = new JSONObject(jsonResponse);

            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(mockResponse);

            // Vérifier que le callback a bien reçu le JSONObject attendu
            Mockito.verify(callback, Mockito.times(1)).onSuccess(mockResponse);

            // Vérifier la logique interne si nécessaire
            JSONObject successJSON = mockResponse.getJSONObject("success");
            String apiKey = successJSON.getString("token");

            // Assertions pour s'assurer que les données extraites sont correctes
            assertNotNull(apiKey);
            assertEquals("abcd1234", apiKey);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }

    @Test
    public void testChiffrementApiKey() {
        //TODO
    }

    @Test
    public void testGetNouvelUtilisateur() {
        // Configurer un utilisateur fictif
        nouvelUtilisateur = new Utilisateur("http://example.com", "user", "password", "abcd1234");

        assertNotNull(nouvelUtilisateur);
        assertEquals("http://example.com", nouvelUtilisateur.getUrl());
        assertEquals("user", nouvelUtilisateur.getNomUtilisateur());
        assertEquals("password", nouvelUtilisateur.getMotDePasse());
        assertEquals("abcd1234", nouvelUtilisateur.getCleApi());
    }

}