package com.juanborges.theguardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {

    private static final String GUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search";
    private static final int STORY_LOADER_ID = 1;
    private static final String API_KEY = "05ce5670-07de-430c-825a-310e26b75d59";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private StoryAdapter adapter;
    private String searchText = "";

    private View loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView storiesListView = findViewById(R.id.list);

        adapter = new StoryAdapter(this, new ArrayList<Story>());
        storiesListView.setAdapter(adapter);

        loadingIndicator = (View) findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        storiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Story currentStory = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri storyUri = Uri.parse(currentStory.getUrl());

                // Create a new intent to view the earthquake URI
                Intent intent = new Intent(Intent.ACTION_VIEW, storyUri);

                // Send the intent to launch a new activity
                startActivity(intent);
            }
        });

        EditText searchEditText = findViewById(R.id.search);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        final ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchText = v.getText().toString();

                if (!searchText.isEmpty()) {
                    searchText = replaceSpaces(searchText);

                    loadingIndicator.setVisibility(View.VISIBLE);

                    // If there is a network connection, fetch data
                    if (networkInfo != null && networkInfo.isConnected()) {

                        // Get a reference to the LoaderManager, in order to interact with loaders.
                        LoaderManager loaderManager = getLoaderManager();

                        loaderManager.restartLoader(STORY_LOADER_ID, null, MainActivity.this);

                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.initLoader(STORY_LOADER_ID, null, MainActivity.this);
                    } else {
                        loadingIndicator.setVisibility(View.GONE);
                        Log.i(LOG_TAG, "ERROR: NETWORK DISABLED");

                        Toast.makeText(MainActivity.this, "LOL",
                                Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

    }

    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", searchText);
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.i(LOG_TAG, uriBuilder.toString());

        return new StoryLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
        // Clear the adapter with the previous data
        adapter.clear();

        loadingIndicator.setVisibility(View.GONE);
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        adapter.clear();
    }

    private String replaceSpaces(String text) {
        return text.replace(" ", "+");
    }
}
