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

        shopping.add(new ListItem(R.drawable.ccaltos, "C.C. Altos de Santa Maria","This is a description","Av. Universidad","Mo-Sa: 8:00-21:00", "0274 2663722"));
        shopping.add(new ListItem(R.drawable.ccvilla, "C.C. Villa Los Chorros","This is a description","Av. Principal de Los Chorros de Milla","8:00-22:00", "0274 2405311"));
        shopping.add(new ListItem(R.drawable.cchistory, "C.C. History Center","This is a description","24 St. Btwn. 2 & 3 Av.","10:00-21:00", "0274 2443461"));
        shopping.add(new ListItem(R.drawable.ccprado, "C.C. Alto Prado","This is a description","Av. Los Proceres","Mo-Fr: 8:00-5:00", "0274 4176124"));
        shopping.add(new ListItem(R.drawable.ccrodeo, "C.C. Rodeo Plaza","This is a description","Av. Las Americas","10:00-20:00", "0274 5000350"));
        shopping.add(new ListItem(R.drawable.ccpie, "C.C. Pie de Monte","This is a description","Av. Los Proceres","10:00-21:00", "0424 7219267"));
        shopping.add(new ListItem(R.drawable.cctapias, "C.C. Las Tapias","This is a description","Av. Andres Bello","11:00-20:00", "0274 2661746"));
        shopping.add(new ListItem(R.drawable.ccmilenium, "C.C. Milenium","This is a description","Av. Andres Bello","7:00-21:00", "0274 2408191"));

        ItemTwoAdapter adapter = new ItemTwoAdapter(getActivity(), shopping);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
