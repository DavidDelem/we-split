package com.uqac.wesplit.enums;

public enum CategoriesEnum {

    COURSES ("Courses"),
    HABITATION ("Logement"),
    LOISIRS ("Loisirs"),
    TRANSPORTS ("Transport"),
    SANTE ("Santé"),
    AUTRE ("Autre");

    private String categorie = "";

    CategoriesEnum(String categorie){
        this.categorie = categorie;
    }

    public String toString(){
        return categorie;
    }
}
