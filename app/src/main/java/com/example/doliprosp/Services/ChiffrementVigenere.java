package com.example.doliprosp.Services;

import java.util.UUID;

public class ChiffrementVigenere {

    private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};

    /**
     * Clé de chiffrement pourles champs qui ne sont pas l'APIKEY
     */
    private static String key = UUID.randomUUID().toString();


    /**
     * chiffre le champ passé en paramètre avec la méthode de Vigenère
     * @param champAChiffre
     * @return le champ chiffré
     */
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

    /**
     * Chiffre la clé de l'API avec la méthode de Vigenère.
     * La clé de chiffrement est la concaténation des champs chiffrés nom, prénom, ville
     * @param cleAPI
     * @param cleChiffrage
     * @return la clé API chiffré
     */
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


    /**
     * Déchiffre la clé API avec la méthode de Vigenère
     * La clé de déchiffrement est la concaténation des champs chiffrés nom / prénom / ville
     * @param cleAPIChiffre la clé API chiffrée
     * @param cleChiffrement la clé de chiffrement pour déchiffrer l'API
     * @return la clé API déchiffré
     */
    public static String dechiffrementCleAPI(String cleAPIChiffre, String cleChiffrement) {
        int keyIndex = 0;
        String cleDechiifre = "";
        for (int i = 0; i < cleAPIChiffre.length(); i++) {
            char caractere = cleAPIChiffre.charAt(i);
            char caractereKey = cleChiffrement.charAt(keyIndex);

            int positionCaractereDeChiffre = getCaractereDeChiffre(caractere,
                    caractereKey);
            char caractereDeChiffre =
                    alphabet[positionCaractereDeChiffre];
            cleDechiifre += caractereDeChiffre;

            keyIndex = (keyIndex + 1) % cleChiffrement.length();

        }
        return cleDechiifre;

    }

    /**
     * Déchiffre le champ passé en paramètre
     * @param champChiffre le champ que l'on veut déchiffrer
     * @return le champ passé en paramètre déchiffré
     */
    public static String dechiffrement(String champChiffre) {
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

    /**
     * Récupère la position dans l'alphabet du caractère déchiffré
     * passé en premier paramètre
     * @param caractere le caractère à déchiffrer
     * @param caractereCle le caractère de la clé de chiffrement
     * @return la position dans l'alphabet du caractère déchiffré
     */
    private static int getCaractereDeChiffre(char caractere, char caractereCle) {

        int positionCararctere = getPositionDansAlphabet(caractere);
        int positionCaractereKey = getPositionDansAlphabet(caractereCle);
        return (positionCararctere - positionCaractereKey + alphabet.length) % alphabet.length;
    }


    /**
     * Récupère la position dans l'alphabet du caractère chiffré
     * passé en premier paramètre
     * @param caractere le caractère à chiffrer
     * @param caractereKey le caractère de la clé de chiffrement
     * @return la position dans l'alphabet du caractère chiffré
     */
    private static int getCaractereChiffre(char caractere, char caractereKey) {
        int positionCararctere = getPositionDansAlphabet(caractere);
        int positionCaractereKey = getPositionDansAlphabet(caractereKey);

        return (positionCaractereKey + positionCararctere) % alphabet.length;
    }

    /**
     * Récupère la position dans l'alphabet d'un caractère
     * @param caractere le caractère pour lequel
     *        on cherche sa position dans l'alphabet
     * @return la position dans l'alphabet du caractère passé en paramètre
     */
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
