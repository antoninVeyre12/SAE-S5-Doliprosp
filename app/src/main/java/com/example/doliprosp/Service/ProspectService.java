package com.example.doliprosp.Service;

import com.example.doliprosp.Interface.IProspect;

import java.util.UUID;

public class ProspectService implements IProspect {
    private UUID idProspect;
    private UUID idSalon;
    private String prenom;
    private String nom;
    private int codePostal;
    private String ville;
    private String adresse;
    private String email;
    private String numeroTelephone;
    private Boolean estClient;


    public ProspectService(UUID idShow, String firstName, String lastName, int postCode,
                           String city, String postalAddress, String email, String phoneNumber,
                           Boolean isClient)
    {
        this.idProspect = UUID.randomUUID();
        this.idSalon = idShow;
        this.prenom = firstName;
        this.nom = lastName;
        this.codePostal = postCode;
        this.ville = city;
        this.adresse = postalAddress;
        this.email = email;
        this.numeroTelephone = phoneNumber;
        this.estClient = isClient;
    }

    @Override
    public void ajouterProspect(ProspectService prospect) {

    }

    @Override
    public void supprimerProspect(ProspectService prospect) {

    }

    @Override
    public void envoyerProspect(ProspectService prospect) {

    }

    @Override
    public ProspectService getProspectEnregistre(String prenom, String nom, int codePostal, String ville, String adresse, String email, String numeroTelephone) {
        return null;
    }

    public void updateProspect(String newFirstName, String newLastName, int newPostCode, String newCity,
                               String newPostalAddress, String newEmail, String newPhoneNumber,
                               Boolean newIsClient )
    {
        this.setFirstName(newFirstName);
        this.setLastName(newLastName);
        this.setPostCode(newPostCode);
        this.setCity(newCity);
        this.setPostalAddress(newPostalAddress);
        this.setEmail(newEmail);
        this.setPhoneNumber(newPhoneNumber);
        this.setIsClient(newIsClient);
    }

    @Override
    public UUID getIdSalon() {
        return null;
    }

    public UUID getIdShow()
    {
        return this.idSalon;
    }

    public UUID getIdProspect()
    {
        return this.idProspect;
    }

    @Override
    public String getPrenom() {
        return "";
    }

    @Override
    public String getNom() {
        return "";
    }

    @Override
    public int getCodePostal() {
        return 0;
    }

    public String getFirstName()
    {
        return this.prenom;
    }

    public String getLastName()
    {
        return this.nom;
    }

    public int getPostCode()
    {
        return this.codePostal;
    }

    public String getEmail()
    {
        return this.email;
    }

    @Override
    public String getNumeroTelephone() {
        return "";
    }

    @Override
    public Boolean getEstClient() {
        return null;
    }

    @Override
    public void setPrenom(String prenom) {

    }

    @Override
    public void setNom(String nom) {

    }

    @Override
    public void setCodePostal(int codePostal) {

    }

    @Override
    public void setVille(String ville) {

    }

    @Override
    public void setAdresse(String adresse) {

    }

    public String getPhoneNumber()
    {
        return this.numeroTelephone;
    }

    public Boolean getIsClient()
    {
        return this.estClient;
    }

    public void setFirstName(String firstName)
    {
        this.prenom = firstName;
    }

    public void setLastName(String lastName)
    {
        this.nom = lastName;
    }

    public void setPostCode(int postCode)
    {
        this.codePostal = postCode;
    }

    public void setCity(String ville)
    {
        this.ville = ville;
    }

    public void setPostalAddress(String adresse)
    {
            this.adresse = adresse;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public void setNumeroTelephone(String numeroTelephone) {

    }

    @Override
    public void setEstClient(Boolean estClient) {

    }

    public void setPhoneNumber(String numeroTelephone)
    {
        this.numeroTelephone = numeroTelephone;
    }

    public void setIsClient(Boolean estClient)
    {
        this.estClient = estClient;
    }
}