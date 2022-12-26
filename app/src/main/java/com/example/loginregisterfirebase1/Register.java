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

public class Register extends AppCompatActivity {

    DatabaseReference dbref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-b7bea-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText amizoneid = findViewById(R.id.amizoneid);
        final EditText Email = findViewById(R.id.Email);
        final EditText password = findViewById(R.id.password);
        final EditText confirmpassword = findViewById(R.id.confirmpassword);
        final EditText fullname = findViewById(R.id.Fullname);
        final Button registerbtn = findViewById(R.id.registerbtn);
        final TextView loginnowbtn = findViewById(R.id.loginNowBtn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String amizoneidtxt = amizoneid.getText().toString();
                final String emailtxt = Email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String confirmpasswordTxt = confirmpassword.getText().toString();
                final String fullnametxt = fullname.getText().toString();

                if (amizoneidtxt.isEmpty()|| emailtxt.isEmpty()|| passwordTxt.isEmpty() || confirmpasswordTxt.isEmpty()||fullnametxt.isEmpty()){
                    Toast.makeText(Register.this, "Please enter all your details", Toast.LENGTH_SHORT).show();
                }
                else if (amizoneidtxt.length()<7 || amizoneidtxt.length()>8){
                    Toast.makeText(Register.this, "Amizone id is 7-8 digits only", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordTxt.equals(confirmpasswordTxt)){
                    Toast.makeText(Register.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(amizoneidtxt))
                                Toast.makeText(Register.this, "Amizone id is already registered", Toast.LENGTH_SHORT).show();
                            else{

                                try {
                                    dbref.child("users").child(amizoneidtxt).child("email").setValue(emailtxt);
                                    dbref.child("users").child(amizoneidtxt).child("name").setValue(fullnametxt);
                                    dbref.child("users").child(amizoneidtxt).child("password").setValue(passwordTxt);
                                    Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception E){
                                    Log.v("Error",E.toString());
                                }
                                startActivity(new Intent(Register.this, Login.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        loginnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}