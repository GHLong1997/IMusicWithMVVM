package com.example.along.scmusic.screen.main;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.example.along.scmusic.R;
import com.example.along.scmusic.widget.viewpager.UnSwipeViewPager;

/**
 * Created by long on 02/05/2018
 */

public final class MainBindingUtils {

    private MainBindingUtils() {
    }

    @BindingAdapter("adapter")
    public static void setAdapterForViewPager(UnSwipeViewPager viewPager, MainAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
    }

    @BindingAdapter("viewPager")
    public static void setUpWithViewPagerForTabLayout(TabLayout tabLayout, ViewPager viewPager) {
        viewPager.addOnAdapterChangeListener((viewPager1, oldAdapter, newAdapter) -> {
            tabLayout.setupWithViewPager(viewPager1, true);
            addTabIcons(tabLayout, viewPager1);
        });
    }

    private static void addTabIcons(TabLayout tabLayout, ViewPager viewPager) {
        int[] icons = { R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_download };
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        tabLayout.getTabAt(viewPager.getCurrentItem())
                .getIcon()
                .setColorFilter(tabLayout.getResources().getColor(R.color.colorAccent),
                        PorterDuff.Mode.SRC_IN);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon()
                        .setColorFilter(tabLayout.getResources().getColor(R.color.colorAccent),
                                PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon()
                        .setColorFilter(tabLayout.getResources().getColor(R.color.color_gray_tab),
                                PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //No-op
            }
        });
    }
}
