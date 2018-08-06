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

        restaurants.add(new ListItem(R.drawable.hotelroom, "La chistorra", "0274 2440021"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Paramo Grill", "0274 2441968"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Burger Bar Pub & Café", "0274 2528627"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Fraga Cafe", "0274 9350481"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Pizzería Da Enzo", "0274 2662881"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Ávila Burger", "0274 5000415"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "5101 Gastrobar", "0274 2520211"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Les Golfes De Salioli", "0274 2445426"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Caprara Pizzeria", "0274 2662057"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Pastelitos La Parroquia", "0414 7485160"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Dolce And Bake", "0274 2712965"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Lusitano´s", "0274 2214748"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Hostal Madrid Restaurante", "0274 2526146"));
        restaurants.add(new ListItem(R.drawable.hotelroom, "Garage Rock Bar", "0424 7055363"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), restaurants);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
