package com.juanborges.theguardiannews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView storiesListView = findViewById(R.id.list);
        List<Story> stories = new ArrayList<>();

        stories.add(new Story("Labor proposes stronger restrictions on gas exports", "AUS Report", "2018-09-02","Australia news", null));
        stories.add(new Story("Jürgen Klopp: I’m really happy Alisson’s first Liverpool clanger is out of the way", "MARCA", "2018-09-02", "Football", null));

        StoryAdapter adapter = new StoryAdapter(this, stories);

        storiesListView.setAdapter(adapter);
    }
}
