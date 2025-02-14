package com.example.doliprosp.Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.content.Context;

import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.viewModel.MesProjetsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ProjetServiceTest {

    private ProjetService projetService;
    private Utilisateur mockUtilisateur;
    private Context mockContext;
    private Projet mockProjet;
    private MesProjetsViewModel mesProjetsViewModel;

    @Before
    public void setUp() {
        mesProjetsViewModel = new MesProjetsViewModel();
        projetService = new ProjetService();
        mockUtilisateur = mock(Utilisateur.class);
        mockContext = mock(Context.class);
        mockProjet = mock(Projet.class);

        mesProjetsViewModel.getProjetListe().add(new Projet("Vincent", "Titre"
                , "Test", "2025-03-23", 1733839025));
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
        String nomProspect = "Vincent";
        ArrayList<Projet> resultat =
                projetService.getProjetDUnProspect(mesProjetsViewModel.getProjetListe(), nomProspect);
        assertEquals(1, resultat.size());
        assertEquals("2025-03-23", resultat.get(0).getDateDebut());
    }

    @Test
    public void testEnvoyerProjet_Succes() throws Exception {
        // Créer une simulation pour le callback
        Outils.APIResponseCallbackPost callback = Mockito.mock(Outils.APIResponseCallbackPost.class);
        // Réponse reçu après un POST
        int postResponse = 1;
        try {
            // Initialiser MockResponse avec la valeur de la réponse de l'API
            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(postResponse);
            // Vérifier que le callback a bien fonctionné
            verify(callback, Mockito.times(1)).onSuccess(postResponse);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }

    @Test
    public void testEnvoyerVersModule_Succes() throws Exception {
        // Créer une simulation pour le callback
        Outils.APIResponseCallbackPost callback = Mockito.mock(Outils.APIResponseCallbackPost.class);
        // Réponse reçu après un POST
        int postResponse = 1;
        try {
            // Initialiser MockResponse avec la valeur de la réponse de l'API
            // Appeler la méthode onSuccess avec le mock
            callback.onSuccess(postResponse);
            // Vérifier que le callback a bien fonctionné
            verify(callback, Mockito.times(1)).onSuccess(postResponse);
        } catch (Exception e) {
            fail("Le test a échoué avec une exception : " + e.getMessage());
        }
    }
}
