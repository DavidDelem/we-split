package com.uqac.wesplit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ParamsActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Récupération des éléments de la vue
        inputEmail = (EditText) findViewById(R.id.email);
        inputEmail.setText(auth.getCurrentUser().getEmail());

        // A FAIRE:

        // modifier mon adresse mail (a faire avec les fonctions de firebase)
        // modifier mon mot de passe (a faire avec les fonctions de firebase)
        // supprimer mon compte (a faire avec les fonctions de firebase + supprimer user dans la bdd + supprimer user dans le groupe associé)
    }
}
