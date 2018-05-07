package com.example.along.scmusic.screen.seemore;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.EndlessRecyclerViewScrollListener;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.Genres;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by Long .
 */

public class SeeMoreMusicViewModel extends BaseViewModel implements SeeMoreMusicAdapter.OnItemClickListener {

    private Context mContext;
    private SeeMoreMusicAdapter mAdapter;
    private TrackRepository mTrackRepository;
    private SchedulerProvider mSchedulerProvider;
    private Navigator mNavigator;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;
    public ObservableField<String> mGenreObservable = new ObservableField<>();

    public SeeMoreMusicViewModel(Context context, TrackRepository trackRepository,
            SchedulerProvider schedulerProvider, Navigator navigator, SeeMoreMusicAdapter adapter,
            String genre) {
        mContext = context;
        mTrackRepository = trackRepository;
        mSchedulerProvider = schedulerProvider;
        mNavigator = navigator;
        mAdapter = adapter;
        mGenreObservable.set(genre);
        mAdapter.setItemClickListener(this);
    }

    @Override
    protected void onStart() {
        getTracksByGenres(mGenreObservable.get());
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onItemClicked(Track track, int position) {
        //TODO edit later
    }

    public SeeMoreMusicAdapter getAdapter() {
        return mAdapter;
    }

    private void getTracksByGenres(@Genres String genres) {
        Disposable disposable =
                mTrackRepository.getTracksByGenre(Constant.LIMIT_10, genres, mAdapter.getOffset())
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(new Consumer<List<Track>>() {
                            @Override
                            public void accept(List<Track> trackList) throws Exception {
                                mAdapter.addData(trackList);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(mContext, throwable.getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
        mCompositeDisposable.add(disposable);
    }

    public RecyclerView.OnScrollListener getEndlessScrollListener(
            RecyclerView.LayoutManager layoutManager) {
        mEndlessScrollListener =
                new EndlessRecyclerViewScrollListener((GridLayoutManager) layoutManager) {
                    @Override
                    public void onLoadMore() {
                        getTracksByGenres(mGenreObservable.get());
                    }
                };
        return mEndlessScrollListener;
    }
}
