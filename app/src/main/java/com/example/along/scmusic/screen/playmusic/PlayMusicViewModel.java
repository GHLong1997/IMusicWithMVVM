package com.example.along.scmusic.screen.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.EndlessRecyclerViewScrollListener;
import com.example.along.scmusic.service.PlayMusicService;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.Genres;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class PlayMusicViewModel extends BaseViewModel
        implements PlayMusicAdapter.OnItemClickListener {

    private static final int PROGRESS_DEFAULT = 0;
    private static final int TIME_DELAY = 100;

    private Context mContext;
    private PlayMusicAdapter mAdapter;
    private TrackRepository mTrackRepository;
    private SchedulerProvider mSchedulerProvider;
    private Navigator mNavigator;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;
    private int mTrackPosition;
    private String mGenre;
    private Handler mHandler = new Handler();
    private Utilities mUtilities = new Utilities();
    private ServiceConnection mServiceConnection;

    public ObservableField<PlayMusicService> mPlayMusicService = new ObservableField<>();
    public ObservableField<String> mMediaTotalDuration = new ObservableField<>();
    public ObservableField<String> mMediaCurrentPosition = new ObservableField<>();
    public ObservableField<Integer> mProgressCurrentTimeMusic = new ObservableField<>();
    public ObservableField<Boolean> mIsPlaying = new ObservableField<>();

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
        playService();
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onItemClicked(int position) {
        changeColorItem(position);
        mPlayMusicService.get().playTrack(position);
        mProgressCurrentTimeMusic.set(PROGRESS_DEFAULT);
        updateProgressBar();
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
                        .subscribe((List<Track> tracks) -> {
                            mAdapter.addData(tracks);
                            mPlayMusicService.get().updateTracks(tracks);
                        }, (Throwable throwable) -> {
                            Toast.makeText(mContext, throwable.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        });
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
        mAdapter.notifyItemRangeChanged(PROGRESS_DEFAULT, mAdapter.getItemCount());
    }

    private void playService() {
        getService();
        Intent intent =
                PlayMusicService.getTracksIntent(mContext, mAdapter.getData(), mTrackPosition);
        if (mContext != null) {
            mContext.startService(intent);
            mContext.bindService(intent, mServiceConnection, mContext.BIND_AUTO_CREATE);
        }
    }

    private void getService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayMusicService.LocalBinder binder = (PlayMusicService.LocalBinder) iBinder;
                mPlayMusicService.set(binder.getService());
                updateProgressBar();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, TIME_DELAY);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mPlayMusicService.get().getSongDuration();
            long currentPosition = mPlayMusicService.get().getCurrentPosition();

            mMediaCurrentPosition.set(mUtilities.milliSecondsToTimer(currentPosition));
            mMediaTotalDuration.set(mUtilities.milliSecondsToTimer(totalDuration));

            int progress = (mUtilities.getProgressPercentage(currentPosition, totalDuration));
            mProgressCurrentTimeMusic.set(progress);
            mIsPlaying.set(true);
            if (mMediaCurrentPosition.get().equals(mMediaTotalDuration.get())
                    && currentPosition != 0) {
                mPlayMusicService.get().nextTrack();
                mTrackPosition = mPlayMusicService.get().getTrackPosition();
                mAdapter.addPosition(mTrackPosition);
                mAdapter.notifyItemRangeChanged(Constant.DEFAULT_POSITION, mAdapter.getItemCount());
            }
            mHandler.postDelayed(this, TIME_DELAY);
        }
    };

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            mPlayMusicService.get()
                    .fastForward(mUtilities.progressToTimer(seekBar.getProgress(),
                            mPlayMusicService.get().getSongDuration()));
            mMediaCurrentPosition.set(
                    mUtilities.milliSecondsToTimer(mPlayMusicService.get().getCurrentPosition()));
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        updateProgressBar();
    }

    public void onNextTrack(View view) {
        mPlayMusicService.get().nextTrack();
        changeColorItem(mPlayMusicService.get().getTrackPosition());
    }

    public void onPreTrack(View view) {
        mPlayMusicService.get().preTrack();
        changeColorItem(mPlayMusicService.get().getTrackPosition());
    }

    public void onPlayAndPauseTrack(View view) {
        if (mPlayMusicService.get().isPlaying()) {
            mPlayMusicService.get().pauseMedia();
            mIsPlaying.set(false);
            mHandler.removeCallbacks(mUpdateTimeTask);
        } else {
            mPlayMusicService.get().playMedia();
            mIsPlaying.set(true);
            updateProgressBar();
        }
    }
}
