package com.example.doliprosp.Services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.util.Log;

import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.viewModel.MesProspectViewModel;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ProspectServiceTest extends TestCase {
    private MesProspectViewModel mesProspectViewModel;
    private ProspectService prospectService;

    @Before
    public void setup() {
        mesProspectViewModel = new MesProspectViewModel();
        prospectService = new ProspectService();

        // Initialisation des listes avec quelques salons pour les tests
        mesProspectViewModel.getProspectListe().add(new Prospect("Salon A",
                "Vincent TIM", "12000", "Rodez", "AV DE BORDEAUX", "a@a.fr",
                "0690976866", "false", "lalala", "2", 1733839025));
    }

    @Test
    public void testListeProspectsInitialisee() {
        assertNull(mesProspectViewModel.getProspectListe());
        assertTrue(mesProspectViewModel.getProspectListe().isEmpty());
    }

    @Test
    public void testExisteDansViewModel() {
        Log.d("TEST", mesProspectViewModel.getProspectListe().toString());
        String nomRecherche = "0676767879";
        boolean resultat = prospectService.existeDansViewModel(nomRecherche, mesProspectViewModel);
        assertFalse(resultat);
    }

    @Test
    public void testExistessDansViewModel() {
        // Initialiser une liste de prospects
        ArrayList<Prospect> prospects = new ArrayList<>();
        prospects.add(new Prospect("Salon A",
                "Vincent TIM", "12000", "Rodez", "AV DE BORDEAUX", "a@a.fr",
                "0690976866", "false", "lalala", "2", 1733839025));

        // Mock de ViewModel
        MesProspectViewModel mesProspectViewModel = mock(MesProspectViewModel.class);
        when(mesProspectViewModel.getProspectListe()).thenReturn(prospects);

        // Numéro de téléphone à tester
        String numeroRecherche = "0676767879";

        // Appel de la méthode
        boolean resultat = prospectService.existeDansViewModel(numeroRecherche, mesProspectViewModel);

        // Vérification du résultat
        assertFalse(resultat);
    }
}