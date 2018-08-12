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

        entertainment.add(new ListItem(R.drawable.teleferico, getString(R.string.teleferico_title),
                getString(R.string.teleferico_description), getString(R.string.teleferico_location),
                getString(R.string.teleferico_schedule), getString(R.string.teleferico_phone)));

        entertainment.add(new ListItem(R.drawable.cinex, getString(R.string.cinex_title),
                getString(R.string.cinex_description), getString(R.string.cinex_location),
                getString(R.string.cinex_schedule), getString(R.string.cinex_phone)));

        entertainment.add(new ListItem(R.drawable.multicine, getString(R.string.multicine_title),
                getString(R.string.multicine_description), getString(R.string.multicine_location),
                getString(R.string.multicine_schedule), getString(R.string.multicine_phone)));

        entertainment.add(new ListItem(R.drawable.ecowild, getString(R.string.ecowild_title),
                getString(R.string.ecowild_description), getString(R.string.ecowild_location),
                getString(R.string.ecowild_schedule), getString(R.string.ecowild_phone)));

        entertainment.add(new ListItem(R.drawable.vantier, getString(R.string.vantier_title),
                getString(R.string.vantier_description), getString(R.string.vantier_location),
                getString(R.string.vantier_schedule), getString(R.string.vantier_phone)));

        ItemTwoAdapter adapter = new ItemTwoAdapter(getActivity(), entertainment);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
