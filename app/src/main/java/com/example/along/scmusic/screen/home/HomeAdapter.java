package com.example.along.scmusic.screen.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.databinding.LayoutItemHomeBinding;
import com.example.along.scmusic.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseRecyclerViewAdapter<HomeAdapter.ViewHolder> {

    private List<Track> mTracks = new ArrayList<>();
    private OnItemClickListener mItemClickListener;

    HomeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemHomeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.layout_item_home, parent, false);
        return new ViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.bind(mTracks.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public void addData(@NonNull List<Track> trackList) {
        mTracks.addAll(trackList);
        notifyDataSetChanged();
    }

    public List<Track> getData() {
        return mTracks;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemHomeBinding mBinding;
        private OnItemClickListener mItemClickListener;

        ViewHolder(LayoutItemHomeBinding binding, final OnItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = itemClickListener;
        }

        void bind(@NonNull Track track, int position) {
            mBinding.setViewModel(new ItemHomeViewModel(track, position, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Track track, int position);
    }
}
