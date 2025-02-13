package com.example.doliprosp.services;

import android.util.Log;

public class ChiffrementVigenere {

    private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};

    public static String key = "3f45d694-c1cf-4115-b0c5-132dc91956bd";


    public static String chiffrement(String champAChiffre) {
        int keyIndex = 0;
        String cleChiffre = "";
        for (int i = 0; i < champAChiffre.length(); i++) {
            char caractere = champAChiffre.charAt(i);
            char caractereKey = key.charAt(keyIndex);

            int positionCaractereChiffre = getCaractereChiffre(caractere,
                    caractereKey);
            char caractereChiffre = alphabet[positionCaractereChiffre];
            cleChiffre += caractereChiffre;
            keyIndex = (keyIndex + 1) % key.length();
        }
        return cleChiffre;
    }

    public static String chiffrementCleAPI(String cleAPI, String cleChiffrage) {
        int keyIndex = 0;
        String cleChiffre = "";
        for (int i = 0; i < cleAPI.length(); i++) {
            char caractere = cleAPI.charAt(i);
            char caractereKey = cleChiffrage.charAt(keyIndex);

            int positionCaractereChiffre = getCaractereChiffre(caractere,
                    caractereKey);
            char caractereChiffre = alphabet[positionCaractereChiffre];
            cleChiffre += caractereChiffre;
            keyIndex = (keyIndex + 1) % cleChiffrage.length();
        }
        return cleChiffre;
    }


    public static String dechiffrementCleAPI(String cleAPIChiffre, String cleDechiffrage) {
        int keyIndex = 0;
        String cleDechiifre = "";
        for (int i = 0; i < cleAPIChiffre.length(); i++) {
            char caractere = cleAPIChiffre.charAt(i);
            char caractereKey = cleDechiffrage.charAt(keyIndex);

            int positionCaractereDeChiffre = getCaractereDeChiffre(caractere,
                    caractereKey);
            char caractereDeChiffre =
                    alphabet[positionCaractereDeChiffre];
            cleDechiifre += caractereDeChiffre;

            keyIndex = (keyIndex + 1) % cleDechiffrage.length();

        }
        return cleDechiifre;

    }

    public static String dechiffrement(String champChiffre) {
        Log.d("kryyyy", key);
        int keyIndex = 0;
        String cleDechiifre = "";
        for (int i = 0; i < champChiffre.length(); i++) {
            char caractere = champChiffre.charAt(i);
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
