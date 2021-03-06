package com.example.oil_laundry.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oil_laundry.Client.makeAnAppointment;
import com.example.oil_laundry.LoginActivity;
import com.example.oil_laundry.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class adminProfile extends AppCompatActivity {

    private FirebaseUser userLogIn;
    private DatabaseReference reff;
    private final String ADMIN="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private String userName;

    TextView emailText;
    EditText firstText;
    EditText lastText;
    EditText phoneText;
    EditText cityText;
    EditText streetText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        Bundle b = getIntent().getExtras();

        //get information from the previous page
        if(b!=null){
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
        }

        //get all the Views
        emailText = (TextView)findViewById(R.id.EmailTextAdmin);
        emailText.setText(userName);
        firstText = (EditText)findViewById(R.id.editTextTextPersonName2);
        lastText = (EditText)findViewById(R.id.editTextTextPersonName3);
        phoneText = (EditText)findViewById(R.id.editTextTextPersonName4);
        cityText = (EditText)findViewById(R.id.editTextTextPersonName5);
        streetText = (EditText)findViewById(R.id.editTextTextPersonName6);

        //call to fillList() function
        fillList();
    }


    /*
    fill all the data of the admin
     */
    public void fillList() {
        Query dateQuery = reff.child("Profile").child(ADMIN);
        //
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("First").getValue()!=null){
                    firstText.setText(dataSnapshot.child("First").getValue().toString());
                }
                if(dataSnapshot.child("Last").getValue()!=null){
                    lastText.setText(dataSnapshot.child("Last").getValue().toString());
                }
                if(dataSnapshot.child("Phone").getValue()!=null){
                    phoneText.setText(dataSnapshot.child("Phone").getValue().toString());
                }
                if(dataSnapshot.child("City").getValue()!=null){
                    cityText.setText(dataSnapshot.child("City").getValue().toString());
                }
                if(dataSnapshot.child("Street").getValue()!=null){
                    streetText.setText(dataSnapshot.child("Street").getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    /*
    save the new data on click
     */
    public void saveClick(View view) {
        String first = firstText.getText().toString();
        String last = lastText.getText().toString();
        String phone = phoneText.getText().toString();
        String city = cityText.getText().toString();
        String street = streetText.getText().toString();

        //add  all the new values
        Query dateQuery = reff.child("Profile").child(ADMIN);
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reff.child("Profile").child(ADMIN).child("First").setValue(first);
                reff.child("Profile").child(ADMIN).child("Last").setValue(last);
                reff.child("Profile").child(ADMIN).child("Phone").setValue(phone);
                reff.child("Profile").child(ADMIN).child("City").setValue(city);
                reff.child("Profile").child(ADMIN).child("Street").setValue(street);
                Toast.makeText(adminProfile.this, "Save", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    /*
     go back on click
     */
    public void previous(View view) {
        Intent connect = new Intent(adminProfile.this, OwnersLaundryMenu.class);
        connect.putExtra("user", userName);
        startActivity(connect);
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
                connect = new Intent(adminProfile.this, adminProfile.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item2:
                Toast.makeText(this, "MAKE AN APPOINTMENT selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(adminProfile.this, turnsOrderedAdmin.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item3:
                Toast.makeText(this, "DELIVERY ORDER selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(adminProfile.this, adminDeliveryOrder.class);
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