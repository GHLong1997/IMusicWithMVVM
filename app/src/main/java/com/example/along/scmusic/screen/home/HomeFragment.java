package com.example.along.scmusic.screen.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.FragmentHomeBinding;
import com.example.along.scmusic.screen.BaseFragment;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import java.util.List;

public class HomeFragment extends BaseFragment implements OnOpenFragmentListener {

    private HomeViewModel mViewModel;
    private boolean mIsFragmentLoaded;
    private FragmentManager mFragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());
        FragmentHomeBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mViewModel = new HomeViewModel(getActivity().getApplicationContext(), trackRepository,
                new Navigator(this), SchedulerProvider.getInstance(), this);
        mViewModel.setAdapter(new HomeAdapter(getActivity()), new HomeAdapter(getActivity()),
                new HomeAdapter(getActivity()), new HomeAdapter(getActivity()),
                new SliderAdapter(getActivity().getSupportFragmentManager()));
        binding.setViewModel(mViewModel);
        mFragmentManager = getActivity().getSupportFragmentManager();

        if (getUserVisibleHint() && !mIsFragmentLoaded) {
            mViewModel.loadData();
            mIsFragmentLoaded = true;
        }
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mIsFragmentLoaded && isResumed()) {
            mViewModel.loadData();
            mIsFragmentLoaded = true;
        }
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
