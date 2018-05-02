package com.example.along.scmusic.data.source.local;

import com.example.along.scmusic.data.TrackDataSource;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sTrackLocalDataSource;

    public static synchronized TrackLocalDataSource getInstance() {
        if (sTrackLocalDataSource == null) {
            sTrackLocalDataSource = new TrackLocalDataSource();
        }
        return sTrackLocalDataSource;
    }
}
