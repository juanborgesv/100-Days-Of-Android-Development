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

    public ItemAdapter(Activity context, ArrayList<ListItem> items)
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

        // Check if the existing view is reused if not, inflate the view
        View listItemView = convertView;
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the current {@link ListItem}
        ListItem currentItem = getItem(position);

        // Find the ImageView in the list_item.xml layout
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.item_image);

        // If the current word has an image, set it. Else disable the ImageView
        if (currentItem.hasImage())
            imageView.setImageResource(currentItem.imageResourceId);
        else
            imageView.setVisibility(View.GONE);

        // Find the title TextView in the list_item.xml layout and set the current item title
        TextView titleText = (TextView) listItemView.findViewById(R.id.title_text);
        titleText.setText(""+ currentItem.getItemName());

        // Find the phone number TextView in the list_item.xml layout
        // and set the current item phone number
        TextView numberText = (TextView) listItemView.findViewById(R.id.number_text);
        numberText.setText(""+ currentItem.getContactNumber());

        return listItemView;
    }
}
