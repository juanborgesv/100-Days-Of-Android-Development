package com.juanborges.tourguide;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemTwoAdapter extends ArrayAdapter<ListItem> {

    public ItemTwoAdapter(Activity context, ArrayList<ListItem> items)
    {
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
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item2, parent, false);
        }

        /** Get the current {@link ListItem} */
        ListItem currentItem = getItem(position);

        /** Find the ImageView in the list_item2.xml layout */
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.item_image);

        // If the current item has an image, set it. Else disable the ImageView
        if (currentItem.hasImage())
            imageView.setImageResource(currentItem.imageResourceId);
        else
            imageView.setVisibility(View.GONE);

        /** Find the title TextView in the list_item2.xml layout */
        TextView titleText = (TextView) listItemView.findViewById(R.id.title_text);

        // If the current item has a title/name, set it. Else disable the TextView
        if (currentItem.getItemName() != null)
            titleText.setText(""+ currentItem.getItemName());
        else
            titleText.setVisibility(View.GONE);

        /** Find the description TextView in the list_item2.xml layout */
        TextView descriptionText = (TextView) listItemView.findViewById(R.id.description_text);

        // If the current item has a description, set it. Else disable the TextView
        if (currentItem.getItemDescription() != null)
            descriptionText.setText(""+ currentItem.getItemDescription());
        else
            descriptionText.setVisibility(View.GONE);

        /** Find the location TextView and LinearLayout in the list_item2.xml layout */
        LinearLayout locationLayout = (LinearLayout) listItemView.findViewById(R.id.location_layout);
        TextView locationText = (TextView) listItemView.findViewById(R.id.location_text);

        // If the current item has a location, set it. Else disable the LinearLayout.
        if (currentItem.getItemLocation() != null)
            locationText.setText(""+ currentItem.getItemLocation());
        else
            locationLayout.setVisibility(View.GONE);

        /** Find the time/schedule TextView and LinearLayout in the list_item2.xml layout */
        LinearLayout timeLayout = (LinearLayout) listItemView.findViewById(R.id.time_layout);
        TextView timeText = (TextView) listItemView.findViewById(R.id.time_text);

        // If the current item has a schedule, set it. Else disable the LinearLayout
        if (currentItem.getItemSchedule() != null)
            timeText.setText(""+ currentItem.getItemSchedule());
        else
            timeLayout.setVisibility(View.GONE);

        /** Find the contact number TextView and LinearLAyout in the list_item2.xml layout*/
        LinearLayout numberLayout = (LinearLayout) listItemView.findViewById(R.id.number_layout);
        TextView numberText = (TextView) listItemView.findViewById(R.id.number_text);

        // If the current item has a contact number, set it. Else disable the LinearLayout
        if (currentItem.getContactNumber() != null)
            numberText.setText(""+ currentItem.getContactNumber());
        else
            numberLayout.setVisibility(View.GONE);

        return listItemView;
    }
}
