package com.example.along.scmusic.screen.playmusic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.EndlessRecyclerViewScrollListener;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.Genres;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PlayMusicViewModel extends BaseViewModel
        implements PlayMusicAdapter.OnItemClickListener {

    private static final int DEFAULT_POSITION = 0;

    private Context mContext;
    private PlayMusicAdapter mAdapter;
    private TrackRepository mTrackRepository;
    private SchedulerProvider mSchedulerProvider;
    private Navigator mNavigator;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;
    private int mTrackPosition;
    private String mGenre;

    public PlayMusicViewModel(Context context, TrackRepository repository, Navigator navigator,
            SchedulerProvider schedulerProvider, PlayMusicAdapter adapter) {

        mContext = context.getApplicationContext();
        mTrackRepository = repository;
        mSchedulerProvider = schedulerProvider;
        mNavigator = navigator;
        mAdapter = adapter;
        mAdapter.setItemClickListener(this);
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
    public void onItemClicked(int position) {
        changeColorItem(position);
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public void setTrackPosition(int position) {
        mTrackPosition = position;
    }

    public PlayMusicAdapter getAdapter() {
        return mAdapter;
    }

    private void getTracksByGenres(@Genres String genres) {
        Disposable disposable =
                mTrackRepository.getTracksByGenre(Constant.LIMIT_TEN, genres, mAdapter.getOffset())
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(trackList -> mAdapter.addData(trackList),
                                throwable -> Toast.makeText(mContext,
                                        throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                        .show());
        mCompositeDisposable.add(disposable);
    }

    public RecyclerView.OnScrollListener getEndlessScrollListener(
            RecyclerView.LayoutManager layoutManager) {
        mEndlessScrollListener =
                new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
                    @Override
                    public void onLoadMore() {
                        getTracksByGenres(mGenre);
                    }
                };
        return mEndlessScrollListener;
    }

    private void changeColorItem(int position) {
        mAdapter.addPosition(position);
        mAdapter.notifyItemRangeChanged(DEFAULT_POSITION, mAdapter.getItemCount());
    }
}
