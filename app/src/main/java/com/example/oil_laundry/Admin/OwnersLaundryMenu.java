package com.example.oil_laundry.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.oil_laundry.LoginActivity;
import com.example.oil_laundry.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnersLaundryMenu extends AppCompatActivity {
    private FirebaseUser userLogIn;
    private DatabaseReference reff;
    private final String ADMIN="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_laundry_menu);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            //System.out.println("connect");
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
            //userName = userLogIn.getUid();
        }

    }

    public void turnsOrderedClick(View view) {
        Intent connect = new Intent(OwnersLaundryMenu.this, turnsOrderedAdmin.class);
        //   Bundle b =new Bundle();
        //b.putInt("AAA", 1);
        connect.putExtra("user", userName);
        startActivity(connect);
    }

    public void adminProfileClick(View view) {
        Intent connect = new Intent(OwnersLaundryMenu.this, adminProfile.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }


    public void adminDeliveryOrderClick(View view) {
        Intent connect = new Intent(OwnersLaundryMenu.this, adminDeliveryOrder.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}