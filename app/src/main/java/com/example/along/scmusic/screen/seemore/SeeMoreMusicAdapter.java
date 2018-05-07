package com.example.along.scmusic.screen.seemore;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.databinding.LayoutItemSeeMoreMusicBinding;
import com.example.along.scmusic.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long .
 */

public class SeeMoreMusicAdapter extends BaseRecyclerViewAdapter<SeeMoreMusicAdapter.ViewHolder> {

    private static final int OFFSET = 10;
    private List<Track> mTrackList = new ArrayList<>();
    private SeeMoreMusicAdapter.OnItemClickListener mItemClickListener;
    private int mOffset = 0;

    SeeMoreMusicAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SeeMoreMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemSeeMoreMusicBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                        R.layout.layout_item_see_more_music, parent, false);
        return new SeeMoreMusicAdapter.ViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeMoreMusicAdapter.ViewHolder holder, int position) {
        holder.bind(mTrackList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mTrackList != null ? mTrackList.size() : 0;
    }

    public void addData(@NonNull List<Track> trackList) {
        mTrackList.addAll(trackList);
        mOffset += OFFSET;
        notifyDataSetChanged();
    }

    public int getOffset() {
        return mOffset;
    }

    public List<Track> getData() {
        return mTrackList;
    }

    public void setItemClickListener(SeeMoreMusicAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemSeeMoreMusicBinding mBinding;
        private OnItemClickListener mItemClickListener;

        ViewHolder(LayoutItemSeeMoreMusicBinding binding,
                final SeeMoreMusicAdapter.OnItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = itemClickListener;
        }

        void bind(Track track, int position) {
            mBinding.setViewModel(new ItemSeeMoreMusicViewModel(track, position, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Track track, int position);
    }
}
