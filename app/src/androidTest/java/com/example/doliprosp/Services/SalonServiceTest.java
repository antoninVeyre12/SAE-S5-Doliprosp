package com.example.doliprosp.Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;

import junit.framework.TestCase;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class SalonServiceTest extends TestCase {
    private Utilisateur utilisateur;
    private Outils.APIResponseCallbackArray callback;
    private ArrayList<Salon> listeSalonsEnregistres;
    private Context mockContext;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
        listeSalonsEnregistres = new ArrayList<>();

        // Simule le contexte (évite les NullPointerExceptions)
        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        if (mockContext == null) {
            throw new IllegalStateException("Le contexte est null. Vérifie l'initialisation.");
        }

    }

    @Test
    public void testListeSalonsEnregistres_Succes() throws Exception {
        // Simule la réponse de l'API
        JSONArray fakeResponse = new JSONArray();
        fakeResponse.put(new JSONObject().put("label", "Salon 1"));
        fakeResponse.put(new JSONObject().put("label", "Salon 2"));

        // Capture le callback pour simuler la réponse
        ArgumentCaptor<Outils.APIResponseCallbackArray> captor = ArgumentCaptor.forClass(Outils.APIResponseCallbackArray.class);

        // Appel simulé de l'API
        Outils.appelAPIGetList(
                eq("https://exemple.com/api/index.php/categories?sortfield=t.date_creation&sortorder=DESC&limit=6&sqlfilters=(t.label%3Alike%3A'%25recherche%25')"),
                eq("cle-api"),
                eq(mockContext),
                captor.capture()
        );

        // Simule le succès de l'appel API avec une réponse JSON
        captor.getValue().onSuccess(fakeResponse);

        // Vérifie que les salons ont été ajoutés correctement
        assertEquals(2, listeSalonsEnregistres.size());
        assertEquals("Salon 1", listeSalonsEnregistres.get(0).getNom());
        assertEquals("Salon 2", listeSalonsEnregistres.get(1).getNom());
    }
}