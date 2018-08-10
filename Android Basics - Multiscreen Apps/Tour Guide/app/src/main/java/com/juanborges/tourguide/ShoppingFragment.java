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

        shopping.add(new ListItem(R.drawable.ccaltos, "C.C. Altos de Santa Maria",
                "Don't be fooled by its size, because it has all the stores needed.\n" +
                        " Go grab a bite at its bakery store. Needing some grocery? Head to the " +
                        " minimarket. Trying to loose a little bit? Step Forward to one of the" +
                        " many night clubs.","Av. Universidad",
                "Mo-Sa: 8:00-21:00", "0274 2663722"));
        shopping.add(new ListItem(R.drawable.ccvilla, "C.C. Villa Los Chorros",
                "This mall is located in a quiet location, visited mostly because its" +
                        " great nightclubs, Garage Rock Bar, Blue Bar, and Ibiza. It also has" +
                        " a variaty of fastfood places and a nice restaurant.",
                "Av. Principal de Los Chorros de Milla","8:00-22:00",
                "0274 2405311"));
        shopping.add(new ListItem(R.drawable.cchistory, "C.C. History Center",
                "Located in el centro of Merida, many tech stores, barbershop, and good" +
                        " prices for shopping. One of the most visited places of el centro.",
                "24 St. Btwn. 2 & 3 Av.","10:00-21:00", "0274 2443461"));
        shopping.add(new ListItem(R.drawable.ccprado, "C.C. Alto Prado",
                "Used to be one of the most visited malls, it has one of the few cinema " +
                        "theaters in the city, the biggest nightclub, and great fastfood places ",
                "Av. Los Proceres","Mo-Fr: 8:00-5:00", "0274 4176124"));
        shopping.add(new ListItem(R.drawable.ccrodeo, "C.C. Rodeo Plaza",
                "The newest mall of Merida and one of the top of the city, it has the " +
                        "best fastfoot places and restaurants, including Avila Burger, Kansas " +
                        "Grill and Caprara. A nightclub has recently opened here and is one of " +
                        "the best","Av. Las Americas","10:00-20:00",
                "0274 5000350"));
        shopping.add(new ListItem(R.drawable.ccpie, "C.C. Pie de Monte",
                "Great for shopping. This great located mall offers a great variety" +
                        " of fastfoods, coffees and restaurants, including Kansas Grill," +
                        " Fraga Cafe, and more.","Av. Los Proceres","10:00-21:00",
                "0424 7219267"));
        shopping.add(new ListItem(R.drawable.cctapias, "C.C. Las Tapias",
                "This old mall has one of the few cinema theaters of Merida, nice " +
                        " shopping places of all types and prices, a great nightclub, and a " +
                        "good chill bar.","Av. Andres Bello","11:00-20:00", "0274 2661746"));
        shopping.add(new ListItem(R.drawable.ccmilenium, "C.C. Milenium",
                "This greatly located mall has one of the best stores for shopping, " +
                        "fastfoods, and restaurants of the city; t has a great dance academy and" +
                        " big parking lots.","Av. Andres Bello","7:00-21:00",
                "0274 2408191"));

        ItemTwoAdapter adapter = new ItemTwoAdapter(getActivity(), shopping);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
