package com.uqac.wesplit.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.wesplit.R;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {

    private ArrayList<Message> objects;

    public MessageAdapter(Context context, int textViewResourceId, ArrayList<Message> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public Message getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_list_messages, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Message i = getItem(position);

        if (i != null) {

            TextView message = (TextView) v.findViewById(R.id.text_right);
            TextView nameDate = (TextView) v.findViewById(R.id.text_right_bottom);

            if (message != null) {
                message.setText(i.getMessage());
            }
            if (nameDate != null) {
                nameDate.setText(i.getName() + ", " + i.getDate());
            }
        }

        return v;

    }
}


