package com.example.along.scmusic.screen.download;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Artist;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.screen.BaseViewModel;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Publisher;

/**
 * Created by long on 23/05/2018
 */

public class DownloadViewModel extends BaseViewModel
        implements DownloadAdapter.OnItemClickListener {

    private static final String ARTWORK_URI = "content://media/external/audio/albumart";
    private Context mContext;
    private TrackRepository mTrackRepository;
    private Navigator mNavigator;
    private SchedulerProvider mSchedulerProvider;
    private OnOpenFragmentListener mFragmentListener;
    private DownloadAdapter mAdapter;

    public DownloadViewModel(Context context, TrackRepository trackRepository, Navigator navigator,
            SchedulerProvider schedulerProvider, OnOpenFragmentListener fragmentListener) {
        mContext = context.getApplicationContext();
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

    }

    public void setAdapter(DownloadAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setItemClickListener(this);
    }

    public DownloadAdapter getAdapter() {
        return mAdapter;
    }

    public void getAllTrackFromLocal() {
        mTrackRepository.getAllTracksFromLocal(mContext)
                .map(new Function<Cursor, List<Track>>() {
                    @Override
                    public List<Track> apply(Cursor cursor) throws Exception {
                        return convertData(cursor);
                    }
                })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<Track>>() {
                    @Override
                    public void accept(List<Track> tracks) throws Exception {
                        mAdapter.addData(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(mContext, throwable.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public List<Track> convertData(Cursor cursor) {
        List<Track> tracks = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                Uri sArtworkUri = Uri.parse(ARTWORK_URI);
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, id);
                String artist =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                Track track = new Track();
                track.setId(id);
                track.setKind("");
                track.setUri("");
                track.setUserId(0);
                track.setGenre("");
                track.setTitle(title);
                track.setStreamUrl(path);
                track.setArtworkUrl(albumArtUri.toString());
                track.setDownloadable(false);
                Artist artist1 = new Artist();
                artist1.setAvatarUrl("");
                artist1.setId(0);
                artist1.setUsername(artist);
                track.setUser(artist1);
                tracks.add(track);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return tracks;
    }

    @Override
    public void onItemClicked(int position) {
        mFragmentListener.onNewFragment(R.id.constraintLayoutMain,
                PlayMusicFragment.class.getSimpleName(), mAdapter.getData(), position, 0);
    }
}
