package com.loosu.activityanimation.ui.yc;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loosu.activityanimation.R;
import com.loosu.activityanimation.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.activityanimation.adapter.base.recyclerview.RecyclerHolder;

import java.util.List;

public class YcViewPagerAdapter extends ARecyclerAdapter<Integer> {
    public YcViewPagerAdapter(@Nullable List<Integer> datas) {
        super(datas);
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<Integer> datas) {
        ViewCompat.setTransitionName(holder.itemView, String.valueOf(position));
        Glide.with(holder.itemView)
                .load(getItem(position))
                .into((ImageView) holder.getView(R.id.iv_image));
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_yc_viewpager_gallery;
    }

    @Override
    public Integer getItem(int position) {
        return mDatas.get(position);
    }
}
