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
public class ShoppingFragment extends Fragment {

    public ShoppingFragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotels, container, false);

        final ArrayList<ListItem> shopping = new ArrayList<>();

        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Altos de Santa Maria", "0274 2663722"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Villa Los Chorros", "0274 2405311"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. History Center", "0274 2443461"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Alto Prado", "0274 244153"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Rodeo Plaza", "0274 2667447"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Pie de Monte", "0274 2510729"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Las Tapias", "0274 244153"));
        shopping.add(new ListItem(R.drawable.hotelroom, "C.C. Milenium", "0274 2667447"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), shopping);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
