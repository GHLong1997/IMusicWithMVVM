package com.example.along.scmusic.screen.search;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.along.scmusic.data.model.Track;

public class ItemSearchViewModel {

    private int mPosition;
    private SearchAdapter.OnItemClickListener mItemClickListener;

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();

    public ItemSearchViewModel(@NonNull Track track, int position,
            SearchAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mPosition = position;
        mTrackObservableField.set(track);
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || mTrackObservableField.get() == null) {
            return;
        }
        mItemClickListener.onItemClicked(mPosition);
    }
}
