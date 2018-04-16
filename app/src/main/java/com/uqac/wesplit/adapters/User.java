package com.uqac.wesplit.adapters;

/**
 Représente un utilisateur
 */

public class User {

    private String identifiant;
    private String name;
    boolean selected = true;


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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name;
    }
}
