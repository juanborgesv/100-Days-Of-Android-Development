package com.juanborges.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropiate {@link android.app.Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new MondayFragment();
        else if (position == 1)
            return new TuesdayFragment();
        else if (position == 2)
            return new WednesdayFragment();
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Monday";
        else if (position == 1)
            return "Tuesday";
        else if (position == 2)
            return "Wednesday";
        return null;
    }

    // 3 represents the amount of fragments that the Adapter is going to manage
    @Override
    public int getCount() {
        return 3;
    }
}
