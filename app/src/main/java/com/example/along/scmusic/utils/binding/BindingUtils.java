package com.example.along.scmusic.utils.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.along.scmusic.R;

public final class BindingUtils {

    private BindingUtils() {
        // No-op
    }

    @BindingAdapter("animation")
    public static void startAnimation(View view, AnimationSet animationSet) {
        view.startAnimation(animationSet);
    }

    @BindingAdapter({ "recyclerAdapter" })
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("url")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                .placeholder(R.drawable.ic_logo))
                .into(imageView);
    }
}
