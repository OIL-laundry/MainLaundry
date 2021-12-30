package com.example.oil_laundry.Client;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oil_laundry.Adapters.OILCalendar;
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

        if(b!=null){

            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
            //userName = userLogIn.getUid();

        }
        //userName="liadnagi@gmail.com";
        cal = new OILCalendar();
        calendarService = (CalendarView)findViewById(R.id.calendarService);
        Time = (Spinner)findViewById(R.id.orderTime);
        Time.setOnItemSelectedListener(this);
        remarkText = (EditText) findViewById(R.id.editTextRemarks);
        //text1 = (TextView)findViewById(R.id.textCal);

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
                                //text1.setText(cal.toString());
                                //System.out.println(Time.getSelectedItemPosition());
                            }
                        });



    }

    public void previous(View view) {
        Intent connect = new Intent(makeAnAppointment.this, clientMain.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }
    public void main(View view) {

    }
    public void profile(View view) {
    }


    public void bookAppointment(View view) {
        cal.hour=Time.getSelectedItemPosition();
        String calendeId = cal.toString2();
        String remarkStr=remarkText.getText().toString();
        Task<DataSnapshot> it = reff.child("orders").child(ADMIN).child(calendeId).get();

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

                if(flag){
                    bubble();
//                    if(bubble()){
//                        reff.child("orders").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour);
//                        reff.child("orders").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour+"/user").setValue(userName);
//                        reff.child("orders").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour+"/remark").setValue(remarkStr);
//                        reff.child("orders").child(ADMIN).child(calendeId).child(""+cal.hour);
//                        reff.child("orders").child(ADMIN).child(calendeId).child(""+cal.hour+"/user").setValue(userName);
//                        reff.child("orders").child(ADMIN).child(calendeId).child(""+cal.hour+"/remark").setValue(remarkStr);
//                        Toast.makeText(makeAnAppointment.this, "Add order", Toast.LENGTH_LONG).show();
//                    }

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
    public boolean bubble(){
        AlertDialog.Builder builder = new AlertDialog.Builder(makeAnAppointment.this);
        builder.setMessage("Are you sure you want to make?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    public void buttonQueuesIBooked(View view) {
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

