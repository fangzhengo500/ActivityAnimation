package com.loosu.activityanimation.ui.yc;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loosu.activityanimation.R;
import com.loosu.activityanimation.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.activityanimation.adapter.base.recyclerview.RecyclerHolder;

import java.util.List;

public class YcGridAdapter extends ARecyclerAdapter<Integer> {
    public YcGridAdapter(@Nullable List<Integer> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_yc_grid_gallery;
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<Integer> datas) {
        Glide.with(holder.itemView)
                .load(getItem(position))
                .into((ImageView) holder.getView(R.id.iv_image));
    }

    @Override
    public Integer getItem(int position) {
        return mDatas.get(position);
    }
}
