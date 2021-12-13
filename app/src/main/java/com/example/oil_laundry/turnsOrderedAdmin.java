package com.example.oil_laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class turnsOrderedAdmin extends AppCompatActivity {

    private FirebaseUser userLogIn;
    private DatabaseReference reff;
    private final String ADMIN="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turns_ordered_admin);

        Bundle b = getIntent().getExtras();

        if(b!=null){
            //System.out.println("connect");
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
            //userName = userLogIn.getUid();

        }
    }


    public void previous(View view) {
        Intent connect = new Intent(turnsOrderedAdmin.this, OwnersLaundryMenu.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }
}