package com.uqac.wesplit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.adapters.UserAdapter;
import com.uqac.wesplit.adapters.UserCheckboxAdapter;
import com.uqac.wesplit.enums.CategoriesEnum;
import com.uqac.wesplit.adapters.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AjoutDepenseActivity extends AppCompatActivity {

    private Context activity;
    private EditText depenseTitre, depenseMontant;
    private Spinner spinnerCategories, spinnerPayePar;
    private Button btnConfirmer;
    private ImageButton btnBack;
    private ListView listUsersCheckbox;
    private CategoriesEnum depenseCategorieValue;
    private User payePar;
    private ArrayList<User> userList;
    private ArrayAdapter<User> adapter;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);
        activity = this;

        // Récupération des éléments de la vue
        depenseTitre = (EditText) findViewById(R.id.depense_titre);
        depenseMontant = (EditText) findViewById(R.id.depense_montant);
        spinnerCategories = (Spinner) findViewById(R.id.depense_categorie);
        spinnerPayePar = (Spinner) findViewById(R.id.depense_payepar);
        btnConfirmer = (Button) findViewById(R.id.btn_ajout_depense);
        btnBack = (ImageButton) findViewById(R.id.btn_depense_retour);
        listUsersCheckbox = (ListView) findViewById(R.id.listview_users_checkbox);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userList = new ArrayList<>();

        // Création de la liste des catégories de dépenses possibles
        spinnerCategories.setAdapter(new ArrayAdapter<CategoriesEnum>(this, android.R.layout.simple_spinner_dropdown_item, CategoriesEnum.values()));

        DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid() + "/groupe");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference ref = database.getReference("groupes/" + dataSnapshot.getValue() + "/users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Map<String, String> usersGroupe = (Map<String, String>) dataSnapshot.getValue();
                        userList = new ArrayList<User>();
                        int positionMoi = 0;
                        int i = 0;
                        for (Map.Entry<String, String> entry : usersGroupe.entrySet()) {
                            userList.add(new User(entry.getKey(), entry.getValue()));
                            if(entry.getKey().equals(auth.getCurrentUser().getUid())) {
                                positionMoi = i;
                            }
                            i++;
                        }

                        ArrayAdapter<User> spinnerAdapter = new ArrayAdapter<User>(activity, android.R.layout.simple_spinner_item, userList);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPayePar.setAdapter(spinnerAdapter);
                        spinnerPayePar.setSelection(positionMoi);

                        adapter = new UserCheckboxAdapter(AjoutDepenseActivity.this, R.layout.row_list_users_checkbox, userList);
                        listUsersCheckbox.setAdapter(adapter);

                        ViewGroup.LayoutParams params = listUsersCheckbox.getLayoutParams();
                        params.height = userList.size() * 102;
                        listUsersCheckbox.setLayoutParams(params);
                        listUsersCheckbox.requestLayout();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Ajout d'une dépense
        btnConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String titre = depenseTitre.getText().toString().trim();
                final String montant = depenseMontant.getText().toString().trim();

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

                DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid());

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nomGroupe = (String) dataSnapshot.child("groupe").getValue();
                        String nameUser = (String) dataSnapshot.child("name").getValue();

                        if(nomGroupe != null && nameUser != null) {

                            DatabaseReference ref = database.getReference("groupes/" + nomGroupe + "/depenses");

                            String key = ref.push().getKey();
                            ref.child(key).child("titre").setValue(titre);
                            ref.child(key).child("montant").setValue(montant);
                            ref.child(key).child("categorie").setValue(depenseCategorieValue.toString());
                            // @todo rendre le payeur variable (pouvoir choisir le user qui paye)
                            ref.child(key).child("payeparid").setValue(payePar.getIdentifiant());
                            ref.child(key).child("payeparname").setValue(payePar.getName());
                            ref.child(key).child("timestamp").setValue(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "");

                            List<User> list = userList;

                            for(User user : userList) {
                                if(user.isSelected()) {
                                    ref.child(key).child("users").child(user.getIdentifiant()).setValue(user.getIdentifiant());
                                }
                            }

                            startActivity(new Intent(AjoutDepenseActivity.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // @todo toast
                    }
                });
            }
        });

        listUsersCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                User user = (User) parent.getItemAtPosition(position);

                CheckBox checkBox = view.findViewById(R.id.checkbox_user);

                if(checkBox.isChecked()) {
                    userList.get(userList.indexOf(user)).setSelected(true);
                } else {
                    userList.get(userList.indexOf(user)).setSelected(false);
                }

                Toast.makeText(getApplicationContext(),
                        "Clicked on: " + user.getIdentifiant(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // Retour à l'activité principale
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AjoutDepenseActivity.this, MainActivity.class));
                finish();
            }
        });

        spinnerCategories.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                depenseCategorieValue = (CategoriesEnum) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        spinnerPayePar.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                payePar = (User) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

    }

}
