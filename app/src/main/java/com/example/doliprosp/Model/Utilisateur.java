package com.example.doliprosp.Model;

public class Utilisateur {

    private String url;
    private String userName;
    private String motDePasse;
    private String apiKey;

    public Utilisateur(String url, String userName, String motDePasse, String apiKey)
    {
        this.url = url;
        this.userName = userName;
        this.motDePasse = motDePasse;
        this.apiKey = apiKey;

    }

    public String getUrl()
    {
        return this.url;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getMotDePasse()
    {
        return this.motDePasse;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setUrl(String newUrl)
    {
        this.url = newUrl;
    }

    public void setUserName(String newUserName)
    {
        this.userName = newUserName;
    }

    public void setMotDePasse(String nouveauMotDePasse) {
        this.motDePasse = nouveauMotDePasse;
    }

    public void setApiKey(String newApiKey)
    {
        this.apiKey = newApiKey;
    }
}
