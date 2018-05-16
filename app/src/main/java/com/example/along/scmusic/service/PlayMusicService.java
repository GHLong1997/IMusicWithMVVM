package com.example.along.scmusic.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.NotificationTarget;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.screen.main.MainActivity;
import com.example.along.scmusic.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayMusicService extends Service implements MediaPlayer.OnPreparedListener {

    private static final int NOTIFICATION_ID = 101;
    private static final int PRIORITY_RECEIVE = 2;
    private static final String TAG = "Exception";
    private static final String EXTRAS_TRACKS = "EXTRAS_TRACKS";
    private static final String QUESTION_MARK = "?";
    private static final String NULL = "null";

    private MediaPlayer mMediaPlayer;
    private IBinder mIBinder = new PlayMusicService.LocalBinder();
    private SharedPreferences mSharedPreferences;
    private List<Track> mTracks;
    private int mTrackPosition;
    private String mSetup;
    private boolean mIsMediaReady;
    private boolean isShuffle;
    private boolean isRepeat;
    private boolean isRepeatOne;
    private boolean isNonRepeat;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();

    public static Intent getTracksIntent(Context context, List<Track> mTracks, int position) {
        Intent intent = new Intent(context, PlayMusicService.class);
        intent.putParcelableArrayListExtra(EXTRAS_TRACKS,
                (ArrayList<? extends Parcelable>) mTracks);
        intent.putExtra(Constant.POSITION, position);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getDataFromSharedPreferences();
        registerBroadcastReceive();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mTracks = intent.getParcelableArrayListExtra(EXTRAS_TRACKS);
        mTrackPosition = intent.getIntExtra(Constant.POSITION, 0);
        if (mTracks != null) {
            initMediaPlayer();
            buildNotification();
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
            Log.e(TAG, e.getMessage());
            stopSelf();
        }
    }

    public String getArt() {
        return mTracks.get(mTrackPosition) != null ? mTracks.get(mTrackPosition).getArtworkUrl()
                : "";
    }

    public void updateTracks(@NonNull List<Track> tracks) {
        mTracks.addAll(tracks);
    }

    public void refreshData(@NonNull List<Track> tracks, int position) {
        mTracks = tracks;
        mTrackPosition = position;
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
        updateNotification(Constant.ACTION_PLAY);
    }

    public void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
        updateNotification(Constant.ACTION_PAUSE);
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

    public void setupMusic() {
        if (isRepeat) {
            isRepeatOne = true;
            isShuffle = false;
            isNonRepeat = false;
            isRepeat = false;
            mSetup = Constant.REPEAT_ONE;
        } else if (isRepeatOne) {
            isShuffle = true;
            isNonRepeat = false;
            isRepeat = false;
            isRepeatOne = false;
            mSetup = Constant.SHUFFLE;
        } else if (isShuffle) {
            isNonRepeat = true;
            isRepeatOne = false;
            isRepeat = false;
            isShuffle = false;
            mSetup = Constant.NON_REPEAT;
        } else if (isNonRepeat) {
            isRepeat = true;
            isNonRepeat = false;
            isShuffle = false;
            isRepeatOne = false;
            mSetup = Constant.REPEAT;
        }
        setupMusicPreferences();
    }

    public String getSetup() {
        return mSetup;
    }

    private void getDataFromSharedPreferences() {
        mSharedPreferences = getSharedPreferences(Constant.SETUP_MUSIC_PREFERENCES, MODE_PRIVATE);
        isRepeat = mSharedPreferences.getBoolean(Constant.REPEAT, false);
        isRepeatOne = mSharedPreferences.getBoolean(Constant.REPEAT_ONE, false);
        isShuffle = mSharedPreferences.getBoolean(Constant.SHUFFLE, false);
        isNonRepeat = mSharedPreferences.getBoolean(Constant.NON_REPEAT, true);
    }

    private void setupMusicPreferences() {
        mSharedPreferences = getSharedPreferences(Constant.SETUP_MUSIC_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(Constant.SHUFFLE, isShuffle);
        editor.putBoolean(Constant.REPEAT, isRepeat);
        editor.putBoolean(Constant.REPEAT_ONE, isRepeatOne);
        editor.putBoolean(Constant.NON_REPEAT, isNonRepeat);
        editor.putString(Constant.SETUP, mSetup);
        editor.apply();
    }

    public void playTrack(int trackPosition) {
        mTrackPosition = trackPosition;
        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
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
        updateNotification(Constant.ACTION_PREVIOUS);
    }

    public void nextTrack(boolean controlByHand) {
        if (!controlByHand) { // no action by user
            if (isShuffle) {
                Random rand = new Random();
                int randomPosition = rand.nextInt(mTracks.size());
                while (randomPosition == mTrackPosition) {
                    randomPosition = rand.nextInt(mTracks.size());
                }
                mTrackPosition = randomPosition;
            } else if (isRepeat) {
                if (mTrackPosition == mTracks.size() - 1) {
                    mTrackPosition = 0;
                } else {
                    mTrackPosition++;
                }
            }
            if (isNonRepeat) {
                if (mTrackPosition == mTracks.size() - 1) {
                    return;
                } else {
                    mTrackPosition++;
                }
            }
        } else { // action by user
            if (mTrackPosition == mTracks.size() - 1) {
                mTrackPosition = 0;
            } else {
                mTrackPosition++;
            }
        }
        stopMedia();
        mMediaPlayer.reset();
        initMediaPlayer();
        updateNotification(Constant.ACTION_PREVIOUS);
    }

    public void setListener(RemoteViews views) {
        Intent previous = new Intent(Constant.ACTION_PREVIOUS);
        Intent pause = new Intent(Constant.ACTION_PAUSE);
        Intent next = new Intent(Constant.ACTION_NEXT);
        Intent play = new Intent(Constant.ACTION_PLAY);

        PendingIntent pPrevious = PendingIntent.getBroadcast(getApplicationContext(), 0, previous,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_previous, pPrevious);

        PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_pause, pPause);

        PendingIntent pNext = PendingIntent.getBroadcast(getApplicationContext(), 0, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_next, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(getApplicationContext(), 0, play,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_play, pPlay);
    }

    @SuppressLint("NewApi")
    private void buildNotification() {
        String trackName = mTracks.get(mTrackPosition).getTitle();
        String userName = mTracks.get(mTrackPosition).getUser().getUsername();
        String imageString = mTracks.get(mTrackPosition).getArtworkUrl();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mNotification = new Notification.Builder(getApplicationContext()).setSmallIcon(
                R.drawable.ic_skip_next)
                .setContentTitle(trackName)
                .setContentText(userName)
                .setContentIntent(pendingIntent)
                .build();
        mNotification.contentView = remoteViews;
        mNotification.contentView.setTextViewText(R.id.text_track_name, trackName);
        mNotification.contentView.setTextViewText(R.id.text_user_name, userName);
        NotificationTarget notificationTarget =
                new NotificationTarget(this, R.id.image_track, mNotification.contentView,
                        mNotification, NOTIFICATION_ID);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(imageString)
                .apply(new RequestOptions().placeholder(R.drawable.ic_logo))
                .into(notificationTarget);
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        setListener(mNotification.contentView);
        startForeground(NOTIFICATION_ID, mNotification);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case Constant.ACTION_PREVIOUS:
                        preTrack();
                        break;
                    case Constant.ACTION_NEXT:
                        nextTrack(true);
                        break;
                    case Constant.ACTION_PLAY:
                        playMedia();
                        break;
                    case Constant.ACTION_PAUSE:
                        pauseMedia();
                        break;
                }
            }
        }
    };

    private void registerBroadcastReceive() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_PLAY);
        intentFilter.addAction(Constant.ACTION_NEXT);
        intentFilter.addAction(Constant.ACTION_PAUSE);
        intentFilter.addAction(Constant.ACTION_PREVIOUS);
        intentFilter.setPriority(PRIORITY_RECEIVE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void updateNotification(String action) {
        if (action.equals(Constant.ACTION_NEXT) || action.equals(Constant.ACTION_PREVIOUS)) {
            mNotification.contentView.setTextViewText(R.id.text_track_name,
                    mTracks.get(mTrackPosition).getTitle());
            mNotification.contentView.setTextViewText(R.id.text_user_name,
                    mTracks.get(mTrackPosition).getUser().getUsername());
            NotificationTarget notificationTarget =
                    new NotificationTarget(this, R.id.image_track, mNotification.contentView,
                            mNotification, NOTIFICATION_ID);
            String art = getArt();
            if (art.equals(NULL)) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(R.drawable.ic_logo)
                        .into(notificationTarget);
            } else {
                Glide.with(getApplicationContext()).asBitmap().load(art).into(notificationTarget);
            }
        } else if (action.equals(Constant.ACTION_PLAY)) {
            mNotification.contentView.setTextViewText(R.id.text_track_name,
                    mTracks.get(mTrackPosition).getTitle());
            mNotification.contentView.setTextViewText(R.id.text_user_name,
                    mTracks.get(mTrackPosition).getUser().getUsername());
            NotificationTarget notificationTarget =
                    new NotificationTarget(this, R.id.image_track, mNotification.contentView,
                            mNotification, NOTIFICATION_ID);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(mTracks.get(mTrackPosition).getArtworkUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_logo))
                    .into(notificationTarget);
            mNotification.contentView.setViewVisibility(R.id.image_play, View.GONE);
            mNotification.contentView.setViewVisibility(R.id.image_pause, View.VISIBLE);
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        } else if (action.equals(Constant.ACTION_PAUSE)) {
            mNotification.contentView.setViewVisibility(R.id.image_pause, View.GONE);
            mNotification.contentView.setViewVisibility(R.id.image_play, View.VISIBLE);
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }
}
