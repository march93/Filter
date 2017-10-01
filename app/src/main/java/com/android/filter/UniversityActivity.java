package com.android.filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class UniversityActivity extends AppCompatActivity {

    public static final String LOG_TAG = University.class.getSimpleName();
    public static final String PLACES_API_REQUEST_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=49.2496600,-123.1193400&radius=50000&type=university&keyword=university&key=AIzaSyCxjjvdDzbKmC8Rm8jkv-GjBglIYTDUDk8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        populateUniversities();
    }

    private void populateUniversities() {

        String uniQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=49.2496600,-123.1193400&radius=50000&type=university&keyword=university&key=AIzaSyCxjjvdDzbKmC8Rm8jkv-GjBglIYTDUDk8";
        ArrayList<University> universities = QueryUtils.extractUniversities(getApplicationContext(), uniQuery);

        for (University u : universities) {
            Log.v("OMG", String.format("%s,%s", u.getUniversityRating(), u.getUniversityName()));
        }
    }

}
