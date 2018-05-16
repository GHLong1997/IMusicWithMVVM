package com.example.along.scmusic.data.source.remote;

import com.example.along.scmusic.data.TrackDataSource;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.source.remote.config.service.ServiceGenerator;
import com.example.along.scmusic.data.source.remote.config.service.TrackApi;
import io.reactivex.Single;
import java.util.List;

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

    @Override
    public Single<List<Track>> getTrendingTracks(String kind, int limit, String order) {
        return mApiTrack.getTrendingTracks(kind, limit, order);
    }

    @Override
    public Single<List<Track>> getTracksByGenre(int limit, String genre, int offset) {
        return mApiTrack.getTrackListByGenres(limit, genre, offset);
    }

    @Override
    public Single<List<Track>> searchTracks(String query, int limit, int offset) {
        return mApiTrack.searchTracks(query, limit, offset);
    }
}
