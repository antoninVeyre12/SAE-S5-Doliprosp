package com.example.doliprosp.Services;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.Interface.IProjetService;
import com.example.doliprosp.Modele.Projet;
import com.example.doliprosp.Modele.Prospect;
import com.example.doliprosp.Modele.Salon;
import com.example.doliprosp.Modele.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.doliprosp.Services.Outils.appelAPIPostInteger;

public class ProjetService implements IProjetService {

    private String url;
    private String urlAppel;

    public void updateProjet(Projet projet, String titre, String description, String dateDebut, long dateTimestamp) {
        projet.setTitre(titre);
        projet.setDescription(description);
        projet.setDateDebut(dateDebut);
        projet.setDateTimestamp(dateTimestamp);
    }

    @Override
    public ArrayList<Projet> getProjetDUnProspect(ArrayList<Projet> projetListe, String nomProspect) {

        ArrayList<Projet> projetDuProspect = new ArrayList<>();
        for (Projet projet : projetListe) {
            if (projet.getNomProspect().equals(nomProspect)) {
                projetDuProspect.add(projet);
            }
        }
        return projetDuProspect;
    }

    public void envoyerProjet(Utilisateur utilisateur, Context context, Projet projetAEnvoyer, int idProspect) {
        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/projects";
        String apikey = utilisateur.getCleApi();
        JSONObject jsonBody = creationJsonProjet(projetAEnvoyer, idProspect);
        Log.d("jsonBody", jsonBody.toString());

        appelAPIPostInteger(urlAppel, utilisateur.getCleApi(), jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) {
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("onerror", errorMessage.toString());
            }
        });
    }

    private JSONObject creationJsonProjet(Projet projet, int idProspect) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("description", projet.getDescription());
            jsonBody.put("title", projet.getTitre());
            jsonBody.put("date_start", projet.getDateTimestamp());
            jsonBody.put("socid", idProspect);
            jsonBody.put("ref",
                    projet.getTitre() + '-' + projet.getNomProspect());
            jsonBody.put("statut", 1);
            jsonBody.put("status", 1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonBody;
    }

    public void envoyerVersModule(Utilisateur utilisateur, Context context,
                                  Projet projetAEnvoyer,
                                  Prospect prospectAEnvoyer,
                                  Salon salonAEnvoyer, int idProspect) {

        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/doliprospapi/salons";

        String apikey = utilisateur.getCleApi();
        JSONObject jsonBody = creationJsonModule(utilisateur, projetAEnvoyer,
                prospectAEnvoyer, salonAEnvoyer, idProspect);
        Log.d("jsonBOdy", jsonBody.toString());
        appelAPIPostInteger(urlAppel, utilisateur.getCleApi(), jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) {
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("onerror", errorMessage.toString());
            }
        });

    }

    private JSONObject creationJsonModule(Utilisateur utilisateur, Projet projet,
                                          Prospect prospect,
                                          Salon salon, int idProspect) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ref",
                    salon.getNom() + '-' + projet.getTitre());
            jsonBody.put("refProjet",
                    projet.getTitre() + '-' + projet.getNomProspect());
            jsonBody.put("description", projet.getDescription());
            jsonBody.put("titreProjet", projet.getTitre());
            jsonBody.put("date_start", projet.getDateTimestamp());
            jsonBody.put("IDProspectClient", idProspect);
            jsonBody.put("NomProspectClient", prospect.getNom());
            jsonBody.put("adressePostale", prospect.getAdresse());
            jsonBody.put("CodePostal", prospect.getCodePostal());
            jsonBody.put("Ville", prospect.getVille());
            jsonBody.put("noTel", prospect.getNumeroTelephone());
            jsonBody.put("email", prospect.getMail());
            jsonBody.put("ClientProspect", "0");
            jsonBody.put("DateDebutProjet", projet.getDateTimestamp());
            jsonBody.put("Commercial", utilisateur.getNomUtilisateur());
            jsonBody.put("miseAjour", "1");
            jsonBody.put("intituleSalon", salon.getNom());
            jsonBody.put("status", 1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonBody;
    }

}