package com.juanborges.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EntertainmentFragment extends Fragment {

    public EntertainmentFragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hotels, container, false);

        final ArrayList<ListItem> entertainment = new ArrayList<>();
        entertainment.add(new ListItem(R.drawable.hotelroom, "Cinex", "0274 2448866"));
        entertainment.add(new ListItem(R.drawable.hotelroom, "Multicine Las Tapias", "0274 2660349"));
        entertainment.add(new ListItem(R.drawable.hotelroom, "Sistema De Teleférico Mukumbary", "0414 7035900"));
        entertainment.add(new ListItem(R.drawable.hotelroom, "Ecowild", "0414 3740041"));
        entertainment.add(new ListItem(R.drawable.hotelroom, "Venezuela de Antier", "0274 2450359"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), entertainment);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
