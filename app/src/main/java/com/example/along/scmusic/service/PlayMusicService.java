package com.example.along.scmusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicService extends Service implements MediaPlayer.OnPreparedListener {

    private static final String EXTRAS_TRACKS = "EXTRAS_TRACKS";
    private static final String QUESTION_MARK = "?";

    private MediaPlayer mMediaPlayer;
    private IBinder mIBinder = new PlayMusicService.LocalBinder();
    private List<Track> mTracks;
    private int mTrackPosition;
    private boolean mIsMediaReady;

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();

    public static Intent getTracksIntent(Context context, List<Track> mTracks, int position) {
        Intent intent = new Intent(context, PlayMusicService.class);
        intent.putParcelableArrayListExtra(EXTRAS_TRACKS,
                (ArrayList<? extends Parcelable>) mTracks);
        intent.putExtra(Constant.POSITION, position);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTracks = intent.getParcelableArrayListExtra(EXTRAS_TRACKS);
        mTrackPosition = intent.getIntExtra(Constant.POSITION, 0);
        if (mTracks != null) {
            initMediaPlayer();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMedia();
    }

    public class LocalBinder extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }

    public void initMediaPlayer() {
        mIsMediaReady = false;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.reset();

        try {
            mTrackObservableField.set(mTracks.get(mTrackPosition));
            mMediaPlayer.setDataSource(mTracks.get(mTrackPosition).getStreamUrl()
                    + QUESTION_MARK
                    + Constant.CLIENT_ID);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    public void updateTracks(@NonNull List<Track> tracks) {
        mTracks.addAll(tracks);
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public int getSongDuration() {
        return mIsMediaReady ? mMediaPlayer.getDuration() : 0;
    }

    public int getCurrentPosition() {
        return mIsMediaReady ? mMediaPlayer.getCurrentPosition() : 0;
    }

    public void fastForward(int position) {
        mMediaPlayer.seekTo(position);
    }

    public void playMedia() {
        if (!mMediaPlayer.isPlaying()) {
            Thread thread = new Thread(() -> {
                mMediaPlayer.start();
                mIsMediaReady = true;
            });
            thread.start();
        }
    }

    public void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public int getTrackPosition() {
        return mTrackPosition;
    }

    public void stopMedia() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void playTrack(int trackPosition) {
        mTrackPosition = trackPosition;
        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public void preTrack() {
        if (mTrackPosition == 0) {
            mTrackPosition = mTracks.size() - 1;
        } else {
            mTrackPosition--;
        }
        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public void nextTrack() {
        if (mTrackPosition == mTracks.size() - 1) {
            mTrackPosition = 0;
        } else {
            mTrackPosition++;
        }

        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
    }
}
