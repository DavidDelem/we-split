package com.uqac.wesplit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uqac.wesplit.dialogs.IdentifiantGroupeDialog;

public class GestionGroupeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_groupe);

        // A FAIRE:

        // afficher le nom du groupe en haut

        // bouton afficher l'identifiant du groupe (ouvre la modal avec le code suivant:)
//        IdentifiantGroupeDialog identifiantGroupeDialog = new IdentifiantGroupeDialog(MainActivity.this);
//        identifiantGroupeDialog.show();

        // bouton renommer le groupe (ouvre une fenêtre popup pour choisir)
        // bouton quitter le groupe (ouvre une fenêtre popup pour confirmer)
        // bouton supprimer le groupe (doit supprimer le groupe mais aussi la valeur de la variable "groupe" dans chaque user associé à ce groupe

        // liste des autres membres du groupe avec possibilité de supprimer chaque membre (fenêtre popup pour confirmer)
    }
}
