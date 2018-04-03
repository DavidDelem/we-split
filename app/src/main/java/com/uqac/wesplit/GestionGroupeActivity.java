package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.dialogs.IdentifiantGroupeDialog;
import com.uqac.wesplit.dialogs.SupprimerGroupeDialog;
import com.uqac.wesplit.helpers.Depense;
import com.uqac.wesplit.helpers.DepenseAdapter;
import com.uqac.wesplit.helpers.User;
import com.uqac.wesplit.helpers.UserAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionGroupeActivity extends AppCompatActivity {

    private Button btnSupprimer, btnQuitter, btnIdentifiant;
    private ImageButton btnRetour;
    private ListView listUsers;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ArrayAdapter<User> adapter;
    private String identifiantGroupe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_groupe);

        btnRetour = (ImageButton) findViewById(R.id.btn_groupe_retour);
        btnSupprimer = (Button) findViewById(R.id.btn_supprimer_groupe);
        btnQuitter = (Button) findViewById(R.id.btn_quitter_groupe);
        btnIdentifiant = (Button) findViewById(R.id.btn_afficher_identifiant_groupe);
        listUsers = (ListView) findViewById(R.id.listview_users);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        final ArrayList<User> users = new ArrayList<>();

        adapter = new UserAdapter(GestionGroupeActivity.this, R.layout.row_list_users, users);
        listUsers.setAdapter(adapter);

        DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid() + "/groupe");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                identifiantGroupe = (String) dataSnapshot.getValue();
                DatabaseReference ref = database.getReference("groupes/" + identifiantGroupe + "/users");

                ref.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = new User(dataSnapshot.getKey(), (String) dataSnapshot.getValue());
                        users.add(user);
                        adapter.notifyDataSetChanged();
//                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        User user = new User(dataSnapshot.getKey(), (String) dataSnapshot.getValue());
                        users.remove(user);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });
//
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        Map<String, String> usersGroupe = (Map<String, String>) dataSnapshot.getValue();
//                        List<User> userList = new ArrayList<User>();
//                        for (Map.Entry<String, String> entry : usersGroupe.entrySet()) {
//                            userList.add(new User(entry.getKey(), entry.getValue()));
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        btnIdentifiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentifiantGroupeDialog identifiantGroupeDialog = new IdentifiantGroupeDialog(GestionGroupeActivity.this);
                identifiantGroupeDialog.show();
            }
        });

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupprimerGroupeDialog supprimerGroupeDialog = new SupprimerGroupeDialog(GestionGroupeActivity.this, identifiantGroupe);
                supprimerGroupeDialog.show();
            }
        });


        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Retour à l'activité principale
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionGroupeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
