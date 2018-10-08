package com.juanborges.tourguide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImagesAdapter extends FragmentPagerAdapter {
    public ImagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new FImageFragment();
        else if (position == 1)
            return new SImageFragment();
        else if (position == 2)
            return new TImageFragment();

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
