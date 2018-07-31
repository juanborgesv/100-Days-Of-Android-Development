package com.juanborges.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    Context myContext;

    public MyFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new NumbersFragment();
        else if (position == 1)
            return new FamilyFragment();
        else if (position == 2)
            return new ColorsFragment();
        else if (position == 3)
            return new PhrasesFragment();

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Numbers";
        } else if (position == 1) {
            return "Family";
        } else if (position == 2) {
            return "Colors";
        } else {
            return "Phrases";
        }
    }

    // Represents the amount of fragments that the Adapter is going to manage
    @Override
    public int getCount() {
        return 4;
    }
}
