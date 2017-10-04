package edu.unc.jeffrey.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void accelerometerPress(View v) {
        Log.v("Test", "Acc push");
    }


    private void lightPress(View v) {
        Log.v("Test", "light push");
    }
}
