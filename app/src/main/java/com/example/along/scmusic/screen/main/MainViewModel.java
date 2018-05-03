package com.example.along.scmusic.screen.main;

import com.example.along.scmusic.screen.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public static final int OFF_SCREEN_PAGE_LIMIT = 2;

    private MainAdapter mAdapter;

    public MainViewModel(MainAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected void onStart() {
        //Todo edit later
    }

    @Override
    protected void onStop() {
        //Todo edit later
    }

    public MainAdapter getAdapter() {
        return mAdapter;
    }
}
