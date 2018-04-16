package com.uqac.wesplit.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.wesplit.auth.LoginActivity;
import com.uqac.wesplit.R;

/**
 Fenêtre dialog permettant de quitter le groupe
 */

public class QuitterGroupeDialog extends Dialog {

    private Activity c;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Button exitButton, confirmButton;
    private ProgressBar progressBar;
    private String identifiantGroupe;

    public QuitterGroupeDialog(Activity a, String identifiantGroupe) {
        super(a);
        this.c = a;
        this.identifiantGroupe = identifiantGroupe;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_quitter_groupe);

        exitButton = (Button) findViewById(R.id.btn_exit_quitter_modal);
        confirmButton = (Button) findViewById(R.id.btn_confirmer_quitter_modal);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitterGroupe();
            }
        });
    }

    private void quitterGroupe() {
        database.getReference("groupes/" + identifiantGroupe + "/users/" + auth.getCurrentUser().getUid()).setValue(null);
        database.getReference("users/" + auth.getCurrentUser().getUid() + "/groupe").setValue(null);
        disconnect();
    }

    private void disconnect() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(c.getApplicationContext(), "Vous avez quité le groupe et avez été déconnecté.", Toast.LENGTH_LONG).show();
        c.startActivity(new Intent(c, LoginActivity.class));
        c.finish();
    }

}

