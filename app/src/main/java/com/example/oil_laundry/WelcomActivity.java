package com.example.oil_laundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CalendarView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomActivity extends AppCompatActivity {

    ImageView im1;
    CalendarView  calendarService;
    OILCalendar cal;
    TextView text1;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            System.out.println("connect");
            userName = (String)b.get("user");
        }

        cal = new OILCalendar(0,0,0,0);
        calendarService = (CalendarView)findViewById(R.id.calendarService);
        text1 = (TextView)
                findViewById(R.id.textCal);
        calendarService
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                    cal.day= dayOfMonth;
                                    cal.month = (month + 1);
                                    cal.year = year;

                                // set this date in TextView for Display
                                text1.setText(userName);
                                //date_view.setText(Date);
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

