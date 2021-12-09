package com.example.oil_laundry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(LoginActivity.this, WelcomActivity.class));
        }
    }


    public void register(View view) {
        EditText emailEditText =findViewById(R.id.EmailText);
        EditText passwordEditText =findViewById(R.id.PasswardText1);
        if(emailEditText.getText().toString().equals("ishaylevy8@gmail.com")&&passwordEditText.getText().toString().equals("123456")){
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(LoginActivity.this, OwnersLaundryMenu.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "register failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent connect = new Intent(LoginActivity.this, WelcomActivity.class);
                             //   Bundle b =new Bundle();
                                //b.putInt("AAA", 1);
                                String s=emailEditText.getText().toString();
                                connect.putExtra("user", s);
                                startActivity(connect);
                            } else {
                                Toast.makeText(LoginActivity.this, "register failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void login(View view) {
        EditText emailEditText =findViewById(R.id.EmailText);
        EditText passwordEditText =findViewById(R.id.PasswardText1);
        if(emailEditText.getText().toString().equals("ishaylevy8@gmail.com")&&passwordEditText.getText().toString().equals("123456")){            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, OwnersLaundryMenu.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "login failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent connect = new Intent(LoginActivity.this, WelcomActivity.class);
                                //   Bundle b =new Bundle();
                                //b.putInt("AAA", 1);
                                String s=emailEditText.getText().toString();
                                connect.putExtra("user", s);
                                startActivity(connect);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "login failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }
}