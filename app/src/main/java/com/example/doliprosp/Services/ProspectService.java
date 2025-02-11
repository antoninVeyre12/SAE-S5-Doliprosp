package com.example.doliprosp.Services;

import android.content.Context;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Utilisateur;
import com.example.doliprosp.viewModel.MesProspectViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Service permettant de gérer les prospects.
 */
public class ProspectService implements IProspectService {
    private String url;
    private String urlAppel;

    /**
     * Récupère la liste des prospects appartenant à un salon donné.
     *
     * @param prospectListe Liste de tous les prospects.
     * @param nomSalon      Nom du salon à filtrer.
     * @return Liste des prospects correspondant au salon donné.
     */
    public ArrayList<Prospect> getProspectDUnSalon(ArrayList<Prospect> prospectListe, String nomSalon) {
        ArrayList<Prospect> prospectsDuSalon = new ArrayList<>();
        for (Prospect prospect : prospectListe) {
            if (prospect.getNomSalon().equals(nomSalon)) {
                prospectsDuSalon.add(prospect);
            }
        }
        return prospectsDuSalon;
    }

    /**
     * Vérifie si un prospect correspondant à une recherche existe déjà.
     *
     * @param context     Contexte de l'application.
     * @param recherche   Terme de recherche.
     * @param champ       Champ sur lequel appliquer le filtre.
     * @param tri         Critère de tri.
     * @param utilisateur Utilisateur courant.
     * @param callback    Callback pour retourner le résultat.
     */
    public void prospectClientExiste(Context context, String recherche,
                                     String champ, String tri,
                                     Utilisateur utilisateur,
                                     Outils.APIResponseCallbackArrayProspect callback) {
        ArrayList<Prospect> listeProspectCorrespondant = new ArrayList<>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t." + tri +
                "&sortorder=DESC&limit=6&sqlfilters=(t." + champ + "%3Alike" +
                "%3A'%25" + recherche + "%25')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context,
                new Outils.APIResponseCallbackArray() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String nomSalon = "";
                                String nom = object.getString("name");
                                String codePostal = object.getString("zip");
                                String ville = object.getString("town");
                                String adressePostale = object.getString(
                                        "adress");
                                String mail = object.getString("email");
                                String numeroTelephone = object.getString(
                                        "phone");
                                String estClient = object.getString("client");
                                String image = object.getString("logo");
                                listeProspectCorrespondant.add(new Prospect(nomSalon,
                                        nom, codePostal, ville,
                                        adressePostale, mail,
                                        numeroTelephone, estClient, image));
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

    /**
     * Vérifie si un prospect existe déjà dans Dolibarr.
     *
     * @param context              Contexte de l'application.
     * @param recherche            Numéro de téléphone du prospect recherché.
     * @param utilisateur          Utilisateur courant.
     * @param mesProspectViewModel ViewModel contenant la liste des prospects.
     * @param callback             Callback retournant si le prospect existe
     *                             ou non.
     */
    public void prospectDejaExistantDolibarr(Context context,
                                             String recherche,
                                             Utilisateur utilisateur,
                                             MesProspectViewModel mesProspectViewModel, Outils.CallbackProspectExiste callback) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties?sortfield=t" +
                ".rowid&sortorder=DESC&limit=6&sqlfilters=(t" +
                ".phone%3Alike%3A'" + recherche + "')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context,
                new Outils.APIResponseCallbackArray() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        callback.onResponse(true);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        boolean existe = existeDansViewModel(recherche,
                                mesProspectViewModel);
                        callback.onResponse(existe);
                    }
                });
    }

    /**
     * Vérifie si un prospect existe déjà dans le ViewModel.
     *
     * @param recherche            Numéro de téléphone du prospect recherché.
     * @param mesProspectViewModel ViewModel contenant la liste des prospects.
     * @return True si le prospect existe, False sinon.
     */
    private boolean existeDansViewModel(String recherche,
                                        MesProspectViewModel mesProspectViewModel) {
        for (Prospect prospect : mesProspectViewModel.getProspectListe()) {
            if (prospect.getNumeroTelephone().equals(recherche)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Met à jour les informations d'un prospect.
     *
     * @param prenom          Prénom du prospect.
     * @param nom             Nom du prospect.
     * @param codePostal      Code postal du prospect.
     * @param ville           Ville du prospect.
     * @param adresse         Adresse postale du prospect.
     * @param mail            Adresse email du prospect.
     * @param numeroTelephone Numéro de téléphone du prospect.
     * @param estClient       Statut du prospect (client ou non).
     * @param idProspect      Identifiant unique du prospect.
     */
    public void updateProspect(String prenom, String nom, int codePostal,
                               String ville, String adresse, String mail,
                               String numeroTelephone, Boolean estClient,
                               UUID idProspect) {
        // TODO : Mettre à jour les informations d'un prospect localement
    }
}
