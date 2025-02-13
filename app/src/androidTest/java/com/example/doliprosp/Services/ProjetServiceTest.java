package com.example.doliprosp.Services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Utilisateur;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ChiffrementVigenere.class, Outils.class})
public class ProjetServiceTest {

    private ProjetService projetService;
    private Utilisateur mockUtilisateur;
    private Context mockContext;
    private Projet mockProjet;

    @Before
    public void setUp() {
        projetService = new ProjetService();
        MockitoAnnotations.initMocks(this);
        mockUtilisateur = mock(Utilisateur.class);
        mockContext = mock(Context.class);
        mockProjet = mock(Projet.class);
    }

    @Test
    public void testUpdateProjet() {
        projetService.updateProjet(mockProjet, "New Title", "New Description", "2025-02-13", 987654321L);
        verify(mockProjet).setTitre("New Title");
        verify(mockProjet).setDescription("New Description");
        verify(mockProjet).setDateDebut("2025-02-13");
        verify(mockProjet).setDateTimestamp(987654321L);
    }

    @Test
    public void testGetProjetDUnProspect() {
        ArrayList<Projet> projets = new ArrayList<>();
        projets.add(mockProjet);
        ArrayList<Projet> result = projetService.getProjetDUnProspect(projets, "Prospect1");
        assertEquals(1, result.size());
        assertEquals(mockProjet, result.get(0));
    }

    @Test
    public void testEnvoyerProjet() throws Exception {
        when(mockUtilisateur.getUrl()).thenReturn("https://fakeurl.com");
        when(mockUtilisateur.getCleApi()).thenReturn("fakeApiKey");

        PowerMockito.mockStatic(ChiffrementVigenere.class);
        when(ChiffrementVigenere.dechiffrementCleAPI(anyString(), anyString())).thenReturn("decryptedApiKey");

        PowerMockito.mockStatic(Outils.class);
        doNothing().when(Outils.class);
        Outils.appelAPIPostInteger(anyString(), anyString(), any(JSONObject.class), any(Context.class), any(Outils.APIResponseCallbackPost.class));

        projetService.envoyerProjet(mockUtilisateur, mockContext, mockProjet, 1);

        PowerMockito.verifyStatic(Outils.class);
        Outils.appelAPIPostInteger(anyString(), anyString(), any(JSONObject.class), any(Context.class), any(Outils.APIResponseCallbackPost.class));
    }
}
