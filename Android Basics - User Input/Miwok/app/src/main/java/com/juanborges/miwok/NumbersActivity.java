package com.juanborges.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        ArrayList<Word> words = new ArrayList<>();

        //words.add("One");
        words.add(new Word("One", "Lutti"));
        words.add(new Word("Two", "Otiiko"));
        words.add(new Word("Three", "Tolookosu"));
        words.add(new Word("Four","Oyyisa"));
        words.add(new Word("Five", "Massokka"));
        words.add(new Word("Six", "Temmokka"));
        words.add(new Word("Seven", "Kenekaku"));
        words.add(new Word("Eight", "Kawinta"));
        words.add(new Word("Nine", "Wo'e"));
        words.add(new Word("Ten", "Na'aacha"));

        /*
        words.add("Eleven");
        words.add("Twelve");
        words.add("Thirteen");
        words.add("Fourteen");
        words.add("Fifteen");
        words.add("Sixteen");
        words.add("Seventeen");
        words.add("Eighteen");
        words.add("Nineteen");
        words.add("Twenty");*/

        WordAdapter adapter = new WordAdapter(this, words);

        //ArrayAdapter<Word> itemsAdapter = new ArrayAdapter<Word>(this, R.layout.list_item, words);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

}
