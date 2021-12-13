package com.example.oil_laundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class turnsOrderedAdmin extends AppCompatActivity {

    private FirebaseUser userLogIn;
    private DatabaseReference reff;
    private final String ADMIN="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private String userName;
    CalendarView calendarService;
    OILCalendar cal;
    TableLayout tbl;


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
        cal = new OILCalendar();
        calendarService = (CalendarView)findViewById(R.id.calendarServiceAdmin);
        tbl = (TableLayout)findViewById(R.id.tableOrders);
/*
        for(int i=0;i<3;i++){
            TableRow newRow = new TableRow(this);
            // add views to the row
            TextView t =new TextView(this);
            t.setText(""+i);
            newRow.addView(t); // you would actually want to set properties on this before adding it

// add the row to the table layout
            tbl.addView(newRow);
        }*/

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
                                cal.hour=0;
                                createTable();





                            }
                        });
    }



    private void createTable(){
        OILCalendar calendeId=cal;
        tbl.removeAllViews();
        Query dateQuery = reff.child("orders").child(ADMIN).child(calendeId.toString2());
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String temp=singleSnapshot.getValue().toString();
                    int i= Integer.parseInt(temp);
                    int j=(i+22)/2;
                    TableRow newRow = new TableRow(turnsOrderedAdmin.this);
                    // add views to the row
                    TextView t = new TextView(turnsOrderedAdmin.this);
                    if(i%2==0){
                        t.setText(""+j+":00");
                    }
                    else{
                        t.setText(""+j+":30");
                    }

                    newRow.addView(t); // you would actually want to set properties on this before adding it

// add the row to the table layout
                    tbl.addView(newRow);

                }
                    //Toast.makeText(turnsOrderedAdmin.this, "Add order", Toast.LENGTH_LONG).show();



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.e("aaa", "onCancelled", databaseError.toException());
            }
        });

    }


    public void previous(View view) {
        Intent connect = new Intent(turnsOrderedAdmin.this, OwnersLaundryMenu.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }
}