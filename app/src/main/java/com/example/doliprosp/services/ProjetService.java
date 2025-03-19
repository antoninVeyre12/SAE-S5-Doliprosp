package com.example.doliprosp.services;

import android.content.Context;
import android.util.Log;

import com.example.doliprosp.interfaces.IProjetService;
import com.example.doliprosp.modeles.Projet;
import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.modeles.Salon;
import com.example.doliprosp.modeles.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.doliprosp.services.Outils.appelAPIPostInteger;

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
        JSONObject jsonBody = creationJsonProjet(projetAEnvoyer, idProspect);
        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());


        appelAPIPostInteger(urlAppel, apiKeyDechiffre, jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) {
                Log.d("succes", response.toString());
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("onerror", errorMessage);
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
        } catch (JSONException e) {
            Log.d("jsonException", e.toString());
        }
        return jsonBody;
    }

    public void envoyerVersModule(Utilisateur utilisateur, Context context,
                                  Projet projetAEnvoyer,
                                  Prospect prospectAEnvoyer,
                                  Salon salonAEnvoyer, int idProspect) {

        url = utilisateur.getUrl();
        urlAppel = url + "/api/index.php/doliprospapi/salons";
        JSONObject jsonBody = null;
        if (projetAEnvoyer == null) {
            jsonBody = creationJsonModuleSansProjet(utilisateur,
                    prospectAEnvoyer, salonAEnvoyer, idProspect);
        } else {
            jsonBody = creationJsonModule(utilisateur, projetAEnvoyer,
                    prospectAEnvoyer, salonAEnvoyer, idProspect);
        }

        String apiKeyDechiffre = ChiffrementVigenere.dechiffrementCleAPI(utilisateur.getCleApi(), utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.getVille());
        appelAPIPostInteger(urlAppel, apiKeyDechiffre, jsonBody, context, new Outils.APIResponseCallbackPost() {
            @Override
            public void onSuccess(Integer response) {
                Log.d("on success", response.toString());
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("one error", errorMessage);
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
            jsonBody.put("IDProspectClient", idProspect);
            jsonBody.put("nomProspectClient", prospect.getNom());
            jsonBody.put("adressePostale", prospect.getAdresse());
            jsonBody.put("codePostal", prospect.getCodePostal());
            jsonBody.put("ville", prospect.getVille());
            jsonBody.put("noTel", prospect.getNumeroTelephone());
            jsonBody.put("email", prospect.getMail());
            jsonBody.put("clientProspect", "0");
            jsonBody.put("dateDebutProjet", projet.getDateTimestamp());
            jsonBody.put("dateSaisie", prospect.getHeureSaisieTimestamp());
            jsonBody.put("commercial", utilisateur.getNomUtilisateur());
            jsonBody.put("miseAJour", false);
            jsonBody.put("intituleSalon", salon.getNom());
            jsonBody.put("status", 1);
        } catch (JSONException e) {
            Log.d("json exception", e.toString());
        }
        return jsonBody;
    }

    /**
     * Crée un objet JSON représentant un projet sans projet associé pour un prospect donné.
     * Cette méthode génère un objet `JSONObject` contenant des informations sur le prospect, le salon et l'utilisateur actuel.
     * Ces informations sont utilisées pour créer une référence de projet, un identifiant pour le prospect, des informations
     * de contact, etc., dans un format JSON. Cela permet de préparer un envoi ou une gestion de données via une API ou
     * une autre couche de communication.
     * @param utilisateur L'utilisateur actuel qui est en charge de la création du projet.
     * @param prospect L'objet représentant le prospect pour lequel le projet est créé.
     * @param salon L'objet représentant le salon où le projet est censé être créé.
     * @param idProspect L'ID du prospect qui est associé au projet.
     * @return JSONObject L'objet JSON contenant les informations formatées du projet sans projet associé.
     */
    private JSONObject creationJsonModuleSansProjet(Utilisateur utilisateur,
                                                    Prospect prospect,
                                                    Salon salon, int idProspect) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ref",
                    salon.getNom() + "-Non Envoyé");
            jsonBody.put("refProjet",
                    "Non Envoyé");
            jsonBody.put("description", "");
            jsonBody.put("titreProjet", "");
            jsonBody.put("IDProspectClient", idProspect);
            jsonBody.put("nomProspectClient", prospect.getNom());
            jsonBody.put("adressePostale", prospect.getAdresse());
            jsonBody.put("codePostal", prospect.getCodePostal());
            jsonBody.put("ville", prospect.getVille());
            jsonBody.put("noTel", prospect.getNumeroTelephone());
            jsonBody.put("email", prospect.getMail());
            jsonBody.put("clientProspect", "0");
            jsonBody.put("dateDebutProjet", "");
            jsonBody.put("dateSaisie", prospect.getHeureSaisieTimestamp());
            jsonBody.put("commercial", utilisateur.getNomUtilisateur());
            jsonBody.put("miseAJour", true);
            jsonBody.put("intituleSalon", salon.getNom());
            jsonBody.put("status", 1);
        } catch (JSONException e) {
            Log.d("JSON error", e.toString());
        }
        return jsonBody;
    }

}