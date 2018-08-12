package com.juanborges.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {

    public RestaurantsFragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurants, container, false);

        final ArrayList<ListItem> restaurants = new ArrayList<>();

        restaurants.add(new ListItem(R.drawable.r_chistorra, getString(R.string.chistorra_title),
                getString(R.string.chistorra_description), getString(R.string.chistorra_schedule),
                getString(R.string.chistorra_phone)));

        restaurants.add(new ListItem(R.drawable.r_burger_bar, getString(R.string.burger_title),
                getString(R.string.burger_location), getString(R.string.burger_schedule),
                getString(R.string.burger_phone)));

        restaurants.add(new ListItem(R.drawable.r_fraga_cafe, getString(R.string.fraga_title),
                getString(R.string.fraga_location), getString(R.string.fraga_schedule),
                getString(R.string.fraga_phone)));

        restaurants.add(new ListItem(R.drawable.r_da_enzo, getString(R.string.daenzo_title),
                getString(R.string.daenzo_location), getString(R.string.daenzo_schedule),
                getString(R.string.daenzo_phone)));

        restaurants.add(new ListItem(R.drawable.r_avila_burger, getString(R.string.avila_title),
                getString(R.string.avila_location), getString(R.string.avila_schedule),
                getString(R.string.avila_phone)));

        restaurants.add(new ListItem(R.drawable.r_gastrobar_5101, getString(R.string.gastrobar_title),
                getString(R.string.gastrobar_location), getString(R.string.gastrobar_schedule),
                getString(R.string.gastrobar_phone)));

        restaurants.add(new ListItem(R.drawable.r_les_golfes, getString(R.string.lesgolfes_title),
                getString(R.string.lesgolfes_location), getString(R.string.lesgolfes_schedule),
                getString(R.string.lesgolfes_phone)));

        ItemAdapter adapter = new ItemAdapter(getActivity(), restaurants);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
