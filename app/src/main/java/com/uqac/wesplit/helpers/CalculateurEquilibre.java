package com.uqac.wesplit.helpers;

import java.util.HashMap;
import java.util.Map;

public class CalculateurEquilibre {

    Map<String, Float> utilisateurDepense;
    Float totalDepenses;

    public CalculateurEquilibre() {
        utilisateurDepense = new HashMap<String, Float>();
        totalDepenses = 0.0f;
    }

    public void ajouterUtilsateur(String identifiant) {
        utilisateurDepense.put(identifiant, 0.0f);
    }

    public void ajouterDepenses(Map<String, Map<String, Object>> depenses) {
        if(depenses != null) {
            for (Map<String, Object> depense : depenses.values()) {
//                String id = (String) depense.get("payeparid");
//                if(utilisateurDepense.get(id) != null) {
//                    utilisateurDepense.put(id, utilisateurDepense.get(id) + Float.parseFloat((String) depense.get("montant")));
//                    totalDepenses = totalDepenses + Float.parseFloat((String) depense.get("montant"));
//                }

                String id = (String) depense.get("payeparid");
                Map<String, String> users = (Map<String, String>) depense.get("users");
                Float montant = Float.parseFloat((String) depense.get("montant"));

                if(users != null) {
                    for (String user : users.values()) {
                        if (utilisateurDepense.get(id) != null) {
                            if (user.equals(id)) {
                                Float result = montant - (montant / user.length());
                                utilisateurDepense.put(id, utilisateurDepense.get(id) + result);
                            } else {
                                Float result = -(montant / user.length());
                                utilisateurDepense.put(id, utilisateurDepense.get(id) + result);
                            }
                        }
                    }
                }
            }
        }
    }

    public String getEquilibreUtilisateur(String identifiant) {
//        Float depensesParUtilisateurEgales = totalDepenses / utilisateurDepense.size();
//
//        if(utilisateurDepense.get(identifiant) < depensesParUtilisateurEgales) {
//            return Float.toString(0.0f - depensesParUtilisateurEgales + utilisateurDepense.get(identifiant));
//        } else {
//            return Float.toString(utilisateurDepense.get(identifiant) - depensesParUtilisateurEgales);
//        }
        return Float.toString(utilisateurDepense.get(identifiant));
    }

    public Float getTotalDepenses() {
       return totalDepenses;
    }

    public Map<String, Float> getUtilisateurDepense() {
        return utilisateurDepense;
    }
}
