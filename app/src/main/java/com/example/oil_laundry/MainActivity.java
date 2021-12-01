package com.example.oil_laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText userName;
    EditText Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.UserNameText);
        Password = findViewById(R.id.PasswardText);
    }

    public void onClickCreate(View view) {
        Intent a = new Intent(MainActivity.this, Main.class);
        startActivity(a);
    }

    public void onClickConnect(View view) {
        Intent a = new Intent(MainActivity.this, LaundryActivity.class);
        Bundle b =new Bundle();
        //b.putInt("AAA", 1);
        a.putExtra("aaa", 1);
        startActivity(a);
        //finish();

    }
}