package com.example.along.scmusic.screen.playmusic;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.databinding.ItemPlayMusicFragmentBinding;
import com.example.along.scmusic.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicAdapter extends BaseRecyclerViewAdapter<PlayMusicAdapter.ViewHolder> {

    private int mCurrentPosition;
    private int mOffset;
    private List<Track> mTracks = new ArrayList<>();
    private OnItemClickListener mItemClickListener;

    PlayMusicAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public PlayMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlayMusicFragmentBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                        R.layout.item_play_music_fragment, parent, false);
        return new ViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayMusicAdapter.ViewHolder holder, int position) {
        holder.bind(mTracks.get(position));
        if (position == mCurrentPosition) {
            holder.changeColor();
        } else {
            holder.resetColor();
        }
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    void addData(@Nullable List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks.addAll(tracks);
        mOffset += tracks.size();
        notifyDataSetChanged();
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    void addPosition(int position) {
        mCurrentPosition = position;
    }

    public void setItemClickListener(PlayMusicAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mItemClickListener;
        private ItemPlayMusicFragmentBinding mBinding;
        private ItemPlayMusicViewModel mViewModel;

        ViewHolder(ItemPlayMusicFragmentBinding binding, OnItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = itemClickListener;
        }

        void bind(Track track) {
            mViewModel =
                    new ItemPlayMusicViewModel(track, getAdapterPosition(), mItemClickListener);
            mBinding.setViewModel(mViewModel);
            mBinding.executePendingBindings();
        }

        void changeColor() {
            mViewModel.mIsSelected.set(true);
        }

        void resetColor() {
            mViewModel.mIsSelected.set(false);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
