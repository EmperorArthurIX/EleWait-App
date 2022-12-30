package com.example.loginregisterfirebase1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Pie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Stats extends AppCompatActivity {
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-b7bea-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        AnyChartView floorFreqChart = findViewById(R.id.floorFrequency);
        AnyChartView floorTrendChart = findViewById(R.id.floorTrend);

        Cartesian freqs = AnyChart.column();

    }
}