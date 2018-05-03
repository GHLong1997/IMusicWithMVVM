package com.example.along.scmusic.screen.home;

import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel implements HomeAdapter.OnItemClickListener {

    private TrackRepository mTrackRepository;
    private Navigator mNavigator;
    private SchedulerProvider mSchedulerProvider;
    private HomeAdapter mRockAdapter;
    private HomeAdapter mAmbientAdapter;
    private HomeAdapter mClassicalAdapter;
    private HomeAdapter mCountryAdapter;

    public HomeViewModel(TrackRepository trackRepository, Navigator navigator,
            SchedulerProvider schedulerProvider) {
        mTrackRepository = trackRepository;
        mNavigator = navigator;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    protected void onStart() {
        //TODO edit later
    }

    @Override
    protected void onStop() {
        //TODO edit later
    }

    @Override
    public void onItemClicked(Track track, int position) {
        //TODO edit later
    }

    public void setAdapter(HomeAdapter rockAdapter, HomeAdapter ambientAdapter,
            HomeAdapter classicalAdapter, HomeAdapter countryAdapter) {
        mRockAdapter = rockAdapter;
        mAmbientAdapter = ambientAdapter;
        mClassicalAdapter = classicalAdapter;
        mCountryAdapter = countryAdapter;
        mRockAdapter.setItemClickListener(this);
        mAmbientAdapter.setItemClickListener(this);
        mClassicalAdapter.setItemClickListener(this);
        mCountryAdapter.setItemClickListener(this);
    }

    public HomeAdapter getRockAdapter() {
        return mRockAdapter;
    }

    public HomeAdapter getAmbientAdapter() {
        return mAmbientAdapter;
    }

    public HomeAdapter getClassicalAdapter() {
        return mClassicalAdapter;
    }

    public HomeAdapter getCountryAdapter() {
        return mCountryAdapter;
    }
}
