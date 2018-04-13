package com.uqac.wesplit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.wesplit.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    private ArrayList<User> objects;

    public UserAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public User getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    public View getView(final int position, View convertView, final ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_users, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        User i = getItem(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView titre = (TextView) v.findViewById(R.id.view_titre);
//            TextView payepar = (TextView) v.findViewById(R.id.view_paye_par);
//            TextView categorie = (TextView) v.findViewById(R.id.view_categorie);
//            TextView montant = (TextView) v.findViewById(R.id.view_montant);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (titre != null){
                titre.setText(i.getName());
            }
//            if (payepar != null){
//                payepar.setText("payé par " + i.getPayeparname());
//            }
//            if (categorie != null){
//                categorie.setText(i.getCategorie());
//            }
//            if (montant != null){
//                montant.setText(i.getMontant() + "$");
//            }
        }

        ImageButton imageButton = v.findViewById(R.id.btn_suppr_user);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });

        // the view must be returned to our activity
        return v;

    }

}