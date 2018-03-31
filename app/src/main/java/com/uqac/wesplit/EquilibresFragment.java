package com.uqac.wesplit;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.helpers.CalculateurEquilibre;
import com.uqac.wesplit.helpers.Depense;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EquilibresFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private CalculateurEquilibre calculateurEquilibre;
    private LinearLayout ll;
    Map<String, String> usersGroupe;

    public EquilibresFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equilibres, container, false);

        ll = (LinearLayout) view.findViewById(R.id.view_eq);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        calculateurEquilibre = new CalculateurEquilibre();

        DatabaseReference refUser = database.getReference("users/" + auth.getCurrentUser().getUid());

        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String groupe = (String) dataSnapshot.child("groupe").getValue();

                DatabaseReference refUsersGroupe = database.getReference("groupes/" + groupe + "/users");

                refUsersGroupe.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        usersGroupe = (HashMap<String, String>) dataSnapshot.getValue();

                        for (String identifiant : usersGroupe.values()) {
                            calculateurEquilibre.ajouterUtilsateur(identifiant);
                        }

                        DatabaseReference refDepensesGroupe = database.getReference("groupes/" + groupe + "/depenses");

                        refDepensesGroupe.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // récupérer dépenses
                                Map<String, Map<String, String>> depensesGroupe = (Map<String, Map<String, String>>) dataSnapshot.getValue();
                                calculateurEquilibre.ajouterDepenses(depensesGroupe);

                                Iterator it = calculateurEquilibre.getUtilisateurDepense().entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pair1 = (Map.Entry)it.next();
                                    Map.Entry pair2 = (Map.Entry) (it.hasNext() ? it.next() : null);

                                    View equilibreElem = LayoutInflater.from(getContext()).inflate(R.layout.row_equilibre, ll, false);


                                    TextView textView = (TextView) equilibreElem.findViewById(R.id.equilibre_montant_1);
                                    TextView textView2 = (TextView) equilibreElem.findViewById(R.id.equilibre_user_1);
                                    TextView textView3 = (TextView) equilibreElem.findViewById(R.id.equilibre_montant_2);
                                    TextView textView4 = (TextView) equilibreElem.findViewById(R.id.equilibre_user_2);

                                    String depense1 = calculateurEquilibre.getEquilibreUtilisateur((String) pair1.getKey());
                                    String depense2 = "";
                                    if(pair2 != null) {
                                        depense2 = calculateurEquilibre.getEquilibreUtilisateur((String) pair2.getKey());
                                    }

                                    if(Float.parseFloat(depense1) >= 0.0f ) {
                                        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_green));
                                        textView.setText("+" + depense1 + "$");
                                    } else {
                                        textView.setText(depense1 + "$");
                                    }
                                    textView2.setText(pair1.getKey() + "");


                                    if(pair2 != null) {
                                        if(Float.parseFloat(depense2) >= 0.0f ) {
                                            textView3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_green));
                                            textView3.setText("+" + depense2 + "$");
                                        } else {
                                            textView3.setText(depense2 + "$");
                                        }
                                        textView4.setText(pair2.getKey() + "");
                                    } else {
                                        textView3.setVisibility(View.INVISIBLE);
                                        textView4.setVisibility(View.INVISIBLE);
                                    }

                                    ll.addView(equilibreElem);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return view;
    }
}
