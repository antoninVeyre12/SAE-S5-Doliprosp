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

public class ProspectService implements IProspectService {
    private String url;
    private String urlAppel;

    public void ajouterProspect(Prospect prospect) {

    }

    public void supprimerProspect(Prospect prospect) {

    }

    public ArrayList<Prospect> getProspectDUnSalon(ArrayList<Prospect> prospectListe, String nomSalon) {

        ArrayList<Prospect> prospectsduSalon = new ArrayList<>();

        for (Prospect prospect : prospectListe) {
            if (prospect.getNomSalon().equals(nomSalon)) {
                prospectsduSalon.add(prospect);
            }
        }

        return prospectsduSalon;
    }

    public void prospectClientExiste(Context context, String recherche, String champ, String tri, Utilisateur utilisateur, Outils.APIResponseCallbackArrayProspect callback) {
        ArrayList<Prospect> listeProspectCorrespondant = new ArrayList<Prospect>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t." + tri + "&sortorder=DESC&limit=6&sqlfilters=(t." + champ + "%3Alike%3A'%25" + recherche + "%25')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {

            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nomSalon = "";
                        String nom = object.getString("name");
                        String codePostal = object.getString("zip");
                        String ville = object.getString("town");
                        String adressePostale = object.getString("adress");
                        String mail = object.getString("email");
                        String numeroTelephone = object.getString("phone");
                        String estClient = object.getString("client");
                        String image = object.getString("logo");
                        listeProspectCorrespondant.add(new Prospect(nomSalon, nom, codePostal,
                                ville, adressePostale, mail, numeroTelephone,
                                estClient, image));
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
        ArrayList<Prospect> listeProspectCorrespondant = new ArrayList<Prospect>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties?sortfield=t.rowid&sortorder=DESC&limit=6&sqlfilters=(t.phone%3Alike%3A'" + recherche + "')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {
            @Override
            public void onSuccess(JSONArray response) {
                callback.onResponse(true);

            }

            @Override
            public void onError(String errorMessage) {
                boolean existe = existeDansViewModel(recherche, mesProspectViewModel);

                callback.onResponse(existe);


            }
        });

    }


    private boolean existeDansViewModel(String recherche, MesProspectViewModel mesProspectViewModel) {
        boolean existe = false;

        for (Prospect prospect : mesProspectViewModel.getProspectListe()) {
            // Vérification si le numéro de téléphone du prospect correspond à celui passé en paramètre
            if (prospect.getNumeroTelephone().equals(recherche)) {
                existe = true;
            }
        }
        return existe;
    }


    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String mail, String numeroTelephone,
                               Boolean estClient, UUID idProspect) {
    }


}