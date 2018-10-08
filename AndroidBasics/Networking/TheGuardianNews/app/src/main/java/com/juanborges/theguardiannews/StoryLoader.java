package com.juanborges.theguardiannews;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Loads a list of stories by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class StoryLoader extends AsyncTaskLoader<List<Story>> {
    final static String LOG_TAG = StoryLoader.class.getSimpleName();


    // Query URL
    private String url;

    public StoryLoader(Context context, String url) {
        super(context);
        this.url = url;

        Log.i(LOG_TAG, "StoryLoader constructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

        Log.i(LOG_TAG, "onStartLoading method");
    }

    @Override
    public List<Story> loadInBackground() {
        if (url == null || url.length() == 0)
            return null;

        Log.i(LOG_TAG, "loadInBackground method");

        return QueryUtils.fetchStoriesData(url);
    }
}
