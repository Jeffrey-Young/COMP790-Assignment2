package edu.unc.jeffrey.assignment2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * Created by young on 10/4/2017.
 */

public class PlotActivity extends AppCompatActivity implements SensorEventListener {

    Sensor _s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        SensorManager sm = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        _s = sm.getDefaultSensor(Integer.parseInt(getIntent().getData().toString()));
//        Sensor accelerometerSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        Sensor proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sm.registerListener(this, _s, 1000000);
      //  sm.registerListener(this, proximitySensor, 1000000);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor != null) {
            PlotView pv = (PlotView) findViewById(R.id.plotView);
            pv.addPoint(event.values, event.sensor.getType());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
