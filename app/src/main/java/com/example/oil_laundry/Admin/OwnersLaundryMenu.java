package com.example.oil_laundry.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

        //get information from the previous page
        Bundle b = getIntent().getExtras();
        if(b!=null){
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
        }

    }

    // go to "turnsOrderedAdmin" on click and set new Bundle
    public void turnsOrderedClick(View view) {
        Intent connect = new Intent(OwnersLaundryMenu.this, turnsOrderedAdmin.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }

    // go to "adminProfile" on click and set new Bundle
    public void adminProfileClick(View view) {
        Intent connect = new Intent(OwnersLaundryMenu.this, adminProfile.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }

    // go to "adminDeliveryOrder" on click and set new Bundle
    public void adminDeliveryOrderClick(View view) {
        Intent connect = new Intent(OwnersLaundryMenu.this, adminDeliveryOrder.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }

    // logout and exit
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /*
    add the toolbar to the page
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    /*
    toolbar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent connect;
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(OwnersLaundryMenu.this, adminProfile.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item2:
                Toast.makeText(this, "MAKE AN APPOINTMENT selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(OwnersLaundryMenu.this, turnsOrderedAdmin.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item3:
                Toast.makeText(this, "DELIVERY ORDER selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(OwnersLaundryMenu.this, adminDeliveryOrder.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item4:
                Toast.makeText(this, "LOGOUT selected", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}