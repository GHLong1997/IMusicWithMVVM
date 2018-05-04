package com.example.along.scmusic.screen.home;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.along.scmusic.data.model.Track;

public class ItemHomeViewModel {

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();
    private HomeAdapter.OnItemClickListener mItemClickListener;
    private int mPosition;

    public ItemHomeViewModel(@NonNull Track track, int position,
            HomeAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mTrackObservableField.set(track);
        mPosition = position;
    }

    public void onItemClicked(View view) {
        //TODO edit later
    }
}
