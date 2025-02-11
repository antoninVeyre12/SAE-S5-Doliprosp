package com.example.doliprosp.Services;

import java.util.UUID;

public class ChiffrementVigenere {

    private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};

    public static String key = UUID.randomUUID().toString();


    public static String chiffrement(String cleApi) {
        int keyIndex = 0;
        String cleChiffre = "";
        for (int i = 0; i < cleApi.length(); i++) {
            char caractere = cleApi.charAt(i);
            char caractereKey = key.charAt(keyIndex);

            int positionCaractereChiffre = getCaractereChiffre(caractere,
                    caractereKey);
            char caractereChiffre = alphabet[positionCaractereChiffre];
            cleChiffre += caractereChiffre;
            keyIndex = (keyIndex + 1) % key.length();
        }
        return cleChiffre;
    }

    public static String dechiffrement(String cleChiffre) {
        int keyIndex = 0;
        String cleDechiifre = "";
        for (int i = 0; i < cleChiffre.length(); i++) {
            char caractere = cleChiffre.charAt(i);
            char caractereKey = key.charAt(keyIndex);

            int positionCaractereDeChiffre = getCaractereDeChiffre(caractere,
                    caractereKey);
            char caractereDeChiffre =
                    alphabet[positionCaractereDeChiffre];
            cleDechiifre += caractereDeChiffre;

            keyIndex = (keyIndex + 1) % key.length();

        }
        return cleDechiifre;

    }

    private static int getCaractereDeChiffre(char caractere, char caractereKey) {

        int positionCararctere = getPositionDansAlphabet(caractere);
        int positionCaractereKey = getPositionDansAlphabet(caractereKey);
        return (positionCararctere - positionCaractereKey + alphabet.length) % alphabet.length;
    }

    private static int getCaractereChiffre(char caractere, char caractereKey) {
        int positionCararctere = getPositionDansAlphabet(caractere);
        int positionCaractereKey = getPositionDansAlphabet(caractereKey);

        return (positionCaractereKey + positionCararctere) % alphabet.length;
    }

    private static int getPositionDansAlphabet(char caractere) {
        int result = -1;
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == caractere) {
                result = i;
            }
        }
        return result;
    }
}
