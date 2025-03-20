package com.example.doliprosp.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.UUID;

public class ChiffrementVigenereTest {

    @Test
    public void chiffrement() {
        //Arrange
        String cle = "6daa50d5-2572-4aca-a58d-4af5deb906ec";
        String champAChiffrer = "Amebhdjcj-gread";
        String champChiffreAttendu = "6pebb3m7i1an6-7";

        //Act
        String champChiffre = ChiffrementVigenere.chiffrement(champAChiffrer, cle);

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
        String cle = "6daa50d5-2572-4aca-a58d-4af5deb906ec";
        String champChiffre = "6pebb3m7i1an6-7";
        String champDechiffreAttendu = "amebhdjcj-gread";

        //Act
        String champDechiffre = ChiffrementVigenere.dechiffrement(champChiffre, cle);

        //Equals
        assertEquals(champDechiffreAttendu, champDechiffre);
    }
}