package com.example.android.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    /** Query URL */
    String url;

    public EarthquakeLoader(Context context, String myUrl) {
        super(context);
        url = myUrl;

        Log.i(LOG_TAG, "EarthquakeLoader constructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i(LOG_TAG, "onStartLoading method");
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (url == null)
            return null;

        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(url);

        Log.i(LOG_TAG, "loadInBackground method");
        return earthquakes;
    }
}