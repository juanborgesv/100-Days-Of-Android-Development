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

        restaurants.add(new ListItem(R.drawable.r_chistorra, "La chistorra Bar & Restaurant", "Av. Alberto Carnevali","Tu-Su: 12:00-0:00","0274 2440021"));
        restaurants.add(new ListItem(R.drawable.r_burger_bar, "Burger Bar Pub & Café","Av. 4 Btwn 17 & 18 St.","Tu-Sa: 12:00-1:00 & Su-Mo: 13:00-23:00", "0274 2528627"));
        restaurants.add(new ListItem(R.drawable.r_fraga_cafe, "Fraga Cafe","Av. los Próceres, C.C. Pie de Monte","Tu-Th: 16:00-23:00 & Fr-Su: 12:30-23:00", "0274 9350481"));
        restaurants.add(new ListItem(R.drawable.r_da_enzo, "Pizzería Da Enzo","C.C. San Cristóbal","We-Su: 17:00-22:00","0274 2662881"));
        restaurants.add(new ListItem(R.drawable.r_avila_burger, "Ávila Burger","Av. Las Americas, C.C. Rodeo Plaza","Mo-Th: 12:00-22:00 & Fr-Su: 8:00-22:00", "0274 5000415"));
        restaurants.add(new ListItem(R.drawable.r_gastrobar_5101, "5101 Gastrobar","Av. 3 Btwn 19 & 20 St.","12:00-1:00", "0274 2520211"));
        restaurants.add(new ListItem(R.drawable.r_les_golfes, "Les Golfes De Salioli","Av. Alberto Carnevali","Mo-Tu: 12:00-13:00 & Th-Sa: 12:00-13:00", "0274 2445426"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), restaurants);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
