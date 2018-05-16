package com.example.along.scmusic.screen.seemore;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.ActivitySeeMoreMusicBinding;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import java.util.List;

public class SeeMoreMusicActivity extends AppCompatActivity implements OnOpenFragmentListener {

    private SeeMoreMusicViewModel mViewModel;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySeeMoreMusicBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_see_more_music);
        String genre = getIntent().getStringExtra(Constant.GENRE);
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());

        mViewModel = new SeeMoreMusicViewModel(this.getApplicationContext(), trackRepository,
                SchedulerProvider.getInstance(), new Navigator(this), new SeeMoreMusicAdapter(this),
                genre, this);
        binding.setViewModel(mViewModel);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onNewFragment(int containerViewId, String tag, @NonNull List<Track> tracks,
            int position, int offset) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                R.anim.slide_out_down, R.anim.slide_out_up);
        PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance(position, offset);
        playMusicFragment.setData(tracks);
        transaction.replace(containerViewId, playMusicFragment, tag)
                .addToBackStack(null)
                .commit();
    }
}
