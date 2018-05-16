package com.example.along.scmusic.data;

import android.content.Context;
import android.database.Cursor;
import com.example.along.scmusic.data.model.Track;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

public interface TrackDataSource {

    interface LocalDataSource extends TrackDataSource {
        Flowable<Cursor> getAllTracksFromLocal(Context context);
    }

    interface RemoteDataSource extends TrackDataSource {

        Single<List<Track>> getTrendingTracks(String kind, int limit, String order);

        Single<List<Track>> getTracksByGenre(int limit, String genre, int offset);

        Single<List<Track>> searchTracks(String query, int limit, int offset);
    }
}
