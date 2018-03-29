package com.uqac.wesplit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.wesplit.helpers.Depense;

import java.util.ArrayList;


public class DepensesFragment extends Fragment {

    private ListView listview;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public DepensesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depenses, container, false);
        listview = (ListView) view.findViewById(R.id.listview_depenses);
        final ArrayList<Depense> items = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // @todo rendre variable depenses
        DatabaseReference ref = database.getReference("groupes/dhVAz7/depenses");

        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Depense depense = dataSnapshot.getValue(Depense.class);
                depense.set_id(dataSnapshot.getKey());
                items.add(depense);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });


        return view;
    }

}
