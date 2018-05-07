package com.example.along.scmusic.screen.home;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.Genres;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomeViewModel extends BaseViewModel implements HomeAdapter.OnItemClickListener {

    private static final String KIND = "Trending";
    private static final int LIMIT_5 = 5;
    private static final String ORDER_BY = "created_at";
    private static final int OFFSET_0 = 0;
    private TrackRepository mTrackRepository;
    private Navigator mNavigator;
    private SchedulerProvider mSchedulerProvider;
    private HomeAdapter mRockAdapter;
    private HomeAdapter mAmbientAdapter;
    private HomeAdapter mClassicalAdapter;
    private HomeAdapter mCountryAdapter;
    private SliderAdapter mSliderAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Context mContext;
    public ObservableField<Boolean> mIsAdapterChanged = new ObservableField<>();

    public HomeViewModel(Context context, TrackRepository trackRepository, Navigator navigator,
            SchedulerProvider schedulerProvider) {
        mContext = context.getApplicationContext();
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
        mCompositeDisposable.clear();
    }

    @Override
    public void onItemClicked(Track track, int position) {
        //TODO edit later
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
        getTracksByGenre(Genres.ALTERNATIVEROCK, mRockAdapter);
        getTracksByGenre(Genres.AMBIENT, mAmbientAdapter);
        getTracksByGenre(Genres.CLASSICAL, mClassicalAdapter);
        getTracksByGenre(Genres.COUNTRY, mCountryAdapter);
    }

    private void getTrendingTracks() {
        Disposable disposable = mTrackRepository.getTrendingTracks(KIND, LIMIT_5, ORDER_BY)
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
        Disposable disposable =
                mTrackRepository.getTracksByGenre(Constant.LIMIT_10, genres, OFFSET_0)
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(trackList -> adapter.addData(trackList),
                                throwable -> Toast.makeText(mContext,
                                        throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                        .show());
        mCompositeDisposable.add(disposable);
    }
}
