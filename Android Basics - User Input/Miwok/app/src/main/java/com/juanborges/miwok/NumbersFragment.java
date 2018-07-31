package com.juanborges.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumbersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumbersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumbersFragment extends Fragment {

    // Handles playback of all the sound files
    MediaPlayer mediaPlayer;

    // Handles audio focus when playing a sound file
    AudioManager audioManager;

    // This listener gets triggered when the {@link MediaPlayer} has completed playing the
    // audio file
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    // This listener gets triggered whenever the audio focus changes, (i.e. we gain or lose audio
    // focus because of another app or device)
    AudioManager.OnAudioFocusChangeListener afChangeListener = new
            AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    {
                        // AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short
                        // amount of time. AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that our app is
                        // allowed to continue playing sound but at a lower volume. We'll treat both cases
                        // the same way because our app is playing short sound files.
                        // Pause playback.
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                    {
                        // AUDIOFOCUS_GAIN case means we have regained focus and can resume playback
                        // Resume Playback.
                        mediaPlayer.start();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                    {
                        // AUDIOFOCUS_LOSS case means we've lost audio focus and stop playback and
                        // cleanup resources.
                        releaseMediaPlayer();
                    }
                }
            };

    public NumbersFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        // final is used in order to reference the words in the OnItemClick method
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("One", "Lutti",
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "Otiiko",
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "Tolookosu",
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four","Oyyisa",
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "Massokka",
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "Temmokka",
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven", "Kenekaku",
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "Kawinta",
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine", "Wo'e",
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "Na'aacha",
                R.drawable.number_ten, R.raw.number_ten));

        // Adapter, the ListView assistant that helps to display each list item
        // into the List View correctly.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

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

                // Request audio focus for playback
                int result = audioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    //audioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word.
                    mediaPlayer = MediaPlayer.create(getActivity(),
                            word.getSoundResourceId());
                    mediaPlayer.start();


                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
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

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
