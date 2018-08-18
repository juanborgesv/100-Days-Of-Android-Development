package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

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
        magnitudeView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView = (TextView) convertView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) convertView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        TextView dateView = convertView.findViewById(R.id.date);
        Date date = new Date(currentEarthquake.getTimeInMiliseconds());
        dateView.setText(formatDate(date));

        TextView timeView = (TextView) convertView.findViewById(R.id.time);
        timeView.setText(formatTime(date));


        return convertView;
    }

    /**
     *
     * @param
     * @return the formatted date string (i.e. "May 2, 1996").
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    /**
     *
     * @param date Date Object
     * @return the formatted date string (i.e. "May 2, 1996").
     */
    private String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @param date Date Object
     * @return the formatted date string (i.e. "8:00 PM").
     */
    private String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        return  simpleDateFormat.format(date);
    }

    private int getMagnitudeColor(double magnitude)
    {
        if (magnitude >= 0 && magnitude < 2)
            return ContextCompat.getColor(getContext(), R.color.magnitude1);
        else if (magnitude >= 2 && magnitude < 3 )
            return ContextCompat.getColor(getContext(), R.color.magnitude2);
        else if (magnitude >= 3 && magnitude < 4)
            return ContextCompat.getColor(getContext(), R.color.magnitude3);
        else if (magnitude >= 4 && magnitude < 5)
            return ContextCompat.getColor(getContext(), R.color.magnitude4);
        else if (magnitude >= 5 && magnitude < 6)
            return ContextCompat.getColor(getContext(), R.color.magnitude5);
        else if (magnitude >= 6 && magnitude < 7)
            return ContextCompat.getColor(getContext(), R.color.magnitude6);
        else if (magnitude >= 7 && magnitude < 8)
            return ContextCompat.getColor(getContext(), R.color.magnitude7);
        else if (magnitude >= 8 && magnitude < 9)
            return ContextCompat.getColor(getContext(), R.color.magnitude8);
        else if (magnitude >= 9 && magnitude < 10)
            return ContextCompat.getColor(getContext(), R.color.magnitude9);

        return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
    }
}
