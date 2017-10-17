package edu.unc.jeffrey.assignment2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 10/4/2017.
 */

public class PlotActivity extends AppCompatActivity implements SensorEventListener {

    Sensor _s;
    ImageView _image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        AxisView xAxis = (AxisView) findViewById(R.id.xAxis);
        AxisView yAxis = (AxisView) findViewById(R.id.yAxis);
        yAxis.setxAxis(false);

        List xList = new ArrayList<String>();
        for (int i = 1; i <= 10; i++) {
            xList.add(i + "");
        }
        xAxis.setLabels(xList);

        List yList = new ArrayList<String>();
        for (int i = 1; i <= 30; i++) {
            yList.add(i + "");
        }
        yAxis.setLabels(yList);
        xAxis.invalidate();
        yAxis.invalidate();

        _image = (ImageView) findViewById(R.id.imageView);




        SensorManager sm = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        _s = sm.getDefaultSensor(Integer.parseInt(getIntent().getData().toString()));
//        Sensor accelerometerSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        Sensor proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sm.registerListener(this, _s, 500000);
      //  sm.registerListener(this, proximitySensor, 1000000);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            AxisView yAxis = (AxisView) findViewById(R.id.yAxis);
            List yList = new ArrayList<String>();
            for (int i = 1; i <= 5; i++) {
                yList.add(i + "");
            }
            yAxis.setLabels(yList);
            yAxis.invalidate();
        }
        if (event.sensor != null) {
            PlotView pv = (PlotView) findViewById(R.id.plotView);
            pv.addPoint(event.values, event.sensor.getType());

            TextView value = (TextView) findViewById(R.id.value);
            TextView mean = (TextView) findViewById(R.id.mean);
            TextView stdDev = (TextView) findViewById(R.id.stdDev);

            if (pv._valuesList.size() != 0) {
                value.setText(pv._valuesList.get(pv._valuesList.size() - 1) + "");
                if (pv._valuesList.get(pv._valuesList.size() - 1) > 20 && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    _image.setImageResource(R.drawable.lotsoffireworks);
                } else if (pv._valuesList.get(pv._valuesList.size() - 1) > 15 && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    _image.setImageResource(R.drawable.fireworks);
                } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    _image.setImageResource(R.drawable.missile);

                }

                if (pv._valuesList.get(pv._valuesList.size() - 1) == 5 && event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    _image.setImageResource(R.drawable.smallhand);
                }  else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
                    _image.setImageResource(R.drawable.largehand);

                }
            }

            if (pv._meanList.size() != 0) {
                mean.setText(pv._meanList.get(pv._meanList.size() - 1) + "");
            }

            if (pv._stdDevList.size() != 0) {
                stdDev.setText(pv._stdDevList.get(pv._stdDevList.size() - 1) + "");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
