package com.uqac.wesplit.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.wesplit.R;

import java.util.ArrayList;

public class DepenseAdapter extends ArrayAdapter<Depense> {

    private ArrayList<Depense> objects;

    public DepenseAdapter(Context context, int textViewResourceId, ArrayList<Depense> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public Depense getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_depenses, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Depense i = getItem(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView titre = (TextView) v.findViewById(R.id.view_titre);
            TextView payepar = (TextView) v.findViewById(R.id.view_paye_par);
            TextView categorie = (TextView) v.findViewById(R.id.view_categorie);
            TextView montant = (TextView) v.findViewById(R.id.view_montant);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (titre != null){
                titre.setText(i.getTitre());
            }
            if (payepar != null){
                payepar.setText("payé par " + i.getPayeparname());
            }
            if (categorie != null){
                categorie.setText(i.getCategorie());
            }
            if (montant != null){
                montant.setText(i.getMontant() + " $CA");
            }
        }

        // the view must be returned to our activity
        return v;

    }


}