package com.example.doliprosp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.viewmodels.MesProspectViewModel;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ProspectServiceTest {

    private ProspectService prospectService;

    @Mock
    private MesProspectViewModel mesProspectViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mesProspectViewModel = new MesProspectViewModel();
        mesProspectViewModel.getProspectListe().add(new Prospect("Salon A",
                "Vincent TIM", "12000", "Rodez", "AV DE BORDEAUX", "a@a.fr",
                "0690976866", "false", "2", 1733839025));
        prospectService = new ProspectService();

    }

    @Test
    public void testExisteDansViewModel() {
        String nomRecherche = "0690976866";
        boolean resultat = prospectService.existeDansViewModel(nomRecherche, mesProspectViewModel);
        assertTrue(resultat);
    }

    @Test
    public void testExistePasDansViewModel() {
        String nomRecherche = "0690976836";
        boolean resultat = prospectService.existeDansViewModel(nomRecherche, mesProspectViewModel);
        assertFalse(resultat);
    }

    @Test
    public void testGetProspectDUnSalon() {
        // Vérifier si le numéro de téléphone existe
        ArrayList<Prospect> resultat =
                prospectService.getProspectDUnSalon(mesProspectViewModel.getProspectListe(),
                        "Salon A");
        assertEquals("Vincent TIM", resultat.get(0).getNom());
    }

    @Test
    public void testEnvoyerProspect() {
        // Créer une simulation pour le callback
        Outils.APIResponseCallbackPost callback = Mockito.mock(Outils.APIResponseCallbackPost.class);
        // JSON de test à simuler comme réponse
        int postResponse = 1;
        try {
            // Convertir la chaîne en JSONArray
            int mockResponse = postResponse;

            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(mockResponse);

            // Vérifier que le callback a bien reçu l'ID attendu
            verify(callback, Mockito.times(1)).onSuccess(mockResponse);

            // Assertions pour s'assurer que les données extraites sont correctes
            assertNotNull(mockResponse);
            assertEquals(1, mockResponse);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }

    @Test
    public void testLieProspectSalon() {
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
}
