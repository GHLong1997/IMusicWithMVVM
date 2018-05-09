package com.example.along.scmusic.screen.home;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.along.scmusic.data.model.Track;
import java.util.List;

public class ItemHomeViewModel {

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();
    private HomeAdapter.OnItemClickListener mItemClickListener;
    private int mPosition;
    private List<Track> mTracks;

    public ItemHomeViewModel(@NonNull Track track, @NonNull List<Track> tracks, int position,
            HomeAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mTrackObservableField.set(track);
        mPosition = position;
        mTracks = tracks;
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || mTrackObservableField.get() == null) {
            return;
        }
        mItemClickListener.onItemClicked(mTracks, mPosition);
    }
}
