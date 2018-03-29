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
import com.uqac.wesplit.ChoixGroupeActivity;
import com.uqac.wesplit.ConfirmationGroupeActivity;
import com.uqac.wesplit.LoginActivity;
import com.uqac.wesplit.R;
import com.uqac.wesplit.ResetPasswordActivity;

public class IdentifiantGroupeDialog extends Dialog {

    private Activity c;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private TextView identifiantGroupeText;
    private Button exitButton, copieButton;
    private ProgressBar progressBar;
    private String identifiantGroupe;

    public IdentifiantGroupeDialog(Activity a) {
        super(a);
        this.c = a;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_identifiant_groupe);

        exitButton = (Button) findViewById(R.id.btn_exit_identifiant_modal);
        copieButton = (Button) findViewById(R.id.btn_copier_identifiant);
        identifiantGroupeText = (TextView) findViewById(R.id.txt_identifiant_groupe);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setIdentifiantGroupe();

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        copieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("identifiant", identifiantGroupe);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(c.getApplicationContext(), "Identifiant copié dans le presse papier", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setIdentifiantGroupe() {

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                identifiantGroupe = (String) dataSnapshot.child("groupe").getValue();
                identifiantGroupeText.setText(identifiantGroupe);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
