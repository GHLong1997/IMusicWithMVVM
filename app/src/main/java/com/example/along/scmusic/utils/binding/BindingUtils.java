package com.example.along.scmusic.utils.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.view.animation.AnimationSet;

public final class BindingUtils {

    private BindingUtils() {
        // No-op
    }

    @BindingAdapter("animation")
    public static void startAnimation(View view, AnimationSet animationSet) {
        view.startAnimation(animationSet);
    }
}
