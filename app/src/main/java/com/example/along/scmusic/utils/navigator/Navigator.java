package com.example.along.scmusic.utils.navigator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.bumptech.glide.util.Preconditions;

public class Navigator {

    @NonNull
    private Activity mActivity;
    @NonNull
    private Fragment mFragment;

    public Navigator(@NonNull Activity activity) {
        mActivity = activity;
    }

    public Navigator(@NonNull Fragment fragment) {
        mFragment = fragment;
        mActivity = Preconditions.checkNotNull(fragment.getActivity());
    }
}
