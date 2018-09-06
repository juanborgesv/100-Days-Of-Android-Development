package com.juanborges.theguardiannews;

import android.app.LoaderManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity /*implements LoaderManager.LoaderCallbacks<List<Story>> */{

    private static final String GUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search";
    private static final String API_KEY = "05ce5670-07de-430c-825a-310e26b75d59";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView storiesListView = findViewById(R.id.list);
        List<Story> stories = new ArrayList<>();

        stories.add(new Story("Labor proposes stronger restrictions on gas exports", "2018-09-02", "Australia News","News", null));
        stories.add(new Story("Jürgen Klopp: I’m really happy Alisson’s first Liverpool clanger is out of the way", "2018-09-02", "Football", "Sports", null));

        StoryAdapter adapter = new StoryAdapter(this, stories);

        storiesListView.setAdapter(adapter);

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", "debate");
        uriBuilder.appendQueryParameter("tag", "politics");
        uriBuilder.appendQueryParameter("from-date", "2014-01-01");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.i(LOG_TAG, uriBuilder.toString());
    }
}
