package edu.unc.jeffrey.assignment2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void accelerometerPress(View v) {
        Intent x = new Intent(this, PlotActivity.class);
        x.setData(Uri.parse(Sensor.TYPE_ACCELEROMETER + ""));
        startActivity(x);
        //Log.v("Test", "Acc push");
    }

    public void proximityPress(View v) {
        Intent x = new Intent(this, PlotActivity.class);
        x.setData(Uri.parse(Sensor.TYPE_PROXIMITY + ""));
        startActivity(x);
    }
}
