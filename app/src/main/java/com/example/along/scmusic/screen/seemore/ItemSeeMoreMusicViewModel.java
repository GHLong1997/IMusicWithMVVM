package com.example.along.scmusic.screen.seemore;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.along.scmusic.data.model.Track;

public class ItemSeeMoreMusicViewModel {

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();
    private SeeMoreMusicAdapter.OnItemClickListener mItemClickListener;
    private int mPosition;

    public ItemSeeMoreMusicViewModel(@NonNull Track track, int position,
            SeeMoreMusicAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mTrackObservableField.set(track);
        mPosition = position;
    }

    public void onItemClicked(View view) {
        //TODO edit later
    }
}
