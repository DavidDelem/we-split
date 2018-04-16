package com.uqac.wesplit.enums;

/**
 Enum représentant les catégories possibles
 */

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

    public static CategoriesEnum getEnum(String s) {
        switch (s) {
            case "Courses":
                return CategoriesEnum.COURSES;
            case "Logement":
                return CategoriesEnum.HABITATION;
            case "Loisirs":
                return CategoriesEnum.LOISIRS;
            case "Transport":
                return CategoriesEnum.TRANSPORTS;
            case "Santé":
                return CategoriesEnum.SANTE;
            case "Autre":
                return CategoriesEnum.AUTRE;
            default:
                return null;
        }
    }
}
