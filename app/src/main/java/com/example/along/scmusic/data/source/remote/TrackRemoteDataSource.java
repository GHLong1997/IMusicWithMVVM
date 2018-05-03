package com.example.along.scmusic.data.source.remote;

import com.example.along.scmusic.data.TrackDataSource;
import com.example.along.scmusic.data.source.remote.config.service.ServiceGenerator;
import com.example.along.scmusic.data.source.remote.config.service.TrackApi;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {

    private static TrackRemoteDataSource sTrackRemoteDataSource;
    private TrackApi mApiTrack;

    public TrackRemoteDataSource(TrackApi apiTrack) {
        mApiTrack = apiTrack;
    }

    public static synchronized TrackRemoteDataSource getInstance() {
        if (sTrackRemoteDataSource == null) {
            sTrackRemoteDataSource =
                    new TrackRemoteDataSource(ServiceGenerator.createService(TrackApi.class));
        }
        return sTrackRemoteDataSource;
    }
}
