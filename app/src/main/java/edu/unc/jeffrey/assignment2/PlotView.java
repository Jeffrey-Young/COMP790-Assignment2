package edu.unc.jeffrey.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 10/4/2017.
 */

public class PlotView extends View {

    private static final int MAX_SIZE = 100;

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
           Log.v("value", _valuesList.get(_valuesList.size() - 1) + "");
        }
        else if (_sensorType == Sensor.TYPE_PROXIMITY) {
            _valuesList.add(toAdd[0]);
        }
        if (_valuesList.size() == MAX_SIZE) { //TODO change 10 to be proportion of width
            _valuesList.remove(0);
        }
        computeMean();
        computeStandardDeviation();
        this.invalidate();
    }

    private void computeMean() {
        if (_meanList.size() < 3) {
            _meanList.add(_valuesList.get(_valuesList.size() - 1));
        } else {
            _meanList.add((_valuesList.get(_valuesList.size() - 1) + _valuesList.get(_valuesList.size() - 2) + _valuesList.get(_valuesList.size() - 3)) / 3);
        }
        if (_meanList.size() == MAX_SIZE){
            _meanList.remove(0);
        }
    }

    private void computeStandardDeviation() {
        if (_stdDevList.size() < 5) {
            _stdDevList.add(0f);
            //Log.v("orig std dev", _valuesList.get(_valuesList.size() - 1) + "");
        } else {
            float val1 = (float) Math.pow(_valuesList.get(_valuesList.size() - 1) - _meanList.get(_meanList.size() - 1), 2);
            float val2 = (float) Math.pow(_valuesList.get(_valuesList.size() - 2) - _meanList.get(_meanList.size() - 1), 2);
            float val3 = (float) Math.pow(_valuesList.get(_valuesList.size() - 3) - _meanList.get(_meanList.size() - 1), 2);

            float sum = val1 + val2 + val3;
            _stdDevList.add((float) Math.sqrt(sum / 2)); // + 9.81f);
           // Log.v("Std Dev", Math.sqrt(sum / 2) + "");
        }
        if (_stdDevList.size() == MAX_SIZE){
            _stdDevList.remove(0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint valueColor = new Paint();
        valueColor.setARGB(255, 0, 0, 0);
        float previousValueX = 0f;
        float previousValueY = 0f;
        float previousMeanX = 0f;
        float previousMeanY = 0f;
        float previousStdDevX = 0f;
        float previousStdDevY = 0f;
        for (int i = 0; i < _valuesList.size(); i++) {
            float ceiling = 0;
            if (_sensorType == Sensor.TYPE_ACCELEROMETER) {
                ceiling = 30.0f;
            } else if (_sensorType == Sensor.TYPE_PROXIMITY) {
                ceiling = 5.0f;
            }
            canvas.drawCircle((canvas.getWidth() * i) / MAX_SIZE, (canvas.getHeight() * _valuesList.get(i)) / ceiling, 5, valueColor);
            canvas.drawCircle((canvas.getWidth() * i) / MAX_SIZE, (canvas.getHeight() * _meanList.get(i)) / ceiling, 5, valueColor);
            canvas.drawCircle((canvas.getWidth() * i) / MAX_SIZE, (canvas.getHeight() * _stdDevList.get(i)) / ceiling, 5, valueColor);
            Paint valuePaint = new Paint();
            valuePaint.setARGB(255, 0, 255, 0);
            valuePaint.setStyle(Paint.Style.STROKE);
            valuePaint.setStrokeWidth(5f);

            Paint meanPaint = new Paint();
            meanPaint.setARGB(255, 0, 0, 255);
            meanPaint.setStyle(Paint.Style.STROKE);
            meanPaint.setStrokeWidth(5f);

            Paint stdDevPaint = new Paint();
            stdDevPaint.setARGB(255, 255, 0, 0);
            stdDevPaint.setStyle(Paint.Style.STROKE);
            stdDevPaint.setStrokeWidth(5f);
            if (i != 0) {
                canvas.drawLine((canvas.getWidth() * i) / MAX_SIZE, (canvas.getHeight() * _valuesList.get(i)) / ceiling,  previousValueX, previousValueY, valuePaint);
                canvas.drawLine((canvas.getWidth() * i) / MAX_SIZE, (canvas.getHeight() * _meanList.get(i)) / ceiling,  previousMeanX, previousMeanY, meanPaint);
                canvas.drawLine((canvas.getWidth() * i) / MAX_SIZE, (canvas.getHeight() * _stdDevList.get(i)) / ceiling,  previousStdDevX, previousStdDevY, stdDevPaint);
            }
            previousValueX = (canvas.getWidth() * i) / MAX_SIZE;
            previousValueY = (canvas.getHeight() * _valuesList.get(i)) / ceiling;
            previousMeanX = (canvas.getWidth() * i) / MAX_SIZE;
            previousMeanY = (canvas.getHeight() * _meanList.get(i)) / ceiling;
            previousStdDevX = (canvas.getWidth() * i) / MAX_SIZE;
            previousStdDevY = (canvas.getHeight() * _stdDevList.get(i)) / ceiling;
        }
    }

}
