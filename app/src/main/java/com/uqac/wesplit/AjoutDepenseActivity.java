package com.uqac.wesplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.uqac.wesplit.enums.CategoriesEnum;

public class AjoutDepenseActivity extends AppCompatActivity implements OnItemSelectedListener {


    private EditText depenseTitre, depenseMontant;
    private Spinner spinnerCategories, spinnerPayePar;
    private Button btnConfirmer, btnBack;
    private CategoriesEnum depenseCategorieValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);

        depenseTitre = (EditText) findViewById(R.id.depense_titre);
        depenseMontant = (EditText) findViewById(R.id.depense_montant);
        spinnerCategories = (Spinner) findViewById(R.id.depense_categorie);
        btnConfirmer = (Button) findViewById(R.id.btn_ajout_depense);
        btnBack = (Button) findViewById(R.id.btn_annulation_ajout_depense);

        // Création de la liste des catégories de dépenses possibles
        spinnerCategories.setAdapter(new ArrayAdapter<CategoriesEnum>(this, android.R.layout.simple_spinner_dropdown_item, CategoriesEnum.values()));
        spinnerCategories.setOnItemSelectedListener(this);

        btnConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titre = depenseTitre.getText().toString().trim();
                String montant = depenseMontant.getText().toString().trim();

                if (TextUtils.isEmpty(titre)) {
                    Toast.makeText(getApplicationContext(), "Entrez un titre !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(montant)) {
                    Toast.makeText(getApplicationContext(), "Entrez un montant !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (depenseCategorieValue.toString() == null || TextUtils.isEmpty(depenseCategorieValue.toString())) {
                    Toast.makeText(getApplicationContext(), "Sélectionnez une catégorie de dépense !", Toast.LENGTH_SHORT).show();
                    return;
                }

                // @todo Ajout de la dépense dans firebase
                // quand on revient sur la mainactivity il faut que ça se refresh mais normalement
                // si on a bien implémenté la liste c'est automatique avec firebase
                startActivity(new Intent(AjoutDepenseActivity.this, MainActivity.class));
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AjoutDepenseActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.depense_categorie) {
            depenseCategorieValue = (CategoriesEnum) parent.getItemAtPosition(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
