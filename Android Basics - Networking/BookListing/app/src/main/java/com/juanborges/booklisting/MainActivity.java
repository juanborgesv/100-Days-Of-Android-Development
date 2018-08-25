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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    static final String LOG_TAG = MainActivity.class.getSimpleName();

    static final String PRE_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=replaceHere:keyes&key=AIzaSyBr8yVSA6u9M9_veMTbPyJYU1FtTNvYNwA";

    static final int BOOK_LOADER_ID = 1;

    String searchText = "https://www.googleapis.com/books/v1/volumes?q=Machine+Learning:keyes&key=AIzaSyBr8yVSA6u9M9_veMTbPyJYU1FtTNvYNwA";

    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.list);
        adapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(adapter);

        EditText searchEditText = (EditText) findViewById(R.id.search);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchText = v.getText().toString();
                searchText = generateUrl(searchText);
                if (!searchText.isEmpty())
                {
                    Log.i(LOG_TAG, searchText);
                }

                return false;
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }
    }

    String generateUrl(String text)
    {
        text = text.replace(" ", "+");

        String urlGenerated = PRE_REQUEST_URL.replace("replaceHere", text);
        return urlGenerated;
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, searchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        adapter.clear();

        if (data != null && !data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }
}
