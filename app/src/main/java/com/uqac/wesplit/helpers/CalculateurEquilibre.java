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

    public void ajouterDepenses(Map<String, Map<String, String>> depenses) {
        if(depenses != null) {
            for (Map<String, String> depense : depenses.values()) {
                String id = depense.get("payeparid");
                if(utilisateurDepense.get(id) != null) {
                    utilisateurDepense.put(id, utilisateurDepense.get(id) + Float.parseFloat(depense.get("montant")));
                    totalDepenses = totalDepenses + Float.parseFloat(depense.get("montant"));
                }
            }
        }
    }

    public String getEquilibreUtilisateur(String identifiant) {
        Float depensesParUtilisateurEgales = totalDepenses / utilisateurDepense.size();

        if(utilisateurDepense.get(identifiant) < depensesParUtilisateurEgales) {
            return Float.toString(0.0f - depensesParUtilisateurEgales + utilisateurDepense.get(identifiant));
        } else {
            return Float.toString(utilisateurDepense.get(identifiant) - depensesParUtilisateurEgales);
        }
    }

    public Float getTotalDepenses() {
       return totalDepenses;
    }

    public Map<String, Float> getUtilisateurDepense() {
        return utilisateurDepense;
    }
}
