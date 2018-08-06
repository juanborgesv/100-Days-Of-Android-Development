package com.juanborges.tourguide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoriesAdapter extends FragmentPagerAdapter {

    public CategoriesAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new HotelsFragment();
        else if (position == 1)
            return new RestaurantsFragment();
        else if (position == 2)
            return new ShoppingFragment();
        else if (position == 3)
            return new EntertainmentFragment();

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Hotels";
        else if (position == 1)
            return "Food";
        else if (position == 2)
            return "Shopping";
        else if (position == 3)
            return "Entertainment";

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
