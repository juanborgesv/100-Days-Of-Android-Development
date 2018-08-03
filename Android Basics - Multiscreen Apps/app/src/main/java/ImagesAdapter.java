import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.juanborges.tourguide.ImageFragment;

public class ImagesAdapter extends FragmentPagerAdapter {

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return new ImageFragment();
        }


        return null;
    }
}
