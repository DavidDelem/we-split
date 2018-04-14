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

                String id = (String) depense.get("payeparid");
                Map<String, String> users = (Map<String, String>) depense.get("users");
                Float montant = Float.parseFloat((String) depense.get("montant"));

                if(users != null) {
                    for (String user : users.values()) {
                        if (utilisateurDepense.get(user) != null) {
                            if (user.equals(id)) {
                                Float result = montant - (montant / users.size());
                                utilisateurDepense.put(user, utilisateurDepense.get(user) + result);
                            } else {
                                Float result = montant / users.size();
                                utilisateurDepense.put(user, utilisateurDepense.get(user) - result);
                            }
                        }
                    }
                }
            }
        }
    }

    public String getEquilibreUtilisateur(String identifiant) {
        return Float.toString(utilisateurDepense.get(identifiant));
    }

    public Float getTotalDepenses() {
       return totalDepenses;
    }

    public Map<String, Float> getUtilisateurDepense() {
        return utilisateurDepense;
    }
}
