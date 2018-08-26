package com.juanborges.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Loads a list of books by using an AsyncTask to perform the
 * network request to the given URL in the background thread.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    static final String LOG_TAG = BookLoader.class.getSimpleName();


    String url;

    public BookLoader(Context context, String myUrl) {
        super(context);

        Log.i(LOG_TAG, "BookLoader constructor");

        url = myUrl;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading");

        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground constructor");

        if (url.isEmpty())
            return null;

        List<Book> books = QueryUtils.fetchBooksData(url);


        return books;
    }
}
