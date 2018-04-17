package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

/**
 Activité confirmant la création du groupe
 */

public class ConfirmationGroupeActivity extends AppCompatActivity {

    private TextView identifiantGroupeText, confirmationGroupeText;
    private Button btnContinuer;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_groupe);

        // Récupération des éléments de la vue
        identifiantGroupeText = (TextView) findViewById(R.id.confirmationgroupe_identifiant);
        confirmationGroupeText = (TextView) findViewById(R.id.confirmationgroupe_texte);
        btnContinuer = (Button) findViewById(R.id.btn_continuer);

        // Récupération des données
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getString("type").equals("nouveau")) {
                // Cas ou l'utilisateur à créé un nouveau groupe
                confirmationGroupeText.setText("Vous avez créé le groupe " + extras.getString("nomGroupe") + " ! Partagez cet identifiant avec vos amis afin qu'ils puissent vous rejoindre.");
                identifiantGroupeText.setText(extras.getString("identifiantGroupe"));
            } else if(extras.getString("type").equals("rejoindre")) {
                // Cas ou l'utilisateur à rejoint un groupe existant
                confirmationGroupeText.setText("Vous avez rejoint le groupe " + extras.getString("nomGroupe") + " ! Vous pouvez désormais commencer à utiliser l'application.");
                identifiantGroupeText.setVisibility(View.GONE);
            }
        }

        // Clic sur le bouton continuer
        btnContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmationGroupeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
