package com.example.along.scmusic.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.along.scmusic.screen.download.DownloadFragment;
import com.example.along.scmusic.screen.home.HomeFragment;
import com.example.along.scmusic.screen.search.SearchFragment;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    MainAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new SearchFragment());
        mFragmentList.add(new DownloadFragment());
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.size() > 0 ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }
}
