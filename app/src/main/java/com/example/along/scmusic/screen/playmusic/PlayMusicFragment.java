package com.example.along.scmusic.screen.playmusic;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.FragmentPlayMusicBinding;
import com.example.along.scmusic.screen.BaseFragment;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends BaseFragment {

    private PlayMusicViewModel mViewModel;
    private List<Track> mTracks = new ArrayList<>();
    private int mTrackPosition;
    private int mOffset;

    public static PlayMusicFragment newInstance(int position, int offset) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.POSITION, position);
        args.putInt(Constant.OFFSET, offset);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentPlayMusicBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_play_music, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTrackPosition = bundle.getInt(Constant.POSITION, 0);
            mOffset = bundle.getInt(Constant.OFFSET, 0);
        }

        initViewModel();

        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    private void initViewModel() {
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());
        mViewModel =
                new PlayMusicViewModel(getContext(), trackRepository, new Navigator(getActivity()),
                        SchedulerProvider.getInstance(), getAdapter());
        mViewModel.setGenre(mTracks.get(Constant.DEFAULT_POSITION).getGenre());
        mViewModel.setTrackPosition(mTrackPosition);
    }

    private PlayMusicAdapter getAdapter() {
        PlayMusicAdapter adapter = new PlayMusicAdapter(getContext());
        adapter.addData(mTracks);
        adapter.setOffset(mOffset);
        adapter.addPosition(mTrackPosition);
        return adapter;
    }

    public void setData(@NonNull List<Track> tracks) {
        mTracks = tracks;
    }
}
