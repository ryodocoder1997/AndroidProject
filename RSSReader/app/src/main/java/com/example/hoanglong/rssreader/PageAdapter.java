package com.example.hoanglong.rssreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TheThao_TabFragment tab_TheThao = new TheThao_TabFragment();
                return tab_TheThao;
            case 1:
                TheGioi_TabFragment tab_TheGioi = new TheGioi_TabFragment();
                return tab_TheGioi;
            case 2:
                PhapLuat_TabFragment tab_PhapLuat = new PhapLuat_TabFragment();
                return tab_PhapLuat;
            case 3:
                GiaiTri_TabFragment tab_GiaiTri = new GiaiTri_TabFragment();
                return tab_GiaiTri;
            case 4:
                DuLich_TabFragment tab_DuLich = new DuLich_TabFragment();
                return tab_DuLich;
            default:
                return null;
        }
    }

    //Get number to create ID
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
