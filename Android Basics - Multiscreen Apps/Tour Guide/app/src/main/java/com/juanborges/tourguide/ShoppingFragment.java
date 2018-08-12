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

        shopping.add(new ListItem(R.drawable.ccaltos, getString(R.string.altos_title),
                getString(R.string.altos_description), getString(R.string.altos_location),
                getString(R.string.altos_schedule), getString(R.string.altos_phone)));

        shopping.add(new ListItem(R.drawable.ccvilla, getString(R.string.villa_title),
                getString(R.string.villa_description), getString(R.string.villa_location),
                getString(R.string.villa_schedule), getString(R.string.villa_phone)));

        shopping.add(new ListItem(R.drawable.cchistory, getString(R.string.history_title),
                getString(R.string.history_description), getString(R.string.history_location),
                getString(R.string.history_schedule), getString(R.string.history_phone)));

        shopping.add(new ListItem(R.drawable.ccprado, getString(R.string.prado_title),
                getString(R.string.prado_description), getString(R.string.prado_location),
                getString(R.string.prado_schedule), getString(R.string.prado_phone)));

        shopping.add(new ListItem(R.drawable.ccrodeo, getString(R.string.rodeo_title),
                getString(R.string.rodeo_description), getString(R.string.rodeo_location),
                getString(R.string.rodeo_schedule), getString(R.string.rodeo_phone)));

        shopping.add(new ListItem(R.drawable.ccpie, getString(R.string.pie_title),
                getString(R.string.pie_description), getString(R.string.pie_location),
                getString(R.string.pie_schedule), getString(R.string.pie_phone)));

        shopping.add(new ListItem(R.drawable.cctapias, getString(R.string.tapias_title),
                getString(R.string.tapias_description), getString(R.string.tapias_location),
                getString(R.string.tapias_schedule), getString(R.string.tapias_phone)));

        shopping.add(new ListItem(R.drawable.ccmilenium, getString(R.string.milenium_title),
                getString(R.string.milenium_description), getString(R.string.milenium_location),
                getString(R.string.milenium_schedule), getString(R.string.milenium_phone)));

        ItemTwoAdapter adapter = new ItemTwoAdapter(getActivity(), shopping);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
