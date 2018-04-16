package com.uqac.wesplit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 Activité permettant de paramétrer son compte
 */

public class ParamsActivity extends AppCompatActivity {

    private EditText inputEmail, inputNewPassword, inputPasswordConfirmation, inputOldPassword;
    private ImageButton btnRetour;
    private Button btnEdit;
    private ProgressBar progressBar;

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
        inputOldPassword = (EditText) findViewById(R.id.oldPassword);
        inputNewPassword = (EditText) findViewById(R.id.newPassword);
        inputPasswordConfirmation = (EditText) findViewById(R.id.passwordConfirmation);
        btnRetour = (ImageButton) findViewById(R.id.btn_params_retour);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        // Clic sur le bouton permettant de modifier les informations du compte
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changeEmail = false;
                Boolean changePassword = true;

                final String email = inputEmail.getText().toString();
                final String oldPassword = inputOldPassword.getText().toString();
                final String newPassword = inputNewPassword.getText().toString();
                final String passwordConfirmation = inputPasswordConfirmation.getText().toString();

                final FirebaseUser user = auth.getInstance().getCurrentUser();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Entrez une adresse email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!email.equals(user.getEmail())) changeEmail = true;

                if (TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(getApplicationContext(), "Entrez votre ancien motde passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(newPassword) && TextUtils.isEmpty(passwordConfirmation)) {
                    changePassword = false;
                } else {
                    if (!newPassword.equals(passwordConfirmation)) {
                        Toast.makeText(getApplicationContext(), "Les 2 nouveaux mots de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(newPassword) || newPassword.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Entrez un nouveau mot de passe de 6 caractères ou plus", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }




                progressBar.setVisibility(View.VISIBLE);


                //On doit réauthentifier l'user pour modifier ses infos (sinon erreur firebase)
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

                if(changePassword) {
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Mot de passe mis à jour", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Erreur mot de passe non changé", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Ancien mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                if(changeEmail) {
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Adresse mail mise à jour", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Erreur, adresse mail non changée", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Ancien mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        // A FAIRE:

        // modifier mon adresse mail (a faire avec les fonctions de firebase)
        // modifier mon mot de passe (a faire avec les fonctions de firebase)
        // supprimer mon compte (a faire avec les fonctions de firebase + supprimer user dans la bdd + supprimer user dans le groupe associé)

        // Retour à l'activité principale
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParamsActivity.this, MainActivity.class));
                finish();
            }
        });


    }
}
