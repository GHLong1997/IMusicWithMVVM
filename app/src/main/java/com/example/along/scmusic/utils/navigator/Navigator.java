package com.example.along.scmusic.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.bumptech.glide.util.Preconditions;
import com.example.along.scmusic.R;

public class Navigator {

    @NonNull
    private Activity mActivity;
    @NonNull
    private Fragment mFragment;

    public Navigator(@NonNull Activity activity) {
        mActivity = activity;
    }

    public Navigator(@NonNull Fragment fragment) {
        mFragment = fragment;
        mActivity = Preconditions.checkNotNull(fragment.getActivity());
    }

    private void startActivity(@NonNull Intent intent) {
        mActivity.startActivity(intent);
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args) {
        Intent intent = new Intent(mActivity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtras(args);
        startActivity(intent);
    }

    private void setFragmentTransactionAnimation(FragmentTransaction transaction,
            @NavigateAnim int animation) {
        switch (animation) {
            case NavigateAnim.FADED:
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case NavigateAnim.BOTTOM_UP:
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                        R.anim.slide_out_down, R.anim.slide_out_up);
                break;
            case NavigateAnim.NONE:
                break;
            default:
                break;
        }
    }

    private void setActivityTransactionAnimation(@ActivityTransition int animation) {
        switch (animation) {
            case ActivityTransition.START:
                mActivity.overridePendingTransition(R.anim.translate_left, R.anim.translate_still);
                break;
            case ActivityTransition.FINISH:
                mActivity.overridePendingTransition(R.anim.translate_still, R.anim.translate_right);
                break;
            case ActivityTransition.NONE:
                break;
            default:
                break;
        }
    }

    @IntDef({ NavigateAnim.BOTTOM_UP, NavigateAnim.FADED, NavigateAnim.NONE })
    @interface NavigateAnim {
        int NONE = 0x00;
        int BOTTOM_UP = 0x02;
        int FADED = 0x03;
    }

    @IntDef({ ActivityTransition.NONE, ActivityTransition.START, ActivityTransition.FINISH })
    @interface ActivityTransition {
        int NONE = 0x00;
        int START = 0x01;
        int FINISH = 0x02;
    }
}
