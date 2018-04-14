package com.uqac.wesplit.helpers;

import com.uqac.wesplit.adapters.Depense;
import com.uqac.wesplit.enums.CategoriesEnum;
import com.uqac.wesplit.enums.PeriodesEnum;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateurStatistiques {

    private Map<CategoriesEnum, Float> repartitionDepenses;
    private Float totalDepenses;

    public CalculateurStatistiques() {
        repartitionDepenses = new HashMap<CategoriesEnum, Float>();
        totalDepenses = 0.0f;

        for (CategoriesEnum categorie : CategoriesEnum.values()) {
            repartitionDepenses.put(categorie, 0f);
        }
    }

    public void repartirDepenses(List<Depense> depenses, PeriodesEnum periode) {

        Calendar calendar = Calendar.getInstance();
        int nbJours = getNbJours(periode);
        calendar.add(Calendar.DAY_OF_YEAR, nbJours);
        Long timestampDebut = calendar.getTime().getTime() / 1000;

        for (CategoriesEnum categorie : CategoriesEnum.values()) {
            repartitionDepenses.put(categorie, 0f);
        }

        if(depenses != null) {
            for (Depense depense : depenses) {
                    if(depense.getTimestamp() != null && (Long.valueOf(depense.getTimestamp()) > timestampDebut || nbJours == 0)) {
                        CategoriesEnum categorie = CategoriesEnum.getEnum(depense.getCategorie());
                        repartitionDepenses.put(categorie, repartitionDepenses.get(categorie) + Float.parseFloat(depense.getMontant()));
                        totalDepenses += Float.parseFloat(depense.getMontant());
                    }
            }
        }
    }

    public Map<CategoriesEnum, Float> obtenirPourcentages() {
        Map<CategoriesEnum, Float> pourcentagesDepenses = new HashMap<CategoriesEnum, Float>();

        for (CategoriesEnum categorie : CategoriesEnum.values()) {
            Float pourcentage = (repartitionDepenses.get(categorie) / totalDepenses) * 100;
            pourcentagesDepenses.put(categorie, pourcentage);
        }
        return pourcentagesDepenses;
    }

    public Map<CategoriesEnum, Float> getRepartitionDepenses() {
        return repartitionDepenses;
    }

    public Float getTotalDepenses() {
        return totalDepenses;
    }

    private int getNbJours(PeriodesEnum periode) {

        switch (periode) {
            case SEPTJOURS:
                return -7;
            case QUINZEJOURS:
                return -15;
            case TRENTEJOURS:
                return -30;
            case TOUT:
                return 0;
            default:
                return 0;
        }

    }
}
