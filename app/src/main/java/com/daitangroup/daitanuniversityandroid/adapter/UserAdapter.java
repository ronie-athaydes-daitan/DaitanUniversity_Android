package com.daitangroup.daitanuniversityandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daitangroup.daitanuniversityandroid.R;
import com.daitangroup.daitanuniversityandroid.glide.RoundedBitmapImageViewTarget;
import com.daitangroup.daitanuniversityandroid.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {

    private static final String TAG = UserAdapter.class.getSimpleName();

    private final List<User> mItems;

    public UserAdapter() {
        this.mItems = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public User getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.list_item_user, parent, false);
        } else {
            view = convertView;
        }

        User item = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(item.getLogin());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

        final Context context = view.getContext();
        final int reqDimen = context.getResources().getDimensionPixelSize(R.dimen.list_item_icon_dimen);

        Glide.with(context)
                .load(item.getAvatarUrl())
                .asBitmap()
                .centerCrop()
                .override(reqDimen, reqDimen)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new RoundedBitmapImageViewTarget(imageView));

        return view;
    }

    public void updateItems(List<User> newItems) {
        mItems.clear();
        mItems.addAll(newItems);
        notifyDataSetChanged();
    }
}