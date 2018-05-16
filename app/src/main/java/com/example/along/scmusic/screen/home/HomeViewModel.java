package com.example.along.scmusic.screen.home;

import android.content.Context;
import android.databinding.ObservableField;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.screen.seemore.SeeMoreMusicActivity;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.Genres;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends BaseViewModel implements HomeAdapter.OnItemClickListener {

    private static final String KIND = "Trending";
    private static final String ORDER_BY = "created_at";
    private static final int LIMIT_FIVE = 5;
    private static final int OFFSET = 0;
    private static final int VALUES_OFFSET_AFTER_REQUEST_DATA_ONCE = 10;
    private static final int REQUEST_CODE = 111;

    private Context mContext;
    private TrackRepository mTrackRepository;
    private Navigator mNavigator;
    private SchedulerProvider mSchedulerProvider;
    private HomeAdapter mRockAdapter;
    private HomeAdapter mAmbientAdapter;
    private HomeAdapter mClassicalAdapter;
    private HomeAdapter mCountryAdapter;
    private SliderAdapter mSliderAdapter;
    private List<Track> mTracks = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private OnOpenFragmentListener mFragmentListener;
    public ObservableField<Boolean> mIsAdapterChanged = new ObservableField<>();
    public String mRock = Genres.ALTERNATIVEROCK;
    public String mAmbient = Genres.AMBIENT;
    public String mClassical = Genres.CLASSICAL;
    public String mCountry = Genres.COUNTRY;

    public HomeViewModel(Context context, TrackRepository trackRepository, Navigator navigator,
            SchedulerProvider schedulerProvider, OnOpenFragmentListener fragmentListener) {
        mContext = context.getApplicationContext();
        mTrackRepository = trackRepository;
        mNavigator = navigator;
        mSchedulerProvider = schedulerProvider;
        mFragmentListener = fragmentListener;
    }

    @Override
    protected void onStart() {
        //TODO edit later
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onItemClicked(List<Track> tracks, int position) {
        mFragmentListener.onNewFragment(R.id.constraintLayoutMain,
                PlayMusicFragment.class.getSimpleName(), tracks, position,
                VALUES_OFFSET_AFTER_REQUEST_DATA_ONCE);
    }

    public void setAdapter(HomeAdapter rockAdapter, HomeAdapter ambientAdapter,
            HomeAdapter classicalAdapter, HomeAdapter countryAdapter, SliderAdapter sliderAdapter) {
        mRockAdapter = rockAdapter;
        mAmbientAdapter = ambientAdapter;
        mClassicalAdapter = classicalAdapter;
        mCountryAdapter = countryAdapter;
        mSliderAdapter = sliderAdapter;
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

    public SliderAdapter getSliderAdapter() {
        return mSliderAdapter;
    }

    public void loadData() {
        getTrendingTracks();
        getTracksByGenre(mRock, mRockAdapter);
        getTracksByGenre(mAmbient, mAmbientAdapter);
        getTracksByGenre(mClassical, mClassicalAdapter);
        getTracksByGenre(mCountry, mCountryAdapter);
    }

    private void getTrendingTracks() {
        Disposable disposable = mTrackRepository.getTrendingTracks(KIND, LIMIT_FIVE, ORDER_BY)
                .flattenAsObservable(trackList -> trackList)
                .map(track -> SliderFragment.newInstance(track))
                .toList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(sliderFragments -> {
                    mSliderAdapter.addData(sliderFragments);
                    mIsAdapterChanged.set(true);
                }, throwable -> Toast.makeText(mContext, throwable.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show());
        mCompositeDisposable.add(disposable);
    }

    private void getTracksByGenre(@Genres String genres, HomeAdapter adapter) {
        Disposable disposable = mTrackRepository.getTracksByGenre(20, genres, OFFSET)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(trackList -> adapter.addData(trackList),
                        throwable -> Toast.makeText(mContext, throwable.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show());
        mCompositeDisposable.add(disposable);
    }

    public void goToSeeMoreMusicActivity(View view, String genre) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.GENRE, genre);
        mNavigator.startActivityForResult(SeeMoreMusicActivity.class, bundle, REQUEST_CODE);
    }
}
