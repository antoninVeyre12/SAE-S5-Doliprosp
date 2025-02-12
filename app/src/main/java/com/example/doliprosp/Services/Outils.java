package com.example.doliprosp.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Classe utilitaire pour effectuer des appels API avec la bibliothèque Volley.
 */
public class Outils {

    private static RequestQueue fileRequete;

    private static String cleApi;

    private UtilisateurViewModel utilisateurVueModele;

    /**
     * Effectue un appel API de connexion.
     *
     * @param url      L'URL de l'API.
     * @param context  Le contexte pour obtenir la file de requêtes.
     * @param callback Le callback à invoquer après la réponse.
     */
    public static void appelAPIConnexion(String url, Context context, APIResponseCallback callback) {
        ignorerSSLCertifcat();
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            JSONObject objectJSON = new JSONObject(reponse);
                            callback.onSuccess(objectJSON); // Notifie en cas de succès
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        Log.d("CONNEXION ECHOUE", "connexion échouée");
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                });
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    /**
     * Effectue un appel API GET avec un en-tête d'authentification.
     *
     * @param url      L'URL de l'API.
     * @param cleApi   La clé API utilisée pour l'authentification.
     * @param context  Le contexte pour obtenir la file de requêtes.
     * @param callback Le callback à invoquer après la réponse.
     */
    public static void appelAPIGet(String url, String cleApi, Context context, APIResponseCallback callback) {
        ignorerSSLCertifcat();
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        Log.d("onResponseAppelApiGETTT", reponse);
                        try {
                            JSONObject objectJSON = new JSONObject(reponse);
                            callback.onSuccess(objectJSON); // Notifie en cas de succès
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        Log.d("onErrorRecupID", erreur.toString());
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", cleApi);
                headers.put("Connection", "close");
                return headers;
            }
        };
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    /**
     * Effectue un appel API GET pour récupérer une liste de données.
     *
     * @param url      L'URL de l'API.
     * @param cleApi   La clé API pour l'authentification.
     * @param context  Le contexte pour obtenir la file de requêtes.
     * @param callback Le callback à invoquer après la réponse.
     */
    public static void appelAPIGetList(String url, String cleApi, Context context, APIResponseCallbackArray callback) {
        ignorerSSLCertifcat();
        StringRequest requeteVolley = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        try {
                            JSONArray arrayJSON = new JSONArray(reponse);
                            callback.onSuccess(arrayJSON); // Notifie en cas de succès
                        } catch (JSONException e) {
                            callback.onError("Erreur de parsing JSON : " + e.getMessage()); // Notifie l'erreur
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError erreur) {
                        callback.onError("Erreur de requête : " + erreur.getMessage()); // Notifie l'erreur
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", cleApi);
                headers.put("Connection", "close");
                return headers;
            }
        };
        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    public static void appelAPIPostInteger(String url, String cleApi, JSONObject jsonBody, Context context, APIResponseCallbackPost callback) {
        ignorerSSLCertifcat();
        StringRequest requeteVolley = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int responseInt = Integer.parseInt(response.trim()); // Convertir la réponse en entier
                            callback.onSuccess(responseInt); // Envoyer l'entier au callback
                        } catch (NumberFormatException e) {
                            callback.onError("Réponse inattendue (non-entier) : " + response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError("Erreur de requête : " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", cleApi);
                headers.put("Connection", "close");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }


    public static void appelAPIPostJson(String url, String cleApi, Context context, APIResponseCallback callback) {
        ignorerSSLCertifcat();
        StringRequest requeteVolley = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        JSONObject objectJSON = null;
                        try {
                            objectJSON = new JSONObject(reponse);
                            callback.onSuccess(objectJSON);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError("Erreur de requête : " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("DOLAPIKEY", cleApi);
                headers.put("Connection", "close");
                return headers;
            }

        };

        fileRequete = getRequestQueue(context);
        fileRequete.add(requeteVolley);
    }

    /**
     * Retourne une instance de la file de requêtes Volley.
     *
     * @param context Le contexte pour obtenir la file de requêtes.
     * @return La file de requêtes.
     */
    public static RequestQueue getRequestQueue(Context context) {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(context);
        }
        return fileRequete;
    }

    public static void ecrireDansFichierInterne(Context context, String nomFichier, String contenu) {
        try {
            // Ouvre le fichier en mode privé pour écrire dedans
            FileOutputStream fos = context.openFileOutput(nomFichier,
                    Context.MODE_PRIVATE);
            fos.write(contenu.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String lireFichierInterne(Context context,
                                            String fichierNom) {
        String contenu = "";
        try {
            // Ouvrir le fichier en mode lecture
            FileInputStream fis = context.openFileInput(fichierNom);
            int character;

            // Lire chaque caractère du fichier et l'ajouter à une chaîne
            while ((character = fis.read()) != -1) {
                contenu += (char) character;
            }

            // Fermer le flux une fois la lecture terminée
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenu;
    }

    public static boolean fichierValide(Context context, String nomFichier) {
        File file = new File(context.getFilesDir() + "/" + nomFichier);
        return file.exists();
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant un objet JSON.
     */
    public interface APIResponseCallback {
        void onSuccess(JSONObject response) throws JSONException;

        void onError(String errorMessage);
    }

    public interface APIResponseCallbackString {
        void onSuccess(String response) throws JSONException;

        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant un tableau JSON.
     */
    public interface APIResponseCallbackArray {
        void onSuccess(JSONArray response);

        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant un tableau JSON.
     */
    public interface APIResponseCallbackPost {
        void onSuccess(Integer response) throws JSONException;

        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant une liste de salons.
     */
    public interface APIResponseCallbackArrayTest {
        void onSuccess(ArrayList<Salon> response);

        void onError(String errorMessage);
    }

    /**
     * Interface de callback pour la gestion de la réponse d'un appel API renvoyant une liste de prospects.
     */
    public interface APIResponseCallbackArrayProspect {
        void onSuccess(ArrayList<Prospect> response);

        void onError(String errorMessage);
    }

    public interface CallbackProspectExiste {
        void onResponse();

        void onError();
    }


    /**
     * Cette méthode ignore les vérifications du certificat SSL
     */
    private static void ignorerSSLCertifcat() {
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Installer un gestionnaire de confiance pour tous les certificats
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCertificates, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}