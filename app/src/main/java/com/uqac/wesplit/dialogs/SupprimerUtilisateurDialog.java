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
import com.uqac.wesplit.MainActivity;
import com.uqac.wesplit.R;
import com.uqac.wesplit.auth.LoginActivity;

/**
 Fenêtre dialog permettant de supprimer un utilisateur du groupe
 */

public class SupprimerUtilisateurDialog extends Dialog {

    private Activity c;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Button exitButton, confirmButton;
    private ProgressBar progressBar;
    private String identifiantGroupe;
    private String identifiantUser;

    public SupprimerUtilisateurDialog(Activity a, String identifiantGroupe, String identifiantUser) {
        super(a);
        this.c = a;
        this.identifiantGroupe = identifiantGroupe;
        this.identifiantUser = identifiantUser;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_supprimer_utilisateur);

        exitButton = (Button) findViewById(R.id.btn_exit_supprimer_user_modal);
        confirmButton = (Button) findViewById(R.id.btn_confirmer_supprimer_user_modal);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerUtilisateur();
            }
        });
    }

    /**
     Permet de supprimer un utilisateur
     @param
     @return void
     */

    private void supprimerUtilisateur() {
        database.getReference("groupes/" + identifiantGroupe + "/users/" + identifiantUser).setValue(null);
        database.getReference("users/" + identifiantUser + "/groupe").setValue(null);
        Toast.makeText(c.getApplicationContext(), "L'utilisateur à été supprimé.", Toast.LENGTH_LONG).show();
        c.startActivity(new Intent(c, MainActivity.class));
        c.finish();
    }

}
