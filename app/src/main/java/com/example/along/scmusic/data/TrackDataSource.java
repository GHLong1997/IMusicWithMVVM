package com.example.along.scmusic.data;

import com.example.along.scmusic.data.model.Track;
import io.reactivex.Single;
import java.util.List;

public interface TrackDataSource {

    interface LocalDataSource extends TrackDataSource {
    }

    interface RemoteDataSource extends TrackDataSource {

        Single<List<Track>> getTrendingTracks(String kind, int limit, String order);

        Single<List<Track>> getTracksByGenre(int limit, String genre, int offset);
    }
}
