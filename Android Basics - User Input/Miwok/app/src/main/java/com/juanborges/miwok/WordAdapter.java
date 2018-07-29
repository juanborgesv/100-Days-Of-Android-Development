package com.juanborges.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link WordAdapter} is an ArrayAdapter that can provide the layout for each list based on a data
 * source, which is a list of {@link Word} objects.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    private static final String LONG_TAG = WordAdapter.class.getSimpleName();
    int backgroundColorId;

    /**
     * This is our own custom constructor. The context is used to inflate the layout file, and the
     * list is the data we want to populate into the list.
     * @param context
     * @param words
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int mBackgroundColorId)
    {
        // Here we initialize the ArrayAdapter's internal storage for the context and the list.
        // The second argument is used when the ArrayAdapter is populating a single view.
        // Because this is a custom adapter for two views and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        backgroundColorId = mBackgroundColorId;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     * @param position Position in the list of data that should be desplayed in the list_item view.
     * @param convertView The recycled view to be populated.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The view for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view.
        View listItemView = convertView;

        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the ImageView in the list_item.xml layout with the id list_item_icon
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.list_item_icon);

        if (currentWord.hasImage())
        {
            // Get the image resource ID from the current Word object and set it
            imageView.setImageResource(currentWord.getImageResourceId());
        }
        else
            imageView.setVisibility(View.GONE);

        // Find the TextView in the list_item.xml layout with the id miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the Miwok translation from the Word object and set this text on the word TextView.
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the id miwok_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the Miwok translation from the Word object and set this text on the word TextView.
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Set the theme color for the list item view
        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), backgroundColorId);
        textContainer.setBackgroundColor(color);


        return listItemView;
    }
}
