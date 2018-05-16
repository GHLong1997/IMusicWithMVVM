package com.example.along.scmusic.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.example.along.scmusic.data.TrackDataSource;
import com.example.along.scmusic.data.model.Track;
import io.reactivex.Flowable;
import java.util.List;
import java.util.concurrent.Callable;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sTrackLocalDataSource;

    public static synchronized TrackLocalDataSource getInstance() {
        if (sTrackLocalDataSource == null) {
            sTrackLocalDataSource = new TrackLocalDataSource();
        }
        return sTrackLocalDataSource;
    }

    @Override
    public Flowable<Cursor> getAllTracksFromLocal(Context context) {
        return Flowable.fromCallable(new Callable<Cursor>() {
            @Override
            public Cursor call() throws Exception {
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                Cursor cursor = context.getContentResolver()
                        .query(uri, null, MediaStore.Audio.Media.IS_MUSIC + "!= 0", null, null);
                return cursor;
            }
        });
    }
}
