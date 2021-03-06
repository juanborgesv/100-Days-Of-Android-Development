package com.juanborges.miwok;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    MediaPlayer mediaPlayer;

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public ColorsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("Red", "Weṭeṭṭi",
                R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Green", "Chokokki",
                R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Brown", "ṭakaakki",
                R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Gray", "ṭopoppi",
                R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("Black", "Kululli",
                R.drawable.color_black, R.raw.color_black));
        words.add(new Word("White", "Kelelli",
                R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Dusty Yellow", "ṭopiisә",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("Mustard Yellow", "Chiwiiṭә",
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // Adapter, the ListView assistant that helps to display each list item
        // into the List View correctly.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        // Create the ListView and get the reference of the actual listView that is going to be used
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Set the adapter for the ListView
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Release the media player if it is currently exists because we are about
                // play a different sound.
                releaseMediaPlayer();
                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word.
                mediaPlayer = MediaPlayer.create(getActivity(),
                        word.getSoundResourceId());
                mediaPlayer.start();


                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing
                mediaPlayer.setOnCompletionListener(onCompletionListener);
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }
}
