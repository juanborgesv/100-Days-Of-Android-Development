package com.juanborges.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    static final String LOG_TAG = MainActivity.class.getSimpleName();

    static final String PRE_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=replaceHere&maxResults=10";

    static final int BOOK_LOADER_ID = 1;

    String searchText = "";

    BookAdapter adapter;

    View loadingIndicator;

    LinearLayout notFoundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.list);
        adapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(adapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        notFoundLayout = (LinearLayout) findViewById(R.id.not_found);

        notFoundLayout.setVisibility(View.INVISIBLE);

        EditText searchEditText = (EditText) findViewById(R.id.search);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchText = v.getText().toString();
                if (!searchText.isEmpty()) {
                    searchText = generateUrl(searchText);
                    Log.i(LOG_TAG, searchText);

                    loadingIndicator.setVisibility(View.VISIBLE);

                    if (networkInfo != null && networkInfo.isConnected()) {
                        // Get a reference to the LoaderManager, in order to interact with loaders.
                        LoaderManager loaderManager = getLoaderManager();

                        loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);

                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    } else {

                    }
                }

                return false;
            }
        });
    }

    String generateUrl(String text) {
        text = text.replace(" ", "+");

        String urlGenerated = PRE_REQUEST_URL.replace("replaceHere", text);
        return urlGenerated;
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader");
        return new BookLoader(this, searchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        Log.i(LOG_TAG, "onLoadFinished");
        adapter.clear();

        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
            notFoundLayout.setVisibility(View.GONE);
        } else {
            notFoundLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i(LOG_TAG, "onLoaderReset");
        adapter.clear();
    }
}
