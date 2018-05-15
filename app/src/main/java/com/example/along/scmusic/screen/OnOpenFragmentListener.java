package com.example.along.scmusic.screen;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import com.example.along.scmusic.data.model.Track;
import java.util.List;

public interface OnOpenFragmentListener {

    void onNewFragment(@IdRes int containerViewId, String tag, @NonNull List<Track> tracks,
            int position, int offset);
}
