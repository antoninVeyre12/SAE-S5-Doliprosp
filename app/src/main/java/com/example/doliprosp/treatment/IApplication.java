package com.example.doliprosp.treatment;

public interface IApplication {

    public void addProspect(Prospect prospect);

    public void addProject(Project project);


    public void addLocalShow(Show localShow);

    public void deleteProspect(Prospect prospect);


    public void deleteProject(Project project);

    public void deleteLocalShow(Show show);

    public void getSavedShow();

    public void sendProspect(Prospect prospect);

    public void sendProject(Project project);

    public void sendShow(Show show);

    public Prospect getSavedProsepct(String newFirstName, String newLastName, int newPostCode, String newCity,
                                     String newPostalAddress, String newEmail, String newPhoneNumber);
}
