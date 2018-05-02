package com.example.along.scmusic.data.repository;

import com.example.along.scmusic.data.TrackDataSource;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;

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
}
