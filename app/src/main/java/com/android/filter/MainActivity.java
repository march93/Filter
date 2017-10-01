package com.android.filter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setOnClick(R.id.graph, GraphActivity.class);
        setOnClick(R.id.tsunami, TsunamiActivity.class);
    }

    private void setOnClick(int resourceID, final Class nextActivity) {
        TextView textView = (TextView) findViewById(resourceID);


        final EditText editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.searchLocation);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String result = editText.getText().toString();

                if (result.length() < 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Invalid Location");
                    alertDialog.setMessage("Please enter a valid location.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    result = result.toLowerCase();
                }
            }
        });
    }
}
