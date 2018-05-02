package com.example.along.scmusic.data;

public interface TrackDataSource {

    interface LocalDataSource extends TrackDataSource {
    }

    interface RemoteDataSource extends TrackDataSource {
    }
}
