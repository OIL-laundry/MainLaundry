package com.example.oil_laundry.Client;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oil_laundry.Calsses.OILCalendar;
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

public class makeAnAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseUser userLogIn;
    private DatabaseReference reff;
    private final String ADMIN="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private String userName;
    ImageView im1;
    CalendarView  calendarService;
    OILCalendar cal;
    Spinner Time;
    EditText remarkText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_an_appointment);
        Bundle b = getIntent().getExtras();

        //get information from the previous page
        if(b!=null){
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
        }

        //get the CalendarView and remarkText
        cal = new OILCalendar();
        calendarService = (CalendarView)findViewById(R.id.calendarService);
        Time = (Spinner)findViewById(R.id.orderTime);
        Time.setOnItemSelectedListener(this);
        remarkText = (EditText) findViewById(R.id.editTextRemarks);

        //listener on calendar change
        calendarService
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {
                                cal.day=(dayOfMonth);
                                cal.month=((month + 1));
                                cal.year=(year);
                                cal.hour=Time.getSelectedItemPosition();
                            }
                        });



    }

    /*
    go back on click
    */
    public void previous(View view) {
        Intent connect = new Intent(makeAnAppointment.this, clientMain.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }
    public void main(View view) {

    }
    public void profile(View view) {
    }


    /*
    add a appointment to fire base
    */
    public void bookAppointment(View view) {
        cal.hour=Time.getSelectedItemPosition();
        String calendeId = cal.toString2();
        String remarkStr=remarkText.getText().toString();
        Task<DataSnapshot> it = reff.child("orders").child(ADMIN).child(calendeId).get();
        //get the data from firebase
        Query dateQuery = reff.child("orders").child(ADMIN).child(calendeId);
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag = true;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String temp=""+cal.hour;
                    if(singleSnapshot.getKey().toString().equals(temp)){
                        flag =false;
                    }
                }
                //If the date is available by the hour
                if(flag){
                    //call bubble() function
                    bubble();
                }
                else{
                    Toast.makeText(makeAnAppointment.this, "Already exists, please select another time", Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.e("aaa", "onCancelled", databaseError.toException());
            }
        });
    }

    /*
    create dialog and add the appointment if "Yes"
     */
    public boolean bubble(){
        AlertDialog.Builder builder = new AlertDialog.Builder(makeAnAppointment.this);
        builder.setMessage("Are you sure you want to make?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //call createAnAppointment() function
                        createAnAppointment();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }


    /*
    add to fire base
     */
    private void createAnAppointment(){
        cal.hour=Time.getSelectedItemPosition();
        String calendeId = cal.toString2();
        String remarkStr=remarkText.getText().toString();
        reff.child("orders").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour);
        reff.child("orders").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour+"/user").setValue(userName);
        reff.child("orders").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour+"/remark").setValue(remarkStr);
        reff.child("orders").child(ADMIN).child(calendeId).child(""+cal.hour);
        reff.child("orders").child(ADMIN).child(calendeId).child(""+cal.hour+"/user").setValue(userName);
        reff.child("orders").child(ADMIN).child(calendeId).child(""+cal.hour+"/remark").setValue(remarkStr);
        Toast.makeText(makeAnAppointment.this, "Add order", Toast.LENGTH_LONG).show();
    }



    public void buttonQueuesIBooked(View view) {
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
                connect = new Intent(makeAnAppointment.this, clientProfile.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item2:
                Toast.makeText(this, "MAKE AN APPOINTMENT selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(makeAnAppointment.this, makeAnAppointment.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item3:
                Toast.makeText(this, "DELIVERY ORDER selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(makeAnAppointment.this, clientDeliveryOrder.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //text1.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}


