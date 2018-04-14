package com.uqac.wesplit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.ModificationDepenseActivity;
import com.uqac.wesplit.R;
import com.uqac.wesplit.adapters.Depense;
import com.uqac.wesplit.adapters.DepenseAdapter;

import java.util.ArrayList;


public class DepensesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listview;
    private SwipeRefreshLayout swipeLayout;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ArrayAdapter<Depense> adapter;

    public DepensesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depenses, container, false);
        listview = (ListView) view.findViewById(R.id.listview_depenses);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);

        final ArrayList<Depense> items = new ArrayList<>();

        adapter = new DepenseAdapter(this.getActivity(), R.layout.row_list_depenses, items);
        listview.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference refUser = database.getReference("users/" + auth.getCurrentUser().getUid());

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String groupe = (String) dataSnapshot.child("groupe").getValue();

                DatabaseReference ref = database.getReference("groupes/" + groupe + "/depenses");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() == 0) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                ref.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Depense depense = dataSnapshot.getValue(Depense.class);
                        depense.set_id(dataSnapshot.getKey());
                        items.add(depense);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        items.remove(dataSnapshot.getValue(Depense.class));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Depense depense = (Depense) listview.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ModificationDepenseActivity.class);
                intent.putExtra("depense", depense);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
            }

        }, 1200);
    }


}