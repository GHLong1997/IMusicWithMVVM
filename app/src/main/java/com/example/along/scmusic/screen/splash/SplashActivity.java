package com.example.along.scmusic.screen.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import com.example.along.scmusic.R;
import com.example.along.scmusic.databinding.SplashActivityBinding;
import com.example.along.scmusic.screen.BaseActivity;
import com.example.along.scmusic.screen.main.MainActivity;

/**
 * Created by Long .
 */

public class SplashActivity extends BaseActivity {
    private static final float FROM_0 = 0.0f;
    private static final float TO_360 = 360.0f;
    private static final float TO_1 = 1.0f;
    private static final float PIVOT_0_5 = 0.5f;
    private static final int DURATION_2000 = 2000;
    private static final int DURATION_1500 = 1500;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.splash_activity);
        binding.setViewModel(this);
        handles();
    }

    public AnimationSet getAnimation() {
        AnimationSet animationSetImage = new AnimationSet(true);
        RotateAnimation rotateAnimationImage =
                new RotateAnimation(FROM_0, TO_360, Animation.RELATIVE_TO_SELF, PIVOT_0_5,
                        Animation.RELATIVE_TO_SELF, PIVOT_0_5);
        rotateAnimationImage.setDuration(DURATION_2000);
        ScaleAnimation scaleAnimationImage = new ScaleAnimation(FROM_0, TO_1, FROM_0, TO_1);
        scaleAnimationImage.setDuration(DURATION_1500);
        animationSetImage.addAnimation(rotateAnimationImage);
        animationSetImage.addAnimation(scaleAnimationImage);
        return animationSetImage;
    }

    public void handles() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, DURATION_2000);
    }
}
