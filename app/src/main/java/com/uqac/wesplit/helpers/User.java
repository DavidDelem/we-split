package com.uqac.wesplit.helpers;

public class User {

    private String identifiant;
    private String name;

    public User(String identifiant, String name) {
        this.identifiant = identifiant;
        this.name = name;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
