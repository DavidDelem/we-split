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


/**
 Adapter permettant de générer chaque un élément de la ListView des utilisateurs
 */

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

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_users, null);
        }

        User i = getItem(position);

        if (i != null) {

            TextView titre = (TextView) v.findViewById(R.id.view_titre);

            if (titre != null){
                titre.setText(i.getName());
            }
        }

        ImageButton imageButton = v.findViewById(R.id.btn_suppr_user);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });

        return v;

    }

}