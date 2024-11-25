package com.example.doliprosp.treatment;

public class User {
    private String url;
    private String userName;
    private String password;
    private String apiKey;
    private IApplication applicationManager;

    public User(String url, String userName, String password)
    {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public void changementUtilisateur(String url, String userName, String password)
    {
        this.setUrl(url);
        this.setUserName(userName);
        this.setPassword(password);
        this.connexion();
    }

    public String connexion()
    {
        /* Todo appel à l'api pour récupérer l'apikey et retourner l'apikey*/
        return null ;
    }

    public void chiffrementApiKey()
    {
        /*TODO chiffrer la clé API avec la méthode de Vigenère*/
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
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

    public void setPassword(String newPassword)
    {
        this.password = newPassword;
    }

    public void setApiKey(String newApiKey)
    {
        this.apiKey = newApiKey;
    }
}