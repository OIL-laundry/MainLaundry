package com.example.oil_laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class clientDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_dashboard);
        Bundle b = getIntent().getExtras();

        TextView Text = (TextView)findViewById(R.id.Hello);

        if(b!=null){
            int v=b.getInt("aaa");
            Text.setText(Integer.toString(v));
        }


    }
}