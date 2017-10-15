package edu.unc.jeffrey.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffrey on 10/12/2017.
 */

public class AxisView extends View {

    private boolean _xAxis = true;
    private List<String> _labels = new ArrayList<String>();

    public AxisView(Context context) {
        super(context);
    }

    public AxisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AxisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AxisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setxAxis(boolean value) {
        _xAxis = value;
    }

    public void setLabels(List<String> labels) {
        _labels = new ArrayList<String>(labels);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint black = new Paint();
        black.setARGB(255, 0, 0, 0);
        black.setStrokeWidth(5);

        int stepSize = 0;
        if (_xAxis) {
            canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, black);
            if (_labels.size() != 0 && canvas.getWidth() != 0) {
                stepSize = canvas.getWidth() / _labels.size();
            }
            for (int i = 0; i < _labels.size(); i++) {
                canvas.drawLine((stepSize * (i + 1)), 0, (stepSize * (i + 1)), canvas.getHeight() / 2, black);
                canvas.drawText(_labels.get(i), (stepSize * (i + 1) - 3), canvas.getHeight(), black);
            }
        } else {// y axis
            canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), black);
            if (_labels.size() != 0 && canvas.getHeight() != 0) {
                stepSize = canvas.getHeight() / _labels.size();
            }
            for (int i = 0; i < _labels.size(); i++) {
                canvas.drawLine(canvas.getWidth() / 2, (stepSize * (i + 1)), canvas.getWidth(), (stepSize * (i + 1)), black);
                canvas.drawText(_labels.get(i), 0, (stepSize * (i + 1) + 4), black);

            }
        }



    }
}
