package com.example.oil_laundry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oil_laundry.Admin.OwnersLaundryMenu;
import com.example.oil_laundry.Client.clientMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final String ADMIN_NAME="admin@gmail.com";
    private final String ADMIN_ID="OcRpZqiVKkTMP2aOWI3LtQe13ZE3";
    private final String ADMIN_PASS="7777777";
    private FirebaseUser userLogIn;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
       super.onStart();

    }

    /*
            Add a new user
     */
    public void register(View view) {
        emailEditText = findViewById(R.id.EmailText);
        passwordEditText = findViewById(R.id.PasswardText1);

        //check if empty
        if(emailEditText.getText().toString().equals("")||passwordEditText.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, "register failed :(", Toast.LENGTH_LONG).show();
        }
        else {//add new user if not exists
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent connect = new Intent(LoginActivity.this, clientMain.class);

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

    /*
    user login
     */
    public void login(View view) {
        emailEditText =findViewById(R.id.EmailText);
        passwordEditText =findViewById(R.id.PasswardText1);
        String emailLowCase = emailEditText.getText().toString().toLowerCase();

        //check if empty
        if(emailEditText.getText().toString().equals("")||passwordEditText.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, "login failed :(", Toast.LENGTH_LONG).show();

        }
        //check if admin then connect to the admin main
        else if(emailLowCase.equals(ADMIN_NAME)&&passwordEditText.getText().toString().equals(ADMIN_PASS)){

            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent connect = new Intent(LoginActivity.this, OwnersLaundryMenu.class);
                                String s=emailEditText.getText().toString();
                                connect.putExtra("user", s);
                                startActivity(connect);
                            } else {
                                Toast.makeText(LoginActivity.this, "login failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        //check if client then connect to the client main
        else {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent connect = new Intent(LoginActivity.this, clientMain.class);
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