package com.uqac.wesplit.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 Classe permettant de calculer les équilibres entre les membres d'un groupe
 */

public class CalculateurEquilibre {

    Map<String, Float> utilisateursEquilibres;

    public CalculateurEquilibre() {
        utilisateursEquilibres = new HashMap<String, Float>();
    }

    /**
     Ajoute un utilisateur pour le calcul des équilibres
     @param identifiant l'identifiant de l'utilisateur
     @return void
     */

    public void ajouterUtilsateur(String identifiant) {
        utilisateursEquilibres.put(identifiant, 0.0f);
    }

    /**
     Ajoute les dépenses et calcul l'équilibre pour chaque dépense
     @param depenses les dépenses pour lesquelles il faut calculer l'équilibre
     @return void
     */

    public void ajouterDepenses(Map<String, Map<String, Object>> depenses) {
        if(depenses != null) {
            for (Map<String, Object> depense : depenses.values()) {

                String id = (String) depense.get("payeparid");
                Map<String, String> users = (Map<String, String>) depense.get("users");
                Float montant = Float.parseFloat((String) depense.get("montant"));

                if(users != null) {
                    for (String user : users.values()) {
                        if (utilisateursEquilibres.get(user) != null) {
                            if (user.equals(id)) {
                                Float result = montant - (montant / users.size());
                                utilisateursEquilibres.put(user, utilisateursEquilibres.get(user) + result);
                            } else {
                                Float result = montant / users.size();
                                utilisateursEquilibres.put(user, utilisateursEquilibres.get(user) - result);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     Renvoie l'équilibre pour un utilisateur donné
     @param identifiant identifiant de l'utilisateur
     @return la valeur de l'équilibre
     */

    public String getEquilibreUtilisateur(String identifiant) {
        return Float.toString(utilisateursEquilibres.get(identifiant));
    }

    /**
     Renvoie les équilibres de tous les utilisateurs
     @param
     @return touts les équilibres
     */

    public Map<String, Float> getUtilisateursEquilibres() {
        return utilisateursEquilibres;
    }
}
