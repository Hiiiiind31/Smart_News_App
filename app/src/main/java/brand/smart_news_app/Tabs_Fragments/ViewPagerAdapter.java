package brand.smart_news_app.Tabs_Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import brand.smart_news_app.Tabs_Fragments.Fragments.Fragment_Main;
import brand.smart_news_app.Tabs_Fragments.Fragments.Fragment_Profile;
import brand.smart_news_app.Tabs_Fragments.Fragments.Fragment_ReadLater;
import brand.smart_news_app.Tabs_Fragments.Fragments.fragment_discover;

/**
 * Created by Hind on 12/9/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    // Tab Titles
    private String tabtitles[] = new String[]{ "الحساب", "إقرأ لاحقا", "استكشف", "الرئيسية"};
    Context context;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                Fragment_Profile fragmenttab1 = new Fragment_Profile();
                return fragmenttab1;

            // Open FragmentTab2.java
            case 1:
                Fragment_ReadLater fragmenttab2 = new Fragment_ReadLater();
                return fragmenttab2;

            // Open FragmentTab3.java
            case 2:

                fragment_discover fragmenttab3 = new fragment_discover();
                return fragmenttab3;
            // Open FragmentTab3.java
            case 3:
                Fragment_Main fragmenttab4 = new Fragment_Main();
                return fragmenttab4;

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
 }