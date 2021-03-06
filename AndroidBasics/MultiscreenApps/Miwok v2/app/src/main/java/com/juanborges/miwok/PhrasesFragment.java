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
public class PhrasesFragment extends Fragment {

    MediaPlayer mediaPlayer;

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public PhrasesFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();

        //words.add("One");
        words.add(new Word("Where are you going?",
                "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?",
                "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...",
                "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?",
                "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.",
                "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?",
                "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.",
                "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.",
                "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.",
                "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.",
                "әnni'nem", R.raw.phrase_come_here));

        // Adapter, the ListView assistant that helps to display each list item
        // into the List View correctly.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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
