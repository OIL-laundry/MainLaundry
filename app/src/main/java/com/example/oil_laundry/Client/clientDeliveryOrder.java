package com.example.oil_laundry.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class clientDeliveryOrder extends AppCompatActivity {
    private FirebaseUser userLogIn;
    private DatabaseReference reff;
    private final String ADMIN="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private String userName;
    private final long NUM_OF_MESSENGER=3;

    CalendarView calendarService;
    OILCalendar cal;
    Spinner Time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_delivery_order);
        Bundle b = getIntent().getExtras();

        if(b!=null){
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
        }

        cal = new OILCalendar();
        calendarService = (CalendarView)findViewById(R.id.calendarService2);
        Time = (Spinner)findViewById(R.id.orderTime2);

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


    public void bookDelivery(View view) {
        cal.hour=Time.getSelectedItemPosition();
        String calendeId = cal.toString2();

        Task<DataSnapshot> it = reff.child("Delivery").child(ADMIN).child(calendeId).get();

        Query dateQuery = reff.child("Delivery").child(ADMIN).child(calendeId);
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long flag = 0;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String temp=""+cal.hour;
                    if(singleSnapshot.getKey().toString().equals(temp)){
                        flag =singleSnapshot.child("users").getChildrenCount()+1;
                    }
                }

                System.out.println(flag);
                if(flag<NUM_OF_MESSENGER){
                    reff.child("Delivery").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour);
                    reff.child("Delivery").child(userLogIn.getUid()).child(calendeId).child(""+cal.hour+"/users").push().setValue(userName);
                    reff.child("Delivery").child(ADMIN).child(calendeId).child(""+cal.hour);
                    reff.child("Delivery").child(ADMIN).child(calendeId).child(""+cal.hour+"/users").push().setValue(userName);
                    Toast.makeText(clientDeliveryOrder.this, "Add delivery", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(clientDeliveryOrder.this, "Already exists, please select another time", Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.e("aaa", "onCancelled", databaseError.toException());
            }
        });









    }



    public void previous(View view) {
        Intent connect = new Intent(clientDeliveryOrder.this, clientMain.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }



}