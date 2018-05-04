package com.example.along.scmusic.screen.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.databinding.FragmentSliderBinding;
import com.example.along.scmusic.screen.BaseFragment;

public class SliderFragment extends BaseFragment {

    private static final String ARGUMENT_ARTWORK_URL = "ARGUMENT_ARTWORK_URL";
    private SliderViewModel mViewModel;

    public static SliderFragment newInstance(Track track) {
        SliderFragment fragment = new SliderFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ARTWORK_URL, track.getArtworkUrl());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentSliderBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_slider, container, false);
        mViewModel = new SliderViewModel();
        Bundle args = getArguments();
        if (args != null) {
            mViewModel.setImageUrl(args.getString(ARGUMENT_ARTWORK_URL));
        }
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
