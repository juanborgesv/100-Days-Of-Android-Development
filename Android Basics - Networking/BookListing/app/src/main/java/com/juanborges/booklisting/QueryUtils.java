package com.juanborges.booklisting;

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

    static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {}

    public static List<Book> fetchBooksData(String urlRequest) {
        Log.i(LOG_TAG, "FetchingBooksData for: "+ urlRequest);

        // Create URL object
        URL myUrl = createUrl(urlRequest);

        // Perform HTTP request to the url and recieve a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(myUrl);
        } catch (IOException exception) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", exception);
        }

        List<Book> books = extractFeatureFromJson(jsonResponse);

        return books;
    }

    /**
     * Create an Url object handling the proper possible exception
     * @param urlRequest
     * @return Url object to query
     */
    private static URL createUrl(String urlRequest) {
        URL myUrl = null;

        try {
            myUrl = new URL(urlRequest);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Problem building the URL", exception);
        }

        return myUrl;
    }

    /**
     * Make an HTTP request to the given URL
     * @param url
     * @return a String as the jsonResponse
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.i(LOG_TAG, "Error trying to connect, response code: "+ urlConnection.getResponseCode());
            }

        } catch (IOException exception) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", exception);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder jsonResponse = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader isReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bReader = new BufferedReader(isReader);

            String line = bReader.readLine();

            while (line != null) {
                jsonResponse.append(line);
                line = bReader.readLine();
            }
        }

        return jsonResponse.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     * @param jsonResponse
     * @return list of books
     */
    private static List<Book> extractFeatureFromJson(String jsonResponse) {

        if (jsonResponse == null || jsonResponse.isEmpty())
            return null;

        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int x = 0; x < booksArray.length(); x++) {
                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentBook = booksArray.getJSONObject(x);

                // For a given book extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that book.
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                StringBuilder authors = new StringBuilder();

                JSONArray authorsArray = volumeInfo.getJSONArray("authors");

                for (int a = 0; a < authorsArray.length(); a++)
                {
                    if (a > 0)
                        authors.append(", "+ authorsArray.getString(a));
                    else
                        authors.append(authorsArray.getString(a));
                }

                String date = volumeInfo.getString("publishedDate");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                String imageUrl = imageLinks.getString("thumbnail");

                String website = volumeInfo.getString("infoLink");

                books.add(new Book(title, authors.toString(), date, imageUrl, website));
            }
        } catch (JSONException exception) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", exception);
        }

        return books;
    }
}
