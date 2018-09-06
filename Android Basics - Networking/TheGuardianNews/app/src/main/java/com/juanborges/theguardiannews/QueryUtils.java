package com.juanborges.theguardiannews;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {}

    public static List<Story> fetchStoriesData(String urlRequest) {

        URL myUrl = createUrl(urlRequest);

        // Check that the url that is going to be used is not null. Otherwise
        // return null since it should not request data from a null URL.
        if (myUrl == null) {
            Log.i(LOG_TAG, "Url provided results null");
            return null;
        }

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(myUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Check that the JSON response from the Query is not null or empty. Otherwise return
        // null since it should not extract anything from a null or empty JSON response.
        if (jsonResponse == null || jsonResponse.length() == 0) {
            Log.i(LOG_TAG, "JSON response is empty!");
            return null;
        }

        List<Story> stories = extractFeatureFromJson(jsonResponse);

        return stories;
    }

    /**
     * Create an URL object from a String
     * @param urlRequest
     * @return
     */
    private static URL createUrl(String urlRequest) {
        URL myUrl = null;

        try {
            myUrl = new URL(urlRequest);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem creating a URL.", e);
       }

       return myUrl;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     * @param myUrl that is going to be use to query
     * @return JSON response from the query
     * @throws IOException by inputStream.close()
     */
    private static String makeHttpRequest(URL myUrl) throws IOException {
        String jsonResponse = "";

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpsURLConnection) myUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(8000); // 8 Seconds
            urlConnection.setConnectTimeout(8000); // 8 Seconds

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.i(LOG_TAG, "Error trying to connect, response code: "+ urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder jsonResponse = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                jsonResponse.append(line);
                line = bufferedReader.readLine();
            }
        }

        return jsonResponse.toString();
    }

    private static List<Story> extractFeatureFromJson(String jsonResponse) {
        List<Story> stories = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int j = 0; j < results.length(); j++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentStory = results.getJSONObject(j);

                String title = null;
                String date = null;
                String section = null;
                String pillar = null;
                String url = null;

                if (currentStory.has("webTitle"))
                    title = currentStory.getString("webTitle");

                if (currentStory.has("webPublicationDate"))
                    date = currentStory.getString("webPublicationDate");

                if (currentStory.has("sectionName"))
                    section = currentStory.getString("sectionName");

                if (currentStory.has("pillarName"))
                    pillar = currentStory.getString("pillarName");

                if (currentStory.has("webUrl"))
                    url = currentStory.getString("webUrl");

                stories.add(new Story(title, date, section, pillar, url));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the story JSON results", e);
        }

        return stories;
    }
}
