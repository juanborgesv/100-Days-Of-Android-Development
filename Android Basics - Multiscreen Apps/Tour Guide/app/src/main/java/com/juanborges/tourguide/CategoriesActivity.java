package com.juanborges.tourguide;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_categories);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoriesAdapter adapter = new CategoriesAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the TabLayout that will be set up with the ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Get the fragment's position picked in the previous activity
        Bundle extraPosition = getIntent().getExtras();
        if (extraPosition != null)
            viewPager.setCurrentItem(extraPosition.getInt("viewPagerPosition"));
    }
}
