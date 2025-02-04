package com.example.doliprosp.Services;

import static com.example.doliprosp.Services.Outils.appelAPIGetList;
import static com.example.doliprosp.Services.Outils.appelAPIPostInteger;
import static com.example.doliprosp.Services.Outils.appelAPIPostJson;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.Interface.IProspectService;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProspectService implements IProspectService {
    private String url;
    private String urlAppel;

    public void envoyerProspect(Utilisateur utilisateur, Context context, List<Prospect> prospectListe, int idSalon) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/thirdparties";
        String apikey = utilisateur.getCleApi();
        JSONObject jsonBody = creationJsonProspect(prospectListe);
        Log.d("jsonBody",jsonBody.toString());
        Log.d("apikey",apikey);

        appelAPIPostInteger(urlAppel, utilisateur.getCleApi(),jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) {
                urlAppel = url + "/api/index.php/thirdparties/"+response+"/categories/"+idSalon ;
                appelAPIPostJson(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallback() {

                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        Log.d("sucesssss",response.toString());
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.d("errrrorrrrr",errorMessage);

                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("onerror",errorMessage.toString());
            }
        });
    }

    private JSONObject creationJsonProspect(List<Prospect> prospectListe){
        JSONObject jsonBody = new JSONObject();
        try {
            for (Prospect prospect : prospectListe) {
                jsonBody.put("name",prospect.getNom());
                jsonBody.put("address", prospect.getAdresse());
                jsonBody.put("zip",prospect.getCodePostal());
                jsonBody.put("phone",prospect.getNumeroTelephone());
                jsonBody.put("email",prospect.getMail());
                jsonBody.put("client",2);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonBody;
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
        urlAppel = url + "/api/index.php/categories?sortfield=t." + tri + "&sortorder=DESC&limit=6&sqlfilters=(t." + champ + "%3Alike%3A'%25" + recherche +"%25')";
        appelAPIGetList(urlAppel, utilisateur.getCleApi(), context, new Outils.APIResponseCallbackArray() {

            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nomSalon = "";
                        String nom = object.getString("name");
                        int codePostal = object.getInt("zip");
                        String ville = object.getString("town");
                        String adressePostale = object.getString("adress");
                        String mail = object.getString("label");
                        String numeroTelephone = object.getString("label");
                        String estClient = object.getString("label");
                        String image = object.getString("label");
                        listeProspectCorrespondant.add(new Prospect(nomSalon, nom, codePostal,
                         ville,  adressePostale,  mail,  numeroTelephone,
                                 estClient,  image));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(listeProspectCorrespondant);
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