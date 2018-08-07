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

        restaurants.add(new ListItem(R.drawable.chistorra, "La chistorra", "0274 2440021"));
        restaurants.add(new ListItem(R.drawable.burger_bar, "Burger Bar Pub & Café", "0274 2528627"));
        restaurants.add(new ListItem(R.drawable.fraga_cafe, "Fraga Cafe", "0274 9350481"));
        restaurants.add(new ListItem(R.drawable.da_enzo, "Pizzería Da Enzo", "0274 2662881"));
        restaurants.add(new ListItem(R.drawable.avila_burger, "Ávila Burger", "0274 5000415"));
        restaurants.add(new ListItem(R.drawable.gastrobar_5101, "5101 Gastrobar", "0274 2520211"));
        restaurants.add(new ListItem(R.drawable.les_golfes, "Les Golfes De Salioli", "0274 2445426"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), restaurants);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
