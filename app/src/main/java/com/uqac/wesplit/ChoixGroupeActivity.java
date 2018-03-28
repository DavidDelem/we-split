package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.wesplit.helpers.TokenGenerator;

public class ChoixGroupeActivity extends AppCompatActivity {

    private static final int IDENTIFIANT_LENGTH = 6;

    private EditText inputNouveau, inputRejoindre;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private Button btnNouveau, btnRejoindre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_groupe);

        inputNouveau = (EditText) findViewById(R.id.nouveaugroupe);
        inputRejoindre = (EditText) findViewById(R.id.rejoindregroupe);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnNouveau = (Button) findViewById(R.id.btn_nouveaugroupe);
        btnRejoindre = (Button) findViewById(R.id.btn_rejoindregroupe);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomGroupe = inputNouveau.getText().toString();
                String identifiantGroupe = TokenGenerator.getRandomToken(6);

                databaseReference.child("groupes").child(identifiantGroupe).child("users").setValue(auth.getCurrentUser().getUid());
                databaseReference.child("groupes").child(identifiantGroupe).child("name").setValue(nomGroupe);
                databaseReference.child("users").child(auth.getCurrentUser().getUid()).child("groupe").setValue(identifiantGroupe);

                Intent intent = new Intent(ChoixGroupeActivity.this, ConfirmationGroupeActivity.class);
                intent.putExtra("nomGroupe", nomGroupe);
                intent.putExtra("identifiantGroupe", identifiantGroupe);
                startActivity(intent);
                finish();
            }
        });

        btnRejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // @todo vérifier que l'id existe (=que le groupe existe déjà)
                // @todo mettre cet id dans le joueur
                // @todo ajouter le joueur au groupe
                String identifiantGroupe = inputNouveau.getText().toString();

                if(identifiantGroupe.length() == IDENTIFIANT_LENGTH) {

                }

                startActivity(new Intent(ChoixGroupeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
