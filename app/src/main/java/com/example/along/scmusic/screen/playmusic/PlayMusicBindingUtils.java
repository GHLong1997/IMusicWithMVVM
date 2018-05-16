package com.example.along.scmusic.screen.playmusic;

import android.databinding.BindingAdapter;
import android.widget.ImageButton;
import com.example.along.scmusic.R;
import com.example.along.scmusic.utils.Constant;

public final class PlayMusicBindingUtils {

    private PlayMusicBindingUtils() {
    }

    @BindingAdapter("imageDrawable")
    public static void setImageDrawable(ImageButton view, String setUp) {
        switch (setUp) {
            case Constant.REPEAT:
                view.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_repeat_orange));
                break;
            case Constant.REPEAT_ONE:
                view.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_repeat_one));
                break;
            case Constant.SHUFFLE:
                view.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_shuffle));
                break;
            case Constant.NON_REPEAT:
                view.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_repeat));
                break;
        }
    }
}
