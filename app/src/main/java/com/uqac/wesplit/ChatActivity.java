package com.uqac.wesplit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.wesplit.enums.CategoriesEnum;
import com.uqac.wesplit.helpers.Depense;
import com.uqac.wesplit.helpers.Message;
import com.uqac.wesplit.helpers.MessageAdapter;
import com.uqac.wesplit.helpers.User;
import com.uqac.wesplit.helpers.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Context activity;
    private EditText message;
    private Button btnEnvoyer;
    private ImageButton btnBack;
    private ListView listMessages;
    private ArrayAdapter<Message> adapter;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activity = this;

        message = (EditText) findViewById(R.id.message);
//        btnEnvoyer = (Button) findViewById(R.id.btn_chat_envoyer);
        btnBack = (ImageButton) findViewById(R.id.btn_chat_retour);
        listMessages = (ListView) findViewById(R.id.listview_messages);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        final ArrayList<Message> messages = new ArrayList<>();

        adapter = new MessageAdapter(ChatActivity.this, R.layout.row_list_messages, messages);
        listMessages.setAdapter(adapter);

        DatabaseReference ref = database.getReference("users/" + auth.getCurrentUser().getUid() + "/groupe");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference ref = database.getReference("groupes/" + (String) dataSnapshot.getValue() + "/messages");

                ref.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Message message = dataSnapshot.getValue(Message.class);
                        message.set_id(dataSnapshot.getKey());
                        messages.add(message);
                        adapter.notifyDataSetChanged();
//                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Retour à l'activité principale
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, MainActivity.class));
                finish();
            }
        });

        // Retour à l'activité principale
//        btnEnvoyer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////
//            }
//        });
    }
}
