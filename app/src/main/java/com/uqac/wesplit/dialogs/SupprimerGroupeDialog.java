package com.uqac.wesplit.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.LoginActivity;
import com.uqac.wesplit.MainActivity;
import com.uqac.wesplit.R;
import com.uqac.wesplit.helpers.User;

import java.util.ArrayList;
import java.util.Map;

public class SupprimerGroupeDialog extends Dialog {

    private Activity c;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Button exitButton, confirmButton;
    private ProgressBar progressBar;
    private String identifiantGroupe;

    public SupprimerGroupeDialog(Activity a, String identifiantGroupe) {
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
        setContentView(R.layout.dialog_supprimer_groupe);

        exitButton = (Button) findViewById(R.id.btn_exit_supprimer_modal);
        confirmButton = (Button) findViewById(R.id.btn_confirmer_supprimer_modal);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("identifiant", identifiantGroupe);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(c.getApplicationContext(), "Identifiant copié dans le presse papier", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void supprimerGroupe() {

        //progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = database.getReference("groupes/" + identifiantGroupe + "/users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> usersGroupe = (Map<String, String>) dataSnapshot.getValue();

                for (Map.Entry<String, String> entry : usersGroupe.entrySet()) {
                    database.getReference("users/" + entry.getKey() + "/groupe").setValue(null);
                }
                database.getReference("groupes/" + identifiantGroupe).setValue(null);
                disconnect();
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void disconnect() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(c.getApplicationContext(), "Le groupe a été définitivement supprimé. Vous avez été déconnecté.", Toast.LENGTH_LONG).show();
        c.startActivity(new Intent(c, LoginActivity.class));
        c.finish();
    }

}
