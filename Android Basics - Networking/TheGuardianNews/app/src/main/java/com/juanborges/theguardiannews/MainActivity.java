package com.juanborges.theguardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {

    private static final String GUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search";
    private static final int STORY_LOADER_ID = 1;
    private static final String API_KEY = "05ce5670-07de-430c-825a-310e26b75d59";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    StoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView storiesListView = findViewById(R.id.list);
        //List<Story> stories = new ArrayList<>();

        //stories.add(new Story("Labor proposes stronger restrictions on gas exports", "2018-09-02", "Australia News","News", null, "Juan Borges"));
        //stories.add(new Story("Jürgen Klopp: I’m really happy Alisson’s first Liverpool clanger is out of the way", "2018-09-02", "Football", "Sports", null, "Elon Musk"));

        adapter = new StoryAdapter(this, new ArrayList<Story>());

        storiesListView.setAdapter(adapter);


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(STORY_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //View loadingIndicator = findViewById(R.id.loading_indicator);
            //loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            //mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", "debate");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        //uriBuilder.appendQueryParameter("tag", "politics");
        //uriBuilder.appendQueryParameter("from-date", "2014-01-01");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.i(LOG_TAG, uriBuilder.toString());

        return new StoryLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
        // Clear the adapter with the previous data
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        adapter.clear();
    }
}
