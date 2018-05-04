package com.example.along.scmusic.screen.home;

import android.databinding.ObservableField;

public class SliderViewModel {

    public ObservableField<String> mImageUrlObservableField = new ObservableField<>();

    public void setImageUrl(String url) {
        mImageUrlObservableField.set(url);
    }
}
