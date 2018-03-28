package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmationGroupeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView identifiantGroupeText, confirmationGroupeText;
    private Button btnContinuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_groupe);

        identifiantGroupeText = (TextView) findViewById(R.id.confirmationgroupe_identifiant);
        confirmationGroupeText = (TextView) findViewById(R.id.confirmationgroupe_texte);
        btnContinuer = (Button) findViewById(R.id.btn_continuer);

        auth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            confirmationGroupeText.setText("Félicitation, vous avez créé le groupe " + extras.getString("nomGroupe") + " ! Partagez cet identifiant avec vos amis afin qu'ils puissent vous rejoindre.");
            identifiantGroupeText.setText(extras.getString("identifiantGroupe"));
        }

        btnContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmationGroupeActivity.this, MainActivity.class));
            }
        });
    }
}
