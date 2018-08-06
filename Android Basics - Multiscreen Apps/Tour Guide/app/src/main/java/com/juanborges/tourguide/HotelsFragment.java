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
public class HotelsFragment extends Fragment {

    public HotelsFragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotels, container, false);

        final ArrayList<ListItem> hotels = new ArrayList<>();

        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel Belensate", "0274 2663722"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel Convencion Boutique", "0274 2405311"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel Mucubaji", "0274 2443461"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel La Terraza", "0274 244153"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel El Serrano", "0274 2667447"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel Mistafi", "0274 2510729"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Posada Casa Sol", "0274 2524164"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel Chama", "0274 2524851"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel Teleferico", "0412 7730148"));
        hotels.add(new ListItem(R.drawable.hotelroom, "Hotel La Pedregosa", "0274 2663181"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), hotels);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
