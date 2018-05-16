package com.example.along.scmusic.screen.download;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.FragmentDownloadBinding;
import com.example.along.scmusic.screen.BaseFragment;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.common.PermissionUtils;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import java.util.List;

public class DownloadFragment extends BaseFragment implements OnOpenFragmentListener {

    private DownloadViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentDownloadBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false);
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());
        mViewModel =
                new DownloadViewModel(getContext(), trackRepository, new Navigator(getActivity()),
                        SchedulerProvider.getInstance(), this);
        mViewModel.setAdapter(new DownloadAdapter(getContext()));
        binding.setViewModel(mViewModel);
        if (getUserVisibleHint() && PermissionUtils.requestPermission(getActivity())) {
            mViewModel.getAllTrackFromLocal();
        }
        return binding.getRoot();
    }

    @Override
    public void onNewFragment(int containerViewId, String tag, @NonNull List<Track> tracks,
            int position, int offset) {
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                R.anim.slide_out_down, R.anim.slide_out_up);
        PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance(position, offset);
        playMusicFragment.setData(tracks);
        transaction.replace(containerViewId, playMusicFragment, tag).addToBackStack(null).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mViewModel.getAllTrackFromLocal();
                    Toast.makeText(getContext(), "Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No granted", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed() && PermissionUtils.requestPermission(getActivity())) {
            mViewModel.getAllTrackFromLocal();
        }
    }
}
