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

        hotels.add(new ListItem(R.drawable.r_hotel_belensate, getString(R.string.belensate_title),
                getString(R.string.belensate_location), getString(R.string.belensate_phone)));

        hotels.add(new ListItem(R.drawable.r_hotel_convencion, getString(R.string.convencion_title),
                getString(R.string.convencion_location), getString(R.string.convencion_phone)));
        hotels.add(new ListItem(R.drawable.r_hotel_terraza, getString(R.string.terraza_title),
                getString(R.string.terraza_location), getString(R.string.terraza_phone)));
        hotels.add(new ListItem(R.drawable.r_hotel_serrano, getString(R.string.serrano_title),
                getString(R.string.serrano_location), getString(R.string.serrano_phone)));
        hotels.add(new ListItem(R.drawable.r_posada_sol, getString(R.string.posada_title),
                getString(R.string.posada_location), getString(R.string.posada_phone)));
        hotels.add(new ListItem(R.drawable.r_hotel_chama, getString(R.string.chama_title),
                getString(R.string.chama_location), getString(R.string.chama_phone)));
        hotels.add(new ListItem(R.drawable.r_hotel_pedregosa, getString(R.string.pedregosa_title),
                getString(R.string.predregosa_location), getString(R.string.pedregosa_phone)));

        ItemAdapter adapter = new ItemAdapter(getActivity(), hotels);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
