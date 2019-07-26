package com.dvtweather.tshepo.dvtweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *
 */

public class PagerMain extends FragmentStatePagerAdapter {

    //counts number of tabs.
    private int tabCount;

    public PagerMain(FragmentManager fm, int numTabs)
    {
        super(fm);
        this.tabCount = numTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new FragMyLocation();

            case 1:
                return new FragPassport();
            default:

                return null;
        }

        //return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
