package com.example.along.scmusic.screen.search;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.EndlessRecyclerViewScrollListener;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by long on 21/05/2018
 */

public class SearchViewModel extends BaseViewModel implements SearchAdapter.OnItemClickListener {

    private Context mContext;
    private TrackRepository mTrackRepository;
    private Navigator mNavigator;
    private SchedulerProvider mSchedulerProvider;
    private SearchAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;
    private OnOpenFragmentListener mFragmentListener;
    private String mName;

    public SearchViewModel(Context context, TrackRepository trackRepository, Navigator navigator,
            SchedulerProvider schedulerProvider, OnOpenFragmentListener fragmentListener) {
        mContext = context;
        mTrackRepository = trackRepository;
        mNavigator = navigator;
        mSchedulerProvider = schedulerProvider;
        mFragmentListener = fragmentListener;
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onItemClicked(int position) {
        mFragmentListener.onNewFragment(R.id.constraintLayoutMain,
                PlayMusicFragment.class.getSimpleName(), mAdapter.getData(), position,
                mAdapter.getOffset());
    }

    public void setAdapter(SearchAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setItemClickListener(this);
    }

    public SearchAdapter getAdapter() {
        return mAdapter;
    }

    public void clearData() {
        mAdapter.clearData();
    }

    public void searchTracks(String name) {
        mName = name;
        Disposable disposable =
                mTrackRepository.searchTracks(mName, Constant.LIMIT_TEN, mAdapter.getOffset())
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(tracks -> {
                            mAdapter.addData(tracks);
                        });
        mCompositeDisposable.add(disposable);
    }

    public RecyclerView.OnScrollListener getEndlessScrollListener(
            RecyclerView.LayoutManager layoutManager) {
        mEndlessScrollListener =
                new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
                    @Override
                    public void onLoadMore() {
                        searchTracks(mName);
                    }
                };
        return mEndlessScrollListener;
    }
}