package com.example.loginregisterfirebase1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static int MINFLOOR = 0, MAXFLOOR = 9, DELAY=10*1000;
    static int nextFloor = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView floorNumberText = findViewById(R.id.floornumber);
        Button refreshBtn = findViewById(R.id.refreshDataBtn);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runLift(floorNumberText);
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