package com.example.doliprosp.services;

/**
 * Classe implémentant le chiffrement et le déchiffrement de Vigenère
 * en utilisant un alphabet personnalisé.
 */
public class ChiffrementVigenere {

    private static char[] alphabet = {'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G',
            'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q',
            'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '-'};

    private ChiffrementVigenere() {
    }

    /**
     * Chiffre une chaîne de caractères en utilisant la clé de chiffrement
     *
     * @param champAChiffre Texte à chiffrer
     * @param key           Clé de chiffrement
     * @return Texte chiffré
     */
    public static String chiffrement(String champAChiffre, String key) {
        int keyIndex = 0;
        StringBuilder cleChiffre = new StringBuilder();
        for (int i = 0; i < champAChiffre.length(); i++) {
            char caractere = champAChiffre.charAt(i);
            char caractereKey = key.charAt(keyIndex);

            int positionCaractereChiffre = getCaractereChiffre(caractere,
                    caractereKey);
            char caractereChiffre = alphabet[positionCaractereChiffre];
            cleChiffre.append(caractereChiffre);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return cleChiffre.toString();
    }

    /**
     * Chiffre une clé API en utilisant une clé de chiffrement
     *
     * @param cleAPI       Clé API à chiffrer
     * @param cleChiffrage Clé de chiffrement
     * @return Clé API chiffrée
     */
    public static String chiffrementCleAPI(String cleAPI, String cleChiffrage) {
        int keyIndex = 0;
        StringBuilder cleChiffre = new StringBuilder();
        for (int i = 0; i < cleAPI.length(); i++) {
            char caractere = cleAPI.charAt(i);
            char caractereKey = cleChiffrage.charAt(keyIndex);

            int positionCaractereChiffre = getCaractereChiffre(caractere,
                    caractereKey);
            char caractereChiffre = alphabet[positionCaractereChiffre];
            cleChiffre.append(caractereChiffre);
            keyIndex = (keyIndex + 1) % cleChiffrage.length();
        }
        return cleChiffre.toString();
    }

    /**
     * Déchiffre une clé API en utilisant une clé de déchiffrement
     *
     * @param cleAPIChiffre  Clé API chiffrée
     * @param cleDechiffrage Clé de déchiffrement
     * @return Clé API déchiffrée
     */
    public static String dechiffrementCleAPI(String cleAPIChiffre, String cleDechiffrage) {
        int keyIndex = 0;
        StringBuilder cleDechiifre = new StringBuilder();
        for (int i = 0; i < cleAPIChiffre.length(); i++) {
            char caractere = cleAPIChiffre.charAt(i);
            char caractereKey = cleDechiffrage.charAt(keyIndex);

            int positionCaractereDeChiffre = getCaractereDeChiffre(caractere,
                    caractereKey);
            char caractereDeChiffre =
                    alphabet[positionCaractereDeChiffre];
            cleDechiifre.append(caractereDeChiffre);

            keyIndex = (keyIndex + 1) % cleDechiffrage.length();

        }
        return cleDechiifre.toString();

    }

    /**
     * Déchiffre un texte chiffré avec une clé
     *
     * @param champChiffre Texte chiffré
     * @param key          Clé de déchiffrement
     * @return Texte déchiffré
     */
    public static String dechiffrement(String champChiffre, String key) {
        int keyIndex = 0;
        StringBuilder cleDechiifre = new StringBuilder();
        for (int i = 0; i < champChiffre.length(); i++) {
            char caractere = champChiffre.charAt(i);
            char caractereKey = key.charAt(keyIndex);

            int positionCaractereDeChiffre = getCaractereDeChiffre(caractere,
                    caractereKey);
            char caractereDeChiffre =
                    alphabet[positionCaractereDeChiffre];
            cleDechiifre.append(caractereDeChiffre);

            keyIndex = (keyIndex + 1) % key.length();

        }
        return cleDechiifre.toString();

    }

    /**
     * Calcule la position du caractère déchiffré dans l'alphabet
     *
     * @param caractere    Caractère chiffré
     * @param caractereKey Caractère de la clé
     * @return Position du caractère déchiffré
     */
    private static int getCaractereDeChiffre(char caractere, char caractereKey) {

        int positionCararctere = getPositionDansAlphabet(caractere);
        int positionCaractereKey = getPositionDansAlphabet(caractereKey);
        return (positionCararctere - positionCaractereKey + alphabet.length) % alphabet.length;
    }

    /**
     * Calcule la position du caractère chiffré dans l'alphabet
     *
     * @param caractere    Caractère en clair
     * @param caractereKey Caractère de la clé
     * @return Position du caractère chiffré
     */
    private static int getCaractereChiffre(char caractere, char caractereKey) {
        int positionCararctere = getPositionDansAlphabet(caractere);
        int positionCaractereKey = getPositionDansAlphabet(caractereKey);

        return (positionCaractereKey + positionCararctere) % alphabet.length;
    }

    /**
     * Trouve la position d'un caractère dans l'alphabet
     *
     * @param caractere Caractère à rechercher
     * @return Position du caractère dans l'alphabet ou -1 s'il n'est pas trouvé
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
