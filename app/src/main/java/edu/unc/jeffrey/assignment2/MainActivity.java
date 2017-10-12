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
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor accel;
    private Sensor prox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        accel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        prox = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        TextView accelText = (TextView) findViewById(R.id.accelerometerText);
        TextView proxText = (TextView) findViewById(R.id.proximityText);
        if (accel != null) {
            accelText.setText("Accelerometer is present\nRange: " + accel.getMaximumRange() + "\nResolution: " + accel.getResolution() + "\nDelay: " + accel.getMinDelay());

        } else {
            accelText.setText("Accelerometer is not present");
        }
        if (prox != null) {
            proxText.setText("Proximity is present\nRange: " + prox.getMaximumRange() + "\nResolution: " + prox.getResolution() + "\nDelay: " + prox.getMinDelay());

        } else {
            proxText.setText("Proximity is not present");
        }

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

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        sm.registerListener(this, accel, Sensor.TYPE_ACCELEROMETER);
//        sm.registerListener(this, prox, Sensor.TYPE_PROXIMITY);
//    }
}
