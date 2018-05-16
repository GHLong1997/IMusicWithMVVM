package com.example.along.scmusic.utils;

import com.example.along.scmusic.BuildConfig;

public final class Constant {

    public static final String BASE_URL = "http://api.soundcloud.com/";
    public static final String CLIENT_ID = "client_id=" + BuildConfig.API_KEY;
    public static final String GENRE = "genre";
    public static final String POSITION = "position";
    public static final String OFFSET = "offset";
    public static final String SETUP_MUSIC_PREFERENCES = "setup_music_preferences";
    public static final String SHUFFLE = "shuffle";
    public static final String REPEAT = "repeat";
    public static final String REPEAT_ONE = "repeat_one";
    public static final String NON_REPEAT = "non_repeat";
    public static final String SETUP = "setup";
    public static final int LIMIT_TEN = 10;
    public static final int DEFAULT_POSITION = 0;
    public static final String ACTION_PLAY = "com.example.along.scmusic.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.example.along.scmusic.ACTION_PAUSE";
    public static final String ACTION_PREVIOUS = "com.example.along.scmusic.ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "com.example.along.scmusic.ACTION_NEXT";
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private Constant() {
    }
}
