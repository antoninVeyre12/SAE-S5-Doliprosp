package com.example.doliprosp.Services;

import android.content.Context;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;

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


    public void prospectClientExiste(Context context, String recherche, String champ, String tri, Utilisateur utilisateur, Outils.APIResponseCallbackArrayProspect callback) {
        ArrayList<Prospect> listeProspectCorrespondant = new ArrayList<Prospect>();
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/categories?sortfield=t." + tri + "&sortorder=DESC&limit=6&sqlfilters=(t." + champ + "%3Alike%3A'%25" + recherche +"%25')";
        Outils.appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {

            @Override
            public void onSuccess(JSONArray response) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        }); 
    }

    public void updateProspect(String prenom, String nom, int codePostal, String ville,
                               String adresse, String mail, String numeroTelephone,
                               Boolean estClient, UUID idProspect)
    {

    }


}