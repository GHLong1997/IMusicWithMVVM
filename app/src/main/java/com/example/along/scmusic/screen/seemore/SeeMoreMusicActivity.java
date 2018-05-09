package com.example.along.scmusic.screen.seemore;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.ActivitySeeMoreMusicBinding;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;

public class SeeMoreMusicActivity extends AppCompatActivity {

    private SeeMoreMusicViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySeeMoreMusicBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_see_more_music);
        String genre = getIntent().getStringExtra(Constant.GENRE);
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());

        mViewModel = new SeeMoreMusicViewModel(this.getApplicationContext(), trackRepository,
                SchedulerProvider.getInstance(), new Navigator(this), new SeeMoreMusicAdapter(this), genre);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }
}
