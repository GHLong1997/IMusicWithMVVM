package com.example.along.scmusic.screen.download;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.databinding.LayoutItemDownloadBinding;
import com.example.along.scmusic.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends BaseRecyclerViewAdapter<DownloadAdapter.ViewHolder> {

    private List<Track> mTracks = new ArrayList<>();
    private OnItemClickListener mItemClickListener;

    DownloadAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemDownloadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.layout_item_download, parent, false);
        return new ViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {
        holder.bind(mTracks.get(position));
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
        notifyDataSetChanged();
    }


    public List<Track> getData() {
        return mTracks;
    }

    public void setItemClickListener(DownloadAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mItemClickListener;
        private LayoutItemDownloadBinding mBinding;

        ViewHolder(LayoutItemDownloadBinding binding, OnItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = itemClickListener;
        }

        void bind(Track track) {
            mBinding.setViewModel(
                    new ItemDownloadViewModel(track, getAdapterPosition(), mItemClickListener));
            mBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
