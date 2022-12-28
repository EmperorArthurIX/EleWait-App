package com.example.loginregisterfirebase1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static int MINFLOOR = 0, MAXFLOOR = 9, DELAY=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView floorNumberText = findViewById(R.id.floornumber);

    }
    protected static void runLift(TextView floorNumber)
    {
        int floor = Integer.parseInt(floorNumber.getText().toString());
        int nextFloor = getNextFloor(floor);
        int floorDiff = Math.abs(floor - nextFloor);
        for (int i = 1; i <= floorDiff; i++)
        {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(floor < nextFloor)
                floorNumber.setText(floor + i);
            else
                floorNumber.setText(floor - i);
        }
        try {
            Thread.sleep((int)(Math.random() * 2 * DELAY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        return val;
    }
}