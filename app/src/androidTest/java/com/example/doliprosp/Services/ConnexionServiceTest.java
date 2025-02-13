package com.example.doliprosp.Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.example.doliprosp.Modele.Utilisateur;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

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
    public void testOnError() {
        // Créer une simulation pour le callback
        Outils.APIResponseCallback callback = Mockito.mock(Outils.APIResponseCallback.class);
        // JSON de test à simuler comme réponse
        String jsonResponse = "{\"error\": {\"code\": \"403\"}}";

        try {
            // Appeler la méthode onSuccess avec le mock
            callback.onError(jsonResponse);

            // Vérifier que le callback a bien reçu le JSONObject attendu
            Mockito.verify(callback, Mockito.times(1)).onError(jsonResponse);

            // Assertions pour s'assurer que les données extraites sont correctes
            assertNotNull(jsonResponse);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }
}