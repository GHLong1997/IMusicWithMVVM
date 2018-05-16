package com.example.along.scmusic.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.along.scmusic.R;
import com.example.along.scmusic.data.model.Track;
import com.example.along.scmusic.databinding.LayoutItemSearchBinding;
import com.example.along.scmusic.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseRecyclerViewAdapter<SearchAdapter.ViewHolder> {

    private int mOffset;
    private List<Track> mTracks = new ArrayList<>();
    private OnItemClickListener mItemClickListener;

    SearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.layout_item_search, parent, false);
        return new ViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
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
        mOffset += tracks.size();
        notifyDataSetChanged();
    }

    public void clearData() {
        mTracks.clear();
        notifyDataSetChanged();
    }

    public List<Track> getData() {
        return mTracks;
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    public void setItemClickListener(SearchAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mItemClickListener;
        private LayoutItemSearchBinding mBinding;

        ViewHolder(LayoutItemSearchBinding binding, OnItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = itemClickListener;
        }

        void bind(Track track) {
            mBinding.setViewModel(
                    new ItemSearchViewModel(track, getAdapterPosition(), mItemClickListener));
            mBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
