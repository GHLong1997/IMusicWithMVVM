package com.example.along.scmusic.screen.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.FragmentHomeBinding;
import com.example.along.scmusic.screen.BaseFragment;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;

public class HomeFragment extends BaseFragment {

    private HomeViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());
        FragmentHomeBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mViewModel = new HomeViewModel(trackRepository, new Navigator(getActivity()),
                SchedulerProvider.getInstance());
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
