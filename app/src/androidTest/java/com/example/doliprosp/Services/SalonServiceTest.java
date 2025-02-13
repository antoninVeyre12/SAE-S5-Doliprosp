package com.example.doliprosp.Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;

public class SalonServiceTest {
    private SalonsViewModel salonsViewModel;
    private MesSalonsViewModel mesSalonsViewModel;
    private SalonService salonService;


    @Before
    public void setup() {
        salonsViewModel = new SalonsViewModel();
        mesSalonsViewModel = new MesSalonsViewModel();
        salonService = new SalonService();

        // Initialisation des listes avec quelques salons pour les tests
        salonsViewModel.getSalonListe().add(new Salon("Salon A"));
        salonsViewModel.getSalonListe().add(new Salon("Salon B"));

        mesSalonsViewModel.getSalonListe().add(new Salon("Salon X"));
        mesSalonsViewModel.getSalonListe().add(new Salon("Salon Y"));
    }

    @Test
    public void testListeSalonsEnregistres_Succes() throws Exception {
        // Créer une simulation pour le callback
        Outils.APIResponseCallbackArray callback = Mockito.mock(Outils.APIResponseCallbackArray.class);
        // JSON de test à simuler comme réponse
        String jsonResponse = "[{\"label\": \"nomSalon1\"}]";

        try {
            // Convertir la chaîne en JSONArray
            JSONArray mockResponse = new JSONArray(jsonResponse);

            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(mockResponse);

            // Vérifier que le callback a bien reçu le JSONArray attendu
            verify(callback, Mockito.times(1)).onSuccess(mockResponse);

            // Extraire le premier objet JSON du tableau
            JSONObject salonObject = mockResponse.getJSONObject(0);
            String nomSalon = salonObject.getString("label");

            // Assertions pour s'assurer que les données extraites sont correctes
            assertNotNull(nomSalon);
            assertEquals("nomSalon1", nomSalon);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }

    @Test
    public void testSalonExiste_inSalonsViewModelOnly() {
        String nomRecherche = "Salon A";
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertTrue(result);
    }

    @Test
    public void testSalonExiste_inMesSalonsViewModelOnly() {
        String nomRecherche = "Salon X";
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertTrue(result);
    }

    @Test
    public void testSalonExiste_inBothViewModels() {
        String nomRecherche = "Salon B";
        mesSalonsViewModel.getSalonListe().add(new Salon("Salon B"));
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertTrue(result);
    }

    @Test
    public void testSalonExiste_notInAnyViewModel() {
        String nomRecherche = "Salon Z";
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertFalse(result);
    }

    @Test
    public void testSalonExiste_caseInsensitive() {
        String nomRecherche = "salon a";
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertTrue(result);
    }

    @Test
    public void testSalonExiste_nullName() {
        String nomRecherche = null;
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertFalse(result);
    }

    @Test
    public void testSalonExiste_emptyName() {
        String nomRecherche = "";
        boolean result = salonService.salonExiste(nomRecherche, salonsViewModel, mesSalonsViewModel);
        assertFalse(result);
    }
    @Test
    public void testGetListeSalonsSelectionnes() {
        mesSalonsViewModel.getSalonListe().get(0).setEstSelectionne(true);
        salonsViewModel.getSalonListe().get(1).setEstSelectionne(true);

        List<Salon> result = salonService.getListeSalonsSelectionnes(mesSalonsViewModel, salonsViewModel);
        assertEquals("Salon X", result.get(0).getNom());
        assertEquals("Salon B", result.get(1).getNom());
        assertEquals(2, result.size());
    }

    @Test
    public void testRechercheMesSalons() {
        List<Salon> result = salonService.rechercheMesSalons("Salon X", mesSalonsViewModel);
        assertEquals(1, result.size());
        assertEquals("Salon X", result.get(0).getNom());
    }

    @Test
    public void testRechercheSalons() {
        List<Salon> result = salonService.rechercheSalons("Salon A", salonsViewModel);
        assertEquals(1, result.size());
        assertEquals("Salon A", result.get(0).getNom());
    }

    @Test
    public void testRecupererIdSalon_Succes() throws Exception {
        // Créer une simulation pour le callback
        Outils.APIResponseCallbackArray callback = Mockito.mock(Outils.APIResponseCallbackArray.class);
        // JSON de test à simuler comme réponse
        String jsonResponse = "[{\"id\": \"2\"}]";

        try {
            // Convertir la chaîne en JSONArray
            JSONArray mockResponse = new JSONArray(jsonResponse);

            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(mockResponse);

            // Vérifier que le callback a bien reçu le JSONArray attendu
            verify(callback, Mockito.times(1)).onSuccess(mockResponse);

            // Extraire le premier objet JSON du tableau
            JSONObject idObject = mockResponse.getJSONObject(0);
            String idSalon = idObject.getString("id");

            // Assertions pour s'assurer que les données extraites sont correctes
            assertNotNull(idSalon);
            assertEquals("2", idSalon);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }

    @Test
    public void testEnvoyerSalon_Succes() throws Exception {
        // Créer une simulation pour le callback
        Outils.APIResponseCallbackPost callback = Mockito.mock(Outils.APIResponseCallbackPost.class);
        // JSON de test à simuler comme réponse
        int postResponse = 1;

        try {
            // Convertir la chaîne en JSONArray
            int mockResponse = postResponse;

            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(mockResponse);

            // Vérifier que le callback a bien reçu le JSONArray attendu
            verify(callback, Mockito.times(1)).onSuccess(mockResponse);

            // Assertions pour s'assurer que les données extraites sont correctes
            assertNotNull(mockResponse);
            assertEquals(1, mockResponse);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }
}

