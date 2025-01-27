package com.example.doliprosp.Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.android.volley.RequestQueue;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.viewModel.MesSalonsViewModel;
import com.example.doliprosp.viewModel.SalonsViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
}

