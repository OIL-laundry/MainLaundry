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

import com.example.oil_laundry.Adapters.OILCalendar;
import com.example.oil_laundry.Client.clientDeliveryOrder;
import com.example.oil_laundry.Client.clientProfile;
import com.example.oil_laundry.Client.makeAnAppointment;
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

public class adminDeliveryOrder extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_delivery_order);
        Bundle b = getIntent().getExtras();

        if(b!=null){
            //System.out.println("connect");
            userName = (String)b.getString("user");
            userLogIn = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference();
            //userName = userLogIn.getUid();
        }
        cal = new OILCalendar();
        calendarService = (CalendarView)findViewById(R.id.calendarServiceAdmin2);
        tbl = (TableLayout)findViewById(R.id.tableOrders);

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

        //tbl.removeViewAt(1);
        int size = tbl.getChildCount()-1;
        while (size>0){
            tbl.removeViewAt(size--);
        }
        Query dateQuery = reff.child("Delivery").child(ADMIN).child(calendeId.toString2());
        dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String temp=singleSnapshot.getKey().toString();
                    //Iterator it = singleSnapshot.getChildren().iterator();

                    String user="";

                    for(DataSnapshot singleSnapshot2 : singleSnapshot.child("users").getChildren()){
                        user+= singleSnapshot2.getValue().toString()+"\n";
                    }
                    //System.out.println(user);
                    //String rem  =singleSnapshot.child("remark").getValue().toString();
                    //System.out.println("user = "+ user);
                    //System.out.println("rem = "+ rem);
                    int i= Integer.parseInt(temp);
                    int j=(i+22)/2;
                    TableRow newRow = new TableRow(adminDeliveryOrder.this);
                    // add views to the row
                    TextView t = new TextView(adminDeliveryOrder.this);
                    if(i%2==0){
                        t.setText(""+j+":00 |   ");
                    }
                    else{
                        t.setText(""+j+":30 |   ");
                    }
                    TextView t2 = new TextView(adminDeliveryOrder.this);
                    t2.setText((user));
                    t2.setMovementMethod(new ScrollingMovementMethod());
                    newRow.addView(t); // you would actually want to set properties on this before adding it

                    newRow.addView(t2);




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
        Intent connect = new Intent(adminDeliveryOrder.this, OwnersLaundryMenu.class);
        connect.putExtra("user", userName);
        startActivity(connect);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent connect;
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(adminDeliveryOrder.this, adminProfile.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item2:
                Toast.makeText(this, "MAKE AN APPOINTMENT selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(adminDeliveryOrder.this, turnsOrderedAdmin.class);
                connect.putExtra("user", userName);
                startActivity(connect);
                return true;
            case R.id.item3:
                Toast.makeText(this, "DELIVERY ORDER selected", Toast.LENGTH_SHORT).show();
                connect = new Intent(adminDeliveryOrder.this, adminDeliveryOrder.class);
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