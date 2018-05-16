package com.example.along.scmusic.screen.main;

import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;

public class MainViewModel extends BaseViewModel {

    public static final int OFF_SCREEN_PAGE_LIMIT = 2;

    private FragmentManager mFragmentManager;
    private MainAdapter mAdapter;

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();
    public ObservableField<Boolean> mTrackVisibility = new ObservableField<>(false);



    public MainViewModel(FragmentManager fragmentManager, MainAdapter adapter) {
        mAdapter = adapter;
        mFragmentManager = fragmentManager;
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

    public void setData(Track track) {
        mTrackObservableField.set(track);
        mTrackVisibility.set(true);
    }

    public void onNextTrack() {
//        PlayMusicFragment playMusicFragment =
//                (PlayMusicFragment) mFragmentManager.findFragmentByTag(
//                        PlayMusicFragment.class.getSimpleName());
//        playMusicFragment.nextTrack();
    }

    public void onPreTrack(View view) {
        PlayMusicFragment playMusicFragment =
                (PlayMusicFragment) mFragmentManager.findFragmentByTag(
                        PlayMusicFragment.class.getSimpleName());
        playMusicFragment.preTrack(view);
    }

    public void onImagePlayMusicClicked(View view) {
        PlayMusicFragment playMusicFragment =
                (PlayMusicFragment) mFragmentManager.findFragmentByTag(
                        PlayMusicFragment.class.getSimpleName());
        playMusicFragment.onPlayAndPauseTrack(view);
    }

    public void onShowFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        PlayMusicFragment playMusicFragment = (PlayMusicFragment) mFragmentManager.findFragmentByTag(
                PlayMusicFragment.class.getSimpleName());
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                R.anim.slide_out_down, R.anim.slide_out_up);
        if (playMusicFragment != null && playMusicFragment.isHidden()) {
            fragmentTransaction.show(playMusicFragment);
            fragmentTransaction.commit();
        }
    }
}
