package com.loosu.activityanimation.ui.yc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.activityanimation.R;
import com.loosu.activityanimation.adapter.base.recyclerview.IRecyclerItemClickListener;
import com.loosu.activityanimation.ui.yc.YcGridAdapter;
import com.loosu.activityanimation.ui.yc.activity.YcViewPagerGalleryActivity;
import com.loosu.activityanimation.utils.DataHelper;

public class YcGridGalleryFragment extends Fragment implements IRecyclerItemClickListener {
    private static final int REQ_CODE_SHOW_DETAIL = 666;

    private RecyclerView mViewList;
    private GridLayoutManager mLayoutManager;
    private YcGridAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yc_grid_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewList = view.findViewById(R.id.view_list);

        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mViewList.setLayoutManager(mLayoutManager);

        mAdapter = new YcGridAdapter(DataHelper.getPics());
        mViewList.setAdapter(mAdapter);

        mAdapter.setItemClickListener(this);
    }

    @Override
    public void onItemClick(RecyclerView parent, int position, RecyclerView.ViewHolder holder, View view) {
        Intent intent = YcViewPagerGalleryActivity.getStartIntent(getActivity(), mAdapter.getDatas(), position);
        startActivityForResult(intent, REQ_CODE_SHOW_DETAIL);
    }
}
