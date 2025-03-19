package com.example.doliprosp.services;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.interfaces.IProspectService;
import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.modeles.Utilisateur;
import com.example.doliprosp.viewmodels.MesProspectViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProspectService implements IProspectService {
    private String url;
    private String urlAppel;
    private static final String SEPARATEUR_OR = "%20or%20";
    private static final String CHAMP_LIKE = "%3Alike%3A";
    private static final String OUVERTURE_LIKE = "'%25";
    private static final String FERMETURE_LIKE = "%25'";

    public void envoyerProspect(Utilisateur utilisateur, Context context, Prospect prospectAEnvoyer, int idSalon, Outils.APIResponseCallbackString callback) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties";
        JSONObject jsonBody = creationJsonProspect(prospectAEnvoyer);
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());

        Outils.appelAPIPostInteger(urlAppel, apiKeyDechiffre, jsonBody, context, new Outils.APIResponseCallbackPost() {

            @Override
            public void onSuccess(Integer response) throws JSONException {
                lieProspectSalon(utilisateur, context, idSalon, response);
                callback.onSuccess(String.valueOf(response));
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("onerror", errorMessage);
            }
        });
    }

    public void lieProspectSalon(Utilisateur utilisateur, Context context,
                                 int idSalon, int response) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties/" + response + "/categories/" + idSalon;
        // Récupération de l'ID Prospect
        // Appel du callback pour transmettre l'ID Prospect
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());

        Outils.appelAPIPostJson(urlAppel, apiKeyDechiffre, context, new Outils.APIResponseCallback() {

            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                Log.d("sucessss envoyer prospect", response.toString());
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    private JSONObject creationJsonProspect(Prospect prospect) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", prospect.getNom());
            jsonBody.put("address", prospect.getAdresse());
            jsonBody.put("zip", prospect.getCodePostal());
            jsonBody.put("town", prospect.getVille());
            jsonBody.put("phone", prospect.getNumeroTelephone());
            jsonBody.put("email", prospect.getMail());
            jsonBody.put("client", prospect.getEstClient());
        } catch (JSONException e) {
            Log.d("json exception", e.toString());
        }
        return jsonBody;
    }

    /**
     * Filtre les prospects en fonction du nom du salon auquel ils sont associés.
     *
     * @param prospectListe La liste des prospects à filtrer.
     * @param nomSalon      Le nom du salon pour lequel nous voulons filtrer les prospects.
     * @return Une liste de prospects associés au salon donné.
     */
    public ArrayList<Prospect> getProspectDUnSalon(ArrayList<Prospect> prospectListe, String nomSalon) {

        ArrayList<Prospect> prospectsduSalon = new ArrayList<>();

        for (Prospect prospect : prospectListe) {
            if (prospect.getNomSalon().equals(nomSalon)) {
                prospectsduSalon.add(prospect);
            }
        }

        return prospectsduSalon;
    }

    /**
     * Recherche des prospects dans le système en fonction de la recherche et du tri.
     *
     * @param context     Le contexte de l'application utilisé pour l'appel API.
     * @param recherche   La valeur de recherche pour filtrer les prospects (nom, email, téléphone, etc.).
     * @param tri         Le critère de tri des résultats.
     * @param utilisateur L'utilisateur connecté, contenant l'URL et la clé API nécessaires.
     * @param callback    Le callback à appeler une fois la réponse reçue de l'API.
     */
    public void prospectClientExiste(Context context, String recherche, String tri, Utilisateur utilisateur, Outils.APIResponseCallbackArrayProspect callback) {
        ArrayList<Prospect> listeProspectCorrespondant = new ArrayList<>();
        url = utilisateur.getUrl();
        String sqlfilters = creerSqlfilter(recherche);
        urlAppel = url + "/api/index.php/thirdparties?sortfield=t." + tri + "&sortorder=ASC&limit=6&sqlfilters=" + sqlfilters;
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());

        Outils.appelAPIGetList(urlAppel, apiKeyDechiffre, context, new Outils.APIResponseCallbackArray() {

            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nomSalon = "";
                        String nom = object.getString("name");
                        String codePostal = object.getString("zip");
                        String ville = object.getString("town");
                        String adressePostale = object.getString("address");
                        String mail = object.getString("email");
                        String numeroTelephone = object.getString("phone");
                        String estClient = object.getString(
                                "client");
                        String idDolibarr = object.getString(
                                "id");
                        listeProspectCorrespondant.add(new Prospect(nomSalon, nom, codePostal,
                                ville, adressePostale, mail, numeroTelephone,
                                estClient, idDolibarr, 0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(listeProspectCorrespondant);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError("client deja existant");
            }
        });
    }


    public void prospectDejaExistantDolibarr(Context context, String recherche, Utilisateur utilisateur,
                                             MesProspectViewModel mesProspectViewModel, Outils.CallbackProspectExiste callback) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties?sortfield=t.rowid&sortorder=DESC&limit=6&sqlfilters=(t.phone%3Alike%3A'" + recherche + "')";
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());

        Outils.appelAPIGetList(urlAppel, apiKeyDechiffre, context, new Outils.APIResponseCallbackArray() {
            @Override
            public void onSuccess(JSONArray response) {
                callback.onResponse();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError();

            }
        });

    }

    public void modifierClient(Context context, Utilisateur utilisateur,
                               Prospect prospectAModifier, String idProspect) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties/" + idProspect;
        JSONObject jsonBody = creationJsonProspect(prospectAModifier);

        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());

        Outils.appelAPIPut(urlAppel, apiKeyDechiffre, context, jsonBody,
                new Outils.APIResponseCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });

    }

    public boolean existeDansViewModel(String recherche, MesProspectViewModel mesProspectViewModel) {
        boolean existe = false;

        for (Prospect prospect : mesProspectViewModel.getProspectListe()) {
            // Vérification si le numéro de téléphone du prospect correspond à celui passé en paramètre
            if (prospect.getNumeroTelephone().equals(recherche)) {
                existe = true;
            }
        }
        return existe;
    }


    /**
     * Crée un filtre SQL pour rechercher un prospect en fonction d'une valeur donnée.
     *
     * @param valeur La valeur de recherche (nom, email, téléphone, etc.).
     * @return La chaîne de filtre SQL générée.
     */
    private String creerSqlfilter(String valeur) {
        return creerChercheChamp("nom", valeur)
                + SEPARATEUR_OR + creerChercheChamp("email", valeur)
                + SEPARATEUR_OR + creerChercheChamp("phone", valeur) + SEPARATEUR_OR + creerChercheChamp("address", valeur)
                + SEPARATEUR_OR + creerChercheChamp("zip", valeur) + SEPARATEUR_OR + creerChercheChamp("town", valeur);

    }

    /**
     * Crée une condition de recherche pour un attribut donné avec une valeur spécifique.
     *
     * @param champ  Le nom du champ de la base de données.
     * @param valeur La valeur à rechercher dans ce champ.
     * @return La condition SQL formatée pour ce champ et cette valeur.
     */
    private String creerChercheChamp(String champ, String valeur) {
        return "(t." + champ + CHAMP_LIKE + OUVERTURE_LIKE + valeur + FERMETURE_LIKE + ")";
    }
}