package com.example.along.scmusic.screen.home;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public final class HomeBindingUtils {

    private HomeBindingUtils() {
    }

    /**
     * Create bottom dots
     */
    @BindingAdapter({ "viewPager", "adapterChanged" })
    public static void setViewPager(CircleIndicator circleIndicator, ViewPager viewPager,
            boolean isAdapterChanged) {
        if (isAdapterChanged) {
            circleIndicator.setViewPager(viewPager);
        }
    }
}
