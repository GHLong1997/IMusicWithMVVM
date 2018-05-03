package com.example.along.scmusic.data.source.remote.config.service;

import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.Genres;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackApi {
    @GET("tracks?" + Constant.CLIENT_ID)
    Single<List<Track>> getTrendingTracks(@Query("kind") String kind, @Query("limit") int limit,
            @Query("order") String order);

    @GET("tracks?" + Constant.CLIENT_ID)
    Single<List<Track>> getTrackListByGenres(@Query("limit") int limit,
            @Query("genres") @Genres String genres, @Query("offset") int offset);
}
