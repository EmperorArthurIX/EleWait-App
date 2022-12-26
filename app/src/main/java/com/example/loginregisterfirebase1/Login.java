package com.example.loginregisterfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-b7bea-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText Amizoneid = findViewById(R.id.Email);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.Loginbtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String amizoneidtxt = Amizoneid.getText().toString();
                final String passwordTxt = password.getText().toString();
                if (amizoneidtxt.isEmpty()|| passwordTxt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your Amizone id or password", Toast.LENGTH_SHORT).show();
                }
                else if (amizoneidtxt.length()<7 || amizoneidtxt.length()>8){
                    Toast.makeText(Login.this, "Amizone id is 7-8 digits only", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("users"))
                            {
                            if(snapshot.child("users").hasChild(amizoneidtxt))
                            {
                                final String correctpassword = snapshot.child("users").child(amizoneidtxt).child("password").getValue(String.class);
                                if(correctpassword.equals(passwordTxt))
                                {
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                }
                                else
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Login.this, "Amizone ID is not registered", Toast.LENGTH_SHORT).show();
                        }
                            else
                                Toast.makeText(Login.this, "Database Empty", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });
    }
}