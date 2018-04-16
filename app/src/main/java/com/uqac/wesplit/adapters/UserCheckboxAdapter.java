package com.uqac.wesplit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.wesplit.R;

import java.util.ArrayList;

/**
 Adapter permettant de générer chaque un élément de la ListView des utilisateurs avec une checkbox
 */

public class UserCheckboxAdapter extends ArrayAdapter<User> {

    private ArrayList<User> objects;

    public UserCheckboxAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(final int position, View convertView, final ViewGroup parent){

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_users_checkbox, null);
        }

        User i = getItem(position);

        if (i != null) {

            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox_user);

            if (checkBox != null){
                checkBox.setText(i.getName());
                checkBox.setChecked(i.isSelected());
            }
        }

        CheckBox checkBox = v.findViewById(R.id.checkbox_user);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });

        return v;

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
