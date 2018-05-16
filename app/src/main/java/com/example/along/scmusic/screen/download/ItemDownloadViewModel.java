package com.example.along.scmusic.screen.download;

import android.databinding.ObservableField;
import android.view.View;
import com.example.along.scmusic.data.model.Track;

/**
 * Created by long on 23/05/2018
 */

public class ItemDownloadViewModel {

    private DownloadAdapter.OnItemClickListener mItemClickListener;
    private int mPosition;

    public ObservableField<Track> mTrackObservableField = new ObservableField<>();

    public ItemDownloadViewModel(Track track, int position, DownloadAdapter.OnItemClickListener itemClickListener) {
        mTrackObservableField.set(track);
        mPosition = position;
        mItemClickListener = itemClickListener;
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || mTrackObservableField == null) {
            return;
        }
        mItemClickListener.onItemClicked(mPosition);
    }
}
