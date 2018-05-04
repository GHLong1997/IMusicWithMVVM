package com.example.along.scmusic.screen.home;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends FragmentPagerAdapter {

    private List<SliderFragment> mSliderFragments = new ArrayList<>();

    public SliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mSliderFragments != null ? mSliderFragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return mSliderFragments != null ? mSliderFragments.size() : 0;
    }

    void addData(@NonNull List<SliderFragment> sliderFragments) {
        mSliderFragments.addAll(sliderFragments);
        notifyDataSetChanged();
    }
}
