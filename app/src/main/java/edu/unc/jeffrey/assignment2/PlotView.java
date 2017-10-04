package edu.unc.jeffrey.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 10/4/2017.
 */

public class PlotView extends View {

    List<Float> _valuesList = new ArrayList<Float>();
    List<Float> _meanList = new ArrayList<Float>();
    List<Float> _stdDevList = new ArrayList<Float>();
    int _sensorType;

    public PlotView(Context context) {
        super(context);
    }

    public PlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addPoint(float toAdd[], int sensorType) {
        _sensorType = sensorType;
        if (_sensorType == Sensor.TYPE_ACCELEROMETER) {
            _valuesList.add((float) Math.sqrt((toAdd[0] * toAdd[0]) + (toAdd[1] * toAdd[1]) + (toAdd[2] * toAdd[2])));
        }
        else if (_sensorType == Sensor.TYPE_PROXIMITY) {
            _valuesList.add(toAdd[0]);
        }
        if (_valuesList.size() == 100) { //TODO change 10 to be proportion of width
            _valuesList.remove(0);
        }
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint color = new Paint();
        color.setARGB(255, 0, 0, 0);
        float previousX = 0f;
        float previousY = 0f;
        for (int i = 0; i < _valuesList.size(); i++) {
            float ceiling = 0;
            if (_sensorType == Sensor.TYPE_ACCELEROMETER) {
                ceiling = 17.0f;
            } else if (_sensorType == Sensor.TYPE_PROXIMITY) {
                ceiling = 5.0f;
            }
            canvas.drawCircle((canvas.getWidth() * i) / _valuesList.size(), (canvas.getHeight() * _valuesList.get(i)) / ceiling, 5, color);
            Paint linePaint = new Paint();
            linePaint.setARGB(255, 0, 255, 0);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(5f);
            if (i != 0) {
                canvas.drawLine((canvas.getWidth() * i) / _valuesList.size(), (canvas.getHeight() * _valuesList.get(i)) / ceiling,  previousX, previousY, linePaint);
            }
            previousX = (canvas.getWidth() * i) / _valuesList.size();
            previousY = (canvas.getHeight() * _valuesList.get(i)) / ceiling;
        }
    }

}
