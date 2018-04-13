package com.uqac.wesplit;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.auth.LoginActivity;
import com.uqac.wesplit.dialogs.IdentifiantGroupeDialog;
import com.uqac.wesplit.fragments.DepensesFragment;
import com.uqac.wesplit.fragments.EquilibresFragment;
import com.uqac.wesplit.fragments.StatistiquesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private View headerView;
    private TextView headerName, headerEmail;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    private ImageButton buttonChat;
    private FloatingActionButton buttonAddDepense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération des éléments de la vue
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.mytabs);
        buttonAddDepense = (FloatingActionButton) findViewById(R.id.add_depense_button);
        buttonChat = (ImageButton) findViewById(R.id.btn_chat);
        headerName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_name);
        headerEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_email);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.nav_gestion_groupe:
                                startActivity(new Intent(MainActivity.this, GestionGroupeActivity.class));
                                break;
                            case R.id.nav_show_groupe:
                                IdentifiantGroupeDialog identifiantGroupeDialog = new IdentifiantGroupeDialog(MainActivity.this);
                                identifiantGroupeDialog.show();
                                break;
                            case R.id.nav_params:
                                startActivity(new Intent(MainActivity.this, ParamsActivity.class));
                                break;
                            case R.id.nav_deconnexion:
                                disconnect();
                                break;
                            default:
                                break;
                        }

                        return true;
                    }
                });

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {

            // Création de la vue

            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }

            DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid());

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String groupe = (String) dataSnapshot.child("groupe").getValue();

                    if(groupe == null || groupe.length() == 0) {
                        startActivity(new Intent(MainActivity.this, ChoixGroupeActivity.class));
                        finish();
                    } else {

                        setViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);

                        headerName.setText((String) dataSnapshot.child("name").getValue());
                        headerEmail.setText(auth.getCurrentUser().getEmail());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        }
        // Clic sur le bouton permettant d'aller sur l'activité d'inscription
        buttonAddDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AjoutDepenseActivity.class));
            }
        });

        // Clic sur le boutton permettant d'aller sur le chat
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });

    }

    @Override protected void onResume() {
        super.onResume();
        // refresh list?
        System.out.println("retour à mainactivity detecté");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DepensesFragment(), "Dépenses");
        adapter.addFragment(new EquilibresFragment(),"Équilibres");
        adapter.addFragment(new StatistiquesFragment(), "Statistiques");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void disconnect() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(), "Vous avez été déconnecté", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

}
