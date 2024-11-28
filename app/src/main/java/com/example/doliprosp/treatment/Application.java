package com.example.doliprosp.treatment;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Application implements IApplication {
    private ArrayList<Prospect> listProspect;
    private ArrayList<Project> listProject;
    private ArrayList<Show> listLocalShow;
    private ArrayList<Show> listSavedShow;
    private RequestQueue fileRequete;

    public Application() {
        this.listProject = new ArrayList<Project>();
        this.listProspect = new ArrayList<Prospect>();
        this.listLocalShow = new ArrayList<Show>();
        this.listSavedShow = new ArrayList<Show>();

    }
    private User commercial;

    public void setUser(User newUser)
    {
        commercial = newUser;
    }

    @Override
    public User getUser() {
        return commercial;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(context);
        }
        return fileRequete;
    }

    public void addProspect(Prospect prospect)
    {
        listProspect.add(prospect);
    }

    public void addProject(Project project)
    {
        listProject.add(project);
    }

    public void addLocalShow(Show localShow)
    {
        listLocalShow.add(localShow);
    }

    public ArrayList<Show> getLocalShow()
    {
        return listLocalShow;
    }
    public void deleteProspect(Prospect prospect)
    {
        listProspect.remove(prospect);
    }

    public void deleteProject(Project project)
    {
        listProject.remove(project);
    }

    public void deleteLocalShow(Show show)
    {
        listLocalShow.remove(show);
        // SUpprimer les prospects et projets du salon
    }

    public ArrayList<Show> getSavedShow()
    {

        ArrayList<Show> listSavedShow = new ArrayList<>();
        listSavedShow.add(new Show("Testttt"));
        listSavedShow.add(new Show("testppp"));
        listSavedShow.add(new Show("Testttt"));
        listSavedShow.add(new Show("testppp"));
        /*TODO récupérer la liste des salons existnats dans l'erp et les ajouter a ListSavedShow*/
        return listSavedShow;
    }

    public void sendProspect(Prospect prospect)
    {
        /*TODO vérifier si le prospect existe*/
        /* Remonter un champs mise A jour (true, false) suivant son existence*/
        /*TODO vérifier les champs*/
        /*TODO envoyer un prospect via API*/
    }

    public void sendProject(Project project)
    {
        /*TODO vérifier les champs*/
        /*TODO envoyer projet */
    }

    public void sendShow(Show show)
    {
        /*TODO vérifier si le salon existe*/
        /* Remonter un champs mise A jour (true, false) suivant son existence*/
        /*TODO vérifier les champs*/
        /*TODO envoyer le salon via API*/
    }

    public Prospect getSavedProsepct(String newFirstName, String newLastName, int newPostCode, String newCity,
                                     String newPostalAddress, String newEmail, String newPhoneNumber)
    {
        /*Le but de cette méthode est de retoruner un prospect sauvegardé à l'iade de un ou plusieurs critères*/
        /* TODO transformer le json renvoyé par l'API en objet Prospect*/
        return null;
    }


}
