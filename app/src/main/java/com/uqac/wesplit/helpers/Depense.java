package com.uqac.wesplit.helpers;

public class Depense {

    private String _id;
    private String categorie;
    private String montant;
    private String payepar;
    private String titre;

    public Depense() {

    }

    public Depense(String _id, String categorie, String montant, String payepar, String titre) {
        this._id = _id;
        this.categorie = categorie;
        this.montant = montant;
        this.payepar = payepar;
        this.titre = titre;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getPayepar() {
        return payepar;
    }

    public void setPayepar(String payepar) {
        this.payepar = payepar;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Depense depense = (Depense) o;

        if (_id != null ? !_id.equals(depense._id) : depense._id != null) return false;
        if (categorie != null ? !categorie.equals(depense.categorie) : depense.categorie != null)
            return false;
        if (montant != null ? !montant.equals(depense.montant) : depense.montant != null)
            return false;
        if (payepar != null ? !payepar.equals(depense.payepar) : depense.payepar != null)
            return false;
        return titre != null ? titre.equals(depense.titre) : depense.titre == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (categorie != null ? categorie.hashCode() : 0);
        result = 31 * result + (montant != null ? montant.hashCode() : 0);
        result = 31 * result + (payepar != null ? payepar.hashCode() : 0);
        result = 31 * result + (titre != null ? titre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Depense{" +
                "_id='" + _id + '\'' +
                ", categorie='" + categorie + '\'' +
                ", montant='" + montant + '\'' +
                ", payepar='" + payepar + '\'' +
                ", titre='" + titre + '\'' +
                '}';
    }
}
