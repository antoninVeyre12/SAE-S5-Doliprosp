package com.example.doliprosp.treatment;

import java.util.List;

public class Application implements IApplication {
    private List<Prospect> listProspect;
    private List<Project> listProject;
    private List<Show> listLocalShow;
    private List<Show> listSavedShow;

    private User commercial;

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
    }

    public void getSavedShow()
    {
        /*TODO récupérer la liste des salons existnats dans l'erp et les ajouter a ListSavedShow*/
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
