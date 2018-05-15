package com.example.along.scmusic.screen.playmusic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.screen.BaseFragment;
import com.example.along.scmusic.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends BaseFragment {

    private List<Track> mTracks = new ArrayList<>();

    public static PlayMusicFragment newInstance(int position, int offset) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.POSITION, position);
        args.putInt(Constant.OFFSET, offset);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_music, container, false);
    }

    public void setData(@NonNull List<Track> tracks) {
        mTracks = tracks;
    }
}
