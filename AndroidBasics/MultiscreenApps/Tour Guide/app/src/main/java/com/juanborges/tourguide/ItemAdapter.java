package com.juanborges.tourguide;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<ListItem> {

    public ItemAdapter(Activity context, ArrayList<ListItem> items) {
        // Here we initialize the ArrayAdapter's internal storage for the context and the list.
        // The second argument is used when the ArrayAdapter is populating a single view.
        // Because this is a custom adapter for two views and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /** Check if the existing view is reused if not, inflate the view */
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        /** Get the current {@link ListItem} */
        ListItem currentItem = getItem(position);

        /** Find the ImageView in the list_item.xml layout */
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.item_image);

        // If the current item has an image, set it. Else disable the ImageView
        if (currentItem.hasImage())
            imageView.setImageResource(currentItem.imageResourceId);
        else
            imageView.setVisibility(View.GONE);

        /** Find the title TextView in the list_item.xml layout */
        TextView titleText = (TextView) listItemView.findViewById(R.id.title_text);

        // If the current item has a title, set it. Else disable the TextView
        if (currentItem.getItemName() != null)
            titleText.setText("" + currentItem.getItemName());
        else
            titleText.setVisibility(View.GONE);

        /** Find the description TextView in the list_item.xml layout */
        TextView descriptionText = (TextView) listItemView.findViewById(R.id.description_text);

        // If the current item has a description, set it. Else disable the TextView
        if (currentItem.getItemDescription() != null)
            descriptionText.setText("" + currentItem.getItemDescription());
        else
            descriptionText.setVisibility(View.GONE);

        /** Find the location TextView in the list_item.xml layout */
        TextView locationText = (TextView) listItemView.findViewById(R.id.location_text);

        // If the current item has a location text, set it. Else disable the TextView
        if (currentItem.getItemLocation() != null)
            locationText.setText("" + currentItem.getItemLocation());
        else
            locationText.setVisibility(View.GONE);

        /** Find the schedule TextView in the list_item.xml layout */
        TextView scheduleText = (TextView) listItemView.findViewById(R.id.time_text);

        // If the current item has a schedule, set it. Else disable the TextView
        if (currentItem.getItemSchedule() != null)
            scheduleText.setText("" + currentItem.getItemSchedule());
        else
            scheduleText.setVisibility(View.GONE);

        /** Find the phone number TextView in the list_item.xml layout */
        TextView numberText = (TextView) listItemView.findViewById(R.id.number_text);

        // If the current word has a contact number, set it. Else disable the TextView
        if (currentItem.getContactNumber() != null)
            numberText.setText("" + currentItem.getContactNumber());
        else
            numberText.setVisibility(View.GONE);

        return listItemView;
    }
}
