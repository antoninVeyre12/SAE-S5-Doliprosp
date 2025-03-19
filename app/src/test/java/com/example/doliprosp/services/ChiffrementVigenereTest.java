package com.example.doliprosp.Services;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ChiffrementVigenereTest {

    @Test
    public void chiffrement() {
        //Arrange
        String champAChiffrer = "Amebhdjcj-gread";
        String champChiffreAttendu = "6pebb3m7i1an6-7";

        //Act
        String champChiffre = ChiffrementVigenere.chiffrement(champAChiffrer);

        //Assert
        assertEquals(champChiffreAttendu, champChiffre);

    }

    @Test
    public void chiffrementCleAPI() {
        //Arrange
        String cleApi = "400b0d425318dfb9921415256b6de8a987bf2be0";
        String cleChiffrement = "6pebb3m7i1an6-7dsjgerjx2s6uc";
        String cleApiChiffreAttendu = "ze4c16fyct1k9e8bqa78hdown7pf-me-9znb-2ec";

        //Act
        String cleApiChiffre = ChiffrementVigenere.chiffrementCleAPI(cleApi, cleChiffrement);

        //Equals
        assertEquals(cleApiChiffreAttendu, cleApiChiffre);
    }

    @Test
    public void dechiffrementCleAPI() {

        //Arrange
        String cleApiChiffre = "ze4c16fyct1k9e8bqa78hdown7pf-me-9znb-2ec";
        String cleChiffrement = "6pebb3m7i1an6-7dsjgerjx2s6uc";
        String cleApiChiffreAttendu = "400b0d425318dfb9921415256b6de8a987bf2be0";

        //Act
        String cleApiDeChiffre = ChiffrementVigenere.dechiffrementCleAPI(cleApiChiffre, cleChiffrement);

        //Equals
        assertEquals(cleApiChiffreAttendu, cleApiDeChiffre);
    }

    @Test
    public void dechiffrement() {
        //Arrange
        String champChiffre = "6pebb3m7i1an6-7";
        String champDechiffreAttendu = "amebhdjcj-gread";

        //Act
        String champDechiffre = ChiffrementVigenere.dechiffrement(champChiffre);

        //Equals
        assertEquals(champDechiffreAttendu, champDechiffre);
    }
}