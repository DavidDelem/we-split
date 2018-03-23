package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class ChoixGroupeActivity extends AppCompatActivity {

    private EditText inputNouveau, inputRejoindre;
    private FirebaseAuth auth;
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

        btnNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // créer le nouveau groupe avec un nom et un id généré
                // mettre cet id dans le joueur
                // rediriger vers un paneau qui indique l'identifiant à transmettre aux amis pour qu'ils rejoignent le groupe
                startActivity(new Intent(ChoixGroupeActivity.this, MainActivity.class));
            }
        });

        btnRejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vérifier que l'id existe
                // mettre cet id dans le joueur
                startActivity(new Intent(ChoixGroupeActivity.this, MainActivity.class));
            }
        });
    }
}
