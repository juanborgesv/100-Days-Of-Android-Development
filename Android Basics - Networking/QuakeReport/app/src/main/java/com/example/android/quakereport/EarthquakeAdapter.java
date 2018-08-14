package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.earthquake_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeView = (TextView) convertView.findViewById(R.id.magnitude);

        // Check if the current Earthquake has magnitude, if it does set it, else disable the view.
        if (currentEarthquake.getMagnitude() != null)
            magnitudeView.setText(""+ currentEarthquake.getMagnitude());
        else
            magnitudeView.setVisibility(View.GONE);

        TextView locationView = convertView.findViewById(R.id.location);

        // Check if the current Earthquake has location, if it does set it, else disable the view.
        if (currentEarthquake.getLocation() != null)
            locationView.setText(""+ currentEarthquake.getLocation());
        else
            locationView.setVisibility(View.GONE);

        TextView dateView = convertView.findViewById(R.id.date);

        // Check if the current Earthquake has a date, if it does set it, else disable the view.
        if (currentEarthquake.getDate() != null)
            dateView.setText(""+ currentEarthquake.getDate());
        else
            dateView.setVisibility(View.GONE);

        return convertView;
    }
}
