package com.android.filter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Created by gabriellim on 01102017--.
 */

public final class QueryUtils {

    private QueryUtils() {

    }

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static ArrayList<University> extractUniversities(Context context, String query) {
        UniversityAsyncTask asyncTask = new UniversityAsyncTask();
        URL url = createUrl(query);
        ArrayList<University> universities = null;

        try {
            universities = asyncTask.execute(url).get();
        }
        catch (ExecutionException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        catch (InterruptedException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }

        return universities;
    }

    private static URL createUrl(String query) {
        URL url = null;

        try {
            url = new URL(query);
        }
        catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }

        return url;
    }

    public static class UniversityAsyncTask extends AsyncTask<URL, Void, ArrayList> {
        @Override
        protected ArrayList<University> doInBackground(URL... params) {
            URL url = params[0];

            String jsonResponse = "";
            try {
                Log.v("URL", String.format("%s", url.toString()));
                jsonResponse = makeHttpRequest(url);
            }
            catch (IOException e) {
                Log.e(LOG_TAG, "Error getting JSON", e);
            }

            ArrayList<University> universities = extractUniversitiesFromJson(jsonResponse);
            return universities;
        }

        @Override
        protected void onPostExecute(ArrayList universities) {
            if (universities == null) {
                return;
            }
        }

        private String makeHttpRequest(URL url) throws IOException {

            Log.v("URL", String.format("%s", url.toString()));

            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }

            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private ArrayList<University> extractUniversitiesFromJson(String universityJSON) {
            ArrayList<University> universities = new ArrayList<University>();

            try {
                JSONObject baseJsonResponse = new JSONObject(universityJSON);
                JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject result = resultsArray.getJSONObject(i);

                    String name = result.getString("name");
                    double rating = 0.0;

                    if (result.has("rating")) {
                        rating = result.getDouble("rating");
                    }

                    University uni = new University(name, rating);
                    universities.add(uni);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            } finally {

                Collections.sort(universities);

                return universities;
            }
        }


    }
}

