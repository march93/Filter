package com.android.filter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setOnClick(R.id.graph, GraphActivity.class);
        setOnClick(R.id.tsunami, TsunamiActivity.class);
    }

    private void setOnClick(int resourceID, final Class nextActivity) {
        TextView textView = (TextView) findViewById(resourceID);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicitIntent = new Intent(MainActivity.this, nextActivity);
                startActivity(explicitIntent);
                Log.v("ACTIVITY LAUNCHED", String.format("Launched %s", nextActivity));
            }
        });
    }
}
