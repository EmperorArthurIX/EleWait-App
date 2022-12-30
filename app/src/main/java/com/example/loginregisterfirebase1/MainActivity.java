package com.example.loginregisterfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static int MINFLOOR = 0, MAXFLOOR = 9, DELAY=10*1000;
    static int nextFloor = 0;
    DatabaseReference dbref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-b7bea-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView floorNumberText = findViewById(R.id.floornumber);
        Button refreshBtn = findViewById(R.id.refreshDataBtn);
        TextView statsPageLink = findViewById(R.id.statsPageLink);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date now = new Date();
                long timestamp = now.getTime() / 1000L;
//                System.out.println(timestamp);
                runLift(floorNumberText);
                dbref.child("liftStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(Long.toString(timestamp)))
                            Toast.makeText(MainActivity.this, "Too soon to move lift", Toast.LENGTH_SHORT).show();
                        else{

                            try {
                                dbref.child("liftStatus").child(timestamp+"").child("floor").setValue(floorNumberText.getText().toString());

                                Toast.makeText(MainActivity.this, "Floor registered successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception E){
                                Log.v("Error",E.toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        statsPageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Stats.class));
            }
        });
    }

    protected static void runLift(TextView floorNumberTxt)
    {
        int floor = Integer.parseInt(floorNumberTxt.getText().toString());
        if(nextFloor == floor) nextFloor = getNextFloor(floor);
        if (nextFloor > floor)
            moveLiftUp(floorNumberTxt);
        else moveLiftDown(floorNumberTxt);
    }

    protected static void moveLiftUp(TextView floorNumber)
    {
        int floor = Integer.parseInt(floorNumber.getText().toString());
        floorNumber.setText(""+(floor+1));
    }
    protected static void moveLiftDown(TextView floorNumber)
    {
        int floor = Integer.parseInt(floorNumber.getText().toString());
        floorNumber.setText(""+(floor-1));
    }
    protected static int getNextFloor(int currentFloor)
    {
        if(currentFloor == MINFLOOR)
            return (int) (Math.random() * MAXFLOOR + 1);
        if(currentFloor == MAXFLOOR)
            return (int) (Math.random() * MAXFLOOR);
        int val = (int) (Math.random() * (MAXFLOOR+1));
        while(val == currentFloor)
            val = (int) (Math.random() * (MAXFLOOR+1));
        return Math.random() > 0.5 ? Math.random() > 0.5? MAXFLOOR : MINFLOOR : val;
    }
}