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

public class UserCheckboxAdapter extends ArrayAdapter<User> {

    private ArrayList<User> objects;

    public UserCheckboxAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(final int position, View convertView, final ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_users_checkbox, null);
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

            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox_user);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (checkBox != null){
                checkBox.setText(i.getName());
                checkBox.setChecked(i.isSelected());
            }
        }

        CheckBox checkBox = v.findViewById(R.id.checkbox_user);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });

        // the view must be returned to our activity
        return v;

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
