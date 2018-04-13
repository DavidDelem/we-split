package com.uqac.wesplit.adapters;

import java.io.Serializable;
import java.util.Map;

public class Depense implements Serializable {

    private String _id;
    private String categorie;
    private String montant;
    private String payeparid;
    private String payeparname;
    private String titre;
    private String timestamp;
    private Map<String,String> users;

    public Depense() {
    }

    public Depense(String _id, String categorie, String montant, String payeparid, String payeparname, String titre, String timestamp, Map<String, String> users) {
        this._id = _id;
        this.categorie = categorie;
        this.montant = montant;
        this.payeparid = payeparid;
        this.payeparname = payeparname;
        this.titre = titre;
        this.timestamp = timestamp;
        this.users = users;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategorie() { return categorie; }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getPayeparid() {
        return payeparid;
    }

    public void setPayeparid(String payeparid) {
        this.payeparid = payeparid;
    }

    public String getPayeparname() {
        return payeparname;
    }

    public void setPayeparname(String payeparname) {
        this.payeparname = payeparname;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
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
        if (payeparid != null ? !payeparid.equals(depense.payeparid) : depense.payeparid != null)
            return false;
        if (payeparname != null ? !payeparname.equals(depense.payeparname) : depense.payeparname != null)
            return false;
        if (titre != null ? !titre.equals(depense.titre) : depense.titre != null) return false;
        if (timestamp != null ? !timestamp.equals(depense.timestamp) : depense.timestamp != null)
            return false;
        return users != null ? users.equals(depense.users) : depense.users == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (categorie != null ? categorie.hashCode() : 0);
        result = 31 * result + (montant != null ? montant.hashCode() : 0);
        result = 31 * result + (payeparid != null ? payeparid.hashCode() : 0);
        result = 31 * result + (payeparname != null ? payeparname.hashCode() : 0);
        result = 31 * result + (titre != null ? titre.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Depense{" +
                "_id='" + _id + '\'' +
                ", categorie='" + categorie + '\'' +
                ", montant='" + montant + '\'' +
                ", payeparid='" + payeparid + '\'' +
                ", payeparname='" + payeparname + '\'' +
                ", titre='" + titre + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", users=" + users +
                '}';
    }
}
