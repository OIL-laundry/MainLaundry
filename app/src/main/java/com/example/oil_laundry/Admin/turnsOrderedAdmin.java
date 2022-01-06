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
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oil_laundry.Calsses.OILCalendar;
import com.example.oil_laundry.LoginActivity;
import com.example.oil_laundry.R;
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

        //get information from the previous page
        Bundle b = getIntent().getExtras();
        if(b!=null){
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
        }

        //get the CalendarView
        cal = new OILCalendar();
        calendarService = (CalendarView)findViewById(R.id.calendarServiceAdmin);
        tbl = (TableLayout)findViewById(R.id.tableOrders);

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
                                cal.hour=0;
                                //call to function createTable()
                                createTable();

                            }
                        });
    }



    private void createTable(){
        OILCalendar calendeId=cal;
        int size = tbl.getChildCount()-1;
        //clean the table
        while (size>0){
            tbl.removeViewAt(size--);
        }

        //get the data from firebase
        Query dateQuery = reff.child("orders").child(ADMIN).child(calendeId.toString2());
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String temp=singleSnapshot.getKey().toString();
                    String user = "  user: "+singleSnapshot.child("user").getValue().toString();
                    String rem  =singleSnapshot.child("remark").getValue().toString();
                    int i= Integer.parseInt(temp);
                    int j=(i+22)/2;
                    TableRow newRow = new TableRow(turnsOrderedAdmin.this);
                    // add views to the row
                    TextView t = new TextView(turnsOrderedAdmin.this);
                    if(i%2==0){
                        t.setText(""+j+":00 |   ");
                    }
                    else{
                        t.setText(""+j+":30 |   ");
                    }
                    TextView t2 = new TextView(turnsOrderedAdmin.this);
                    t2.setText((rem+"\n"+user));
                    t2.setMovementMethod(new ScrollingMovementMethod());
                    newRow.addView(t); // you would actually want to set properties on this before adding it

                    newRow.addView(t2);

                    // add the row to the table layout
                    tbl.addView(newRow);

                }



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
        Intent connect = new Intent(turnsOrderedAdmin.this, OwnersLaundryMenu.class);
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
                connect = new Intent(turnsOrderedAdmin.this, adminProfile.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item2:
                Toast.makeText(this, "MAKE AN APPOINTMENT selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(turnsOrderedAdmin.this, turnsOrderedAdmin.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item3:
                Toast.makeText(this, "DELIVERY ORDER selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(turnsOrderedAdmin.this, adminDeliveryOrder.class);
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