package com.example.along.scmusic.screen.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.data.repository.TrackRepository;
import com.example.along.scmusic.data.source.local.TrackLocalDataSource;
import com.example.along.scmusic.data.source.remote.TrackRemoteDataSource;
import com.example.along.scmusic.databinding.FragmentSearchBinding;
import com.example.along.scmusic.screen.BaseFragment;
import com.example.along.scmusic.screen.OnOpenFragmentListener;
import com.example.along.scmusic.screen.playmusic.PlayMusicFragment;
import com.example.along.scmusic.utils.Constant;
import com.example.along.scmusic.utils.navigator.Navigator;
import com.example.along.scmusic.utils.rx.SchedulerProvider;
import java.util.List;

public class SearchFragment extends BaseFragment
        implements SearchView.OnCloseListener, SearchView.OnQueryTextListener,
        OnOpenFragmentListener{

    private SearchViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentSearchBinding fragmentSearchBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        TrackRepository trackRepository =
                TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                        TrackLocalDataSource.getInstance());
        mViewModel =
                new SearchViewModel(getContext(), trackRepository, new Navigator(getActivity()),
                        SchedulerProvider.getInstance(),this);
        mViewModel.setAdapter(new SearchAdapter(getContext()));
        fragmentSearchBinding.setViewModel(mViewModel);
        ((AppCompatActivity) getActivity()).setSupportActionBar(fragmentSearchBinding.toolBar);
        setHasOptionsMenu(true);
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onClose() {
        mViewModel.clearData();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mViewModel.clearData();
        mViewModel.searchTracks(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onNewFragment(int containerViewId, String tag, @NonNull List<Track> tracks,
            int position, int offset) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                R.anim.slide_out_down, R.anim.slide_out_up);
        PlayMusicFragment playMusicFragment
                = PlayMusicFragment.newInstance(position, offset);
        playMusicFragment.setData(tracks);
        transaction.replace(containerViewId, playMusicFragment, tag)
                .addToBackStack(null)
                .commit();
    }
}
