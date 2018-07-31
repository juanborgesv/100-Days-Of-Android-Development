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
public class FamilyFragment extends Fragment {
    MediaPlayer mediaPlayer;

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("Father", "әpә",
                R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother", "әṭa",
                R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Son", "Angsi",
                R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter", "Tune",
                R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Older Brother", "Taachi",
                R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("Younger Brother", "Chalitti",
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("Older Sister", "Teṭe",
                R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("Younger Sister", "Kolliti",
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("Grandmother", "Ama",
                R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Grandfather", "Paapa",
                R.drawable.family_grandfather, R.raw.family_grandfather));

        // Adapter, the ListView assistant that helps to display each list item
        // into the List View correctly.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

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
