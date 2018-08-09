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
        entertainment.add(new ListItem(R.drawable.cinex,"Cinex","Regular",
                "C.C. Alto Prado, Av. Los Proceres", "Time not specified",
                "0274 2448866"));
        entertainment.add(new ListItem(R.drawable.multicine, "Multicine Las Tapias",
                "Regular","C.C. Las Tapias, Av. Andres Bello",
                "Time not specified", "0274 2660349"));
        entertainment.add(new ListItem(R.drawable.teleferico,
                "Sistema De Teleférico Mukumbari","Its base is located in the " +
                "Venezuelan city of Mérida at an altitude of 1,640 metres (5,380 ft), and its " +
                "terminus is on Pico Espejo, at 4,765 metres (15,633 ft). \n\nIt is the highest and " +
                "second longest cable car in the world for just 500 meters, but is in first place" +
                " for being the only one which combined such height and length. \n\nMérida Cable Car" +
                " is a journey of 12.5 kilometers, reaching a height of 4,765 meters, making it" +
                " an engineering marvel that is one of a kind and has over 40 years of history.",
                "Plaza Las Heroinas","Tu-Su: 7:00 - 13:00", "0414 7035900"));
        entertainment.add(new ListItem(R.drawable.ecowild, "Ecowild","Good","El Valle, Via la Culata","9:00 - 17:00", "0414 3740041"));
        entertainment.add(new ListItem(R.drawable.vantier, "Venezuela de Antier","Good","5111, Merida","9:00 - 7:00", "0274 2450359"));

        ItemTwoAdapter adapter = new ItemTwoAdapter(getActivity(), entertainment);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
