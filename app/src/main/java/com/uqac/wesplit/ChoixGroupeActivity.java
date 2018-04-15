package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.auth.SignupActivity;
import com.uqac.wesplit.helpers.TokenGenerator;

public class ChoixGroupeActivity extends AppCompatActivity {

    private static final int IDENTIFIANT_LENGTH = 6;

    private EditText inputNouveau, inputRejoindre;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ProgressBar progressBar;
    private Button btnNouveau, btnRejoindre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_groupe);

        // Récupération des éléments de la vue
        inputNouveau = (EditText) findViewById(R.id.nouveaugroupe);
        inputRejoindre = (EditText) findViewById(R.id.rejoindregroupe);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnNouveau = (Button) findViewById(R.id.btn_nouveaugroupe);
        btnRejoindre = (Button) findViewById(R.id.btn_rejoindregroupe);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nomGroupe = inputNouveau.getText().toString();
                final String identifiantGroupe = TokenGenerator.getRandomToken(6);

                DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid() + "/name");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DatabaseReference ref = database.getReference();
                        ref.child("groupes").child(identifiantGroupe).child("users").child(auth.getCurrentUser().getUid()).setValue(dataSnapshot.getValue());
                        ref.child("groupes").child(identifiantGroupe).child("name").setValue(nomGroupe);
                        ref.child("users").child(auth.getCurrentUser().getUid()).child("groupe").setValue(identifiantGroupe);

                        Intent intent = new Intent(ChoixGroupeActivity.this, ConfirmationGroupeActivity.class);
                        intent.putExtra("type", "nouveau");
                        intent.putExtra("nomGroupe", nomGroupe);
                        intent.putExtra("identifiantGroupe", identifiantGroupe);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }

                });
            }
        });

        btnRejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String identifiantGroupe = inputRejoindre.getText().toString().trim();

                if(identifiantGroupe.length() == IDENTIFIANT_LENGTH) {

                    DatabaseReference ref = database.getReference("groupes/" + identifiantGroupe);

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String nomGroupe = (String) dataSnapshot.child("name").getValue();

                            if(nomGroupe != null) {

                                DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid() + "/name");

                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        DatabaseReference ref = database.getReference();
                                        ref.child("users").child(auth.getCurrentUser().getUid()).child("groupe").setValue(identifiantGroupe);
                                        ref.child("groupes").child(identifiantGroupe).child("users").child(auth.getCurrentUser().getUid()).setValue(dataSnapshot.getValue());

                                        Intent intent = new Intent(ChoixGroupeActivity.this, ConfirmationGroupeActivity.class);
                                        intent.putExtra("type", "rejoindre");
                                        intent.putExtra("nomGroupe", nomGroupe);
                                        intent.putExtra("identifiantGroupe", identifiantGroupe);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                            } else {
                                Toast.makeText(ChoixGroupeActivity.this, "Le groupe saisi n'existe pas. Vérifiez l'identifiant.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                } else {
                    Toast.makeText(ChoixGroupeActivity.this, "L'identifiant est invalide.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
