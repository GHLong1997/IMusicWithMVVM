package com.example.along.scmusic.screen.playmusic;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.along.scmusic.data.model.Track;

public class ItemPlayMusicViewModel {

    private int mPosition;
    private PlayMusicAdapter.OnItemClickListener mItemClickListener;
    public ObservableField<Track> mTrackObservableField = new ObservableField<>();
    public ObservableField<Boolean> mIsSelected = new ObservableField<>();

    public ItemPlayMusicViewModel(@NonNull Track track, int position,
            PlayMusicAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mTrackObservableField.set(track);
        mPosition = position;
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || mTrackObservableField.get() == null) {
            return;
        }
        mItemClickListener.onItemClicked(mPosition);
    }
}
