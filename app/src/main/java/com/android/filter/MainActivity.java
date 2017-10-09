package com.android.filter;

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

import java.util.HashMap;
import java.util.Map;
import android.location.LocationManager;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    Button button;

    Map<Integer, Class> ACTIVITY_MAP = new HashMap<Integer, Class>() {{
        put(R.id.tsunami, TsunamiActivity.class);
        put(R.id.place, UniversityActivity.class);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusCheck();
        setupSearchLocation();
        setupButtonOnClick();
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setupSearchLocation() {
        final EditText editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.searchLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    Intent graphView = new Intent(MainActivity.this, GraphActivity.class);
                    startActivity(graphView);
                }
            }
        });
    }

    private void setupButtonOnClick() {
        for (Map.Entry<Integer, Class> entry : ACTIVITY_MAP.entrySet()) {
            Integer resourceID = entry.getKey();
            Class activityClass = entry.getValue();

            setOnClick(resourceID, activityClass);
        }
    }

    private void setOnClick(Integer resourceID, final Class nextActivity) {
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
