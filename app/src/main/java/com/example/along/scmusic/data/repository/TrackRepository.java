package com.example.along.scmusic.data.repository;

import android.content.Context;
import android.database.Cursor;
import com.example.along.scmusic.data.TrackDataSource;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by long on 02/05/2018
 */

public class TrackRepository {

    private static TrackRepository sTrackRepository;
    private TrackDataSource.RemoteDataSource mTrackRemoteDataSource;
    private TrackDataSource.LocalDataSource mTrackLocalDataSource;

    private TrackRepository(TrackDataSource.RemoteDataSource trackRemoteDataSource,
            TrackDataSource.LocalDataSource trackLocalDataSource) {
        mTrackRemoteDataSource = trackRemoteDataSource;
        mTrackLocalDataSource = trackLocalDataSource;
    }

    public static synchronized TrackRepository getInstance(
            TrackDataSource.RemoteDataSource trackRemoteDataSource,
            TrackLocalDataSource.LocalDataSource trackLocalDataSource) {
        if (sTrackRepository == null) {
            sTrackRepository = new TrackRepository(trackRemoteDataSource, trackLocalDataSource);
        }
        return sTrackRepository;
    }

    public Single<List<Track>> getTrendingTracks(String kind, int limit, String order) {
        return mTrackRemoteDataSource.getTrendingTracks(kind, limit, order);
    }

    public Single<List<Track>> getTracksByGenre(int limit, String genre, int offset) {
        return mTrackRemoteDataSource.getTracksByGenre(limit, genre, offset);
    }

    public Single<List<Track>> searchTracks(String query, int limit, int offset) {
        return mTrackRemoteDataSource.searchTracks(query, limit, offset);
    }

    public Flowable<Cursor> getAllTracksFromLocal(Context context) {
        return mTrackLocalDataSource.getAllTracksFromLocal(context);
    }
}
