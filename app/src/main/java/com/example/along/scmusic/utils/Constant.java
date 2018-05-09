package com.example.along.scmusic.utils;

import com.example.along.scmusic.BuildConfig;

public final class Constant {

    public static final String BASE_URL = "http://api.soundcloud.com/";
    public static final String CLIENT_ID = "client_id=" + BuildConfig.API_KEY;
    public static final int LIMIT_10 = 10;
    public static final String GENRE = "genre";

    private Constant() {
    }
}
