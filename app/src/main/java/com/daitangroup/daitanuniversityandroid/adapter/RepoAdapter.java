package com.daitangroup.daitanuniversityandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.daitangroup.daitanuniversityandroid.model.Repo;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private final List<Repo> mItems;

    public RepoAdapter(List<Repo> items) {
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO: Inflate item view
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: Bind item view
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // public final TextView mNameTextView;
        // TODO: Declare all item's views

        public ViewHolder(View view) {
            super(view);
            // TODO: Lookup all item's views
        }
    }
}