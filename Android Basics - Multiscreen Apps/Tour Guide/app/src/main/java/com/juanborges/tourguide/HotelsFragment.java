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

        hotels.add(new ListItem(R.drawable.hotel_belensate, "Hotel Belensate","Urb. La Hacienda", "0274 2663722"));
        hotels.add(new ListItem(R.drawable.hotel_convencion, "Hotel Convencion Boutique","Av. Alberto Carnevali" ,"0274 2405311"));
        hotels.add(new ListItem(R.drawable.hotel_terraza, "Hotel La Terraza","Av. Principal de Los Chorros de Milla", "0274 244153"));
        hotels.add(new ListItem(R.drawable.hotel_serrano, "Hotel El Serrano","Av. Los Proceres", "0274 2667447"));
        hotels.add(new ListItem(R.drawable.posada_sol, "Posada Casa Sol","Avenida 4", "0274 2524164"));
        hotels.add(new ListItem(R.drawable.hotel_chama, "Hotel Chama","Avenida 4" ,"0274 2524851"));
        hotels.add(new ListItem(R.drawable.hotel_pedregosa, "Hotel La Pedregosa","La Pedregosa","0274 2663181"));

        ItemAdapter adapter = new ItemAdapter(getActivity(), hotels);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
