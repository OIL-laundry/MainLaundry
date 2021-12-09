package com.example.oil_laundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView;
import android.widget.CalendarView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView im1;
    CalendarView  calendarService;
    OILCalendar cal;
    Spinner Time;

    TextView text1;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            System.out.println("connect");
            userName = (String)b.getString("user");
        }

        cal = new OILCalendar(0,0,0,0);
        calendarService = (CalendarView)findViewById(R.id.calendarService);
        Time = (Spinner)findViewById(R.id.orderTime);
        Time.setOnItemSelectedListener(this);
        text1 = (TextView)
                findViewById(R.id.textCal);
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
                                cal.day= dayOfMonth;
                                cal.month = (month + 1);
                                cal.year = year;
                                text1.setText(userName);
                            }
                        });



    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    public void main(View view) {

    }
    public void profile(View view) {
    }
    public void bookAppointment(View view) {
    }
    public void buttonQueuesIBooked(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        text1.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class OILCalendar {

        int day,month,year,hour;

        private OILCalendar(int day, int month, int year, int hour){
            this.day=day;
            this.month=month;
            this.year=year;
            this.hour=hour;
        }

        @Override
        public String toString(){
            return (day+"/"+month+"/"+year+" "+hour+":00");
        }

    }
}

