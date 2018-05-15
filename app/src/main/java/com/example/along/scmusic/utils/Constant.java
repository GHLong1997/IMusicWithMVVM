package com.example.along.scmusic.utils;

import com.example.along.scmusic.BuildConfig;

public final class Constant {

    public static final String BASE_URL = "http://api.soundcloud.com/";
    public static final String CLIENT_ID = "client_id=" + BuildConfig.API_KEY;
    public static final String GENRE = "genre";
    public static final String POSITION = "position";
    public static final String OFFSET = "offset";
    public static final int LIMIT_TEN = 10;
    public static final int DEFAULT_POSITION = 0;

    private Constant() {
    }
}
