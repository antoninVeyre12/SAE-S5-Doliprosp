package com.example.doliprosp.treatment;

import java.util.UUID;

public class Prospect {
    private UUID idProspect;
    private UUID idShow;
    private String firstName;
    private String lastName;
    private int postCode;
    private String city;
    private String postalAddress;
    private String email;
    private String phoneNumber;
    private Boolean isClient;

    private IApplication applicationManager;


    public Prospect(UUID idShow, String firstName, String lastName, int postCode,
                    String city, String postalAddress, String email, String phoneNumber,
                    Boolean isClient)
    {
        this.idProspect = UUID.randomUUID();
        this.idShow = idShow;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
        this.city = city;
        this.postalAddress = postalAddress;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isClient = isClient;
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

    public UUID getIdShow()
    {
        return this.idShow;
    }

    public UUID getIdProspect()
    {
        return this.idProspect;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public int getPostCode()
    {
        return this.postCode;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public Boolean getIsClient()
    {
        return this.isClient;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setPostCode(int postCode)
    {
        this.postCode = postCode;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setPostalAddress(String postalAddress)
    {
        this.postalAddress = postalAddress;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setIsClient(Boolean isClient)
    {
        this.isClient = isClient;
    }
}