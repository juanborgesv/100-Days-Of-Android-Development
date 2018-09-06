package com.juanborges.theguardiannews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Loads a list of stories by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class StoryLoader extends AsyncTaskLoader<List<Story>> {

    // Query URL
    String url;

    public StoryLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {
        if (url == null || url.length() == 0)
            return null;

        return QueryUtils.fetchStoriesData(url);
    }


}
