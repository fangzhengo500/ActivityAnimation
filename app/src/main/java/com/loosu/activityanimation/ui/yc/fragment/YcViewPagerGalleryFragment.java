package com.loosu.activityanimation.ui.yc.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.activityanimation.R;
import com.loosu.activityanimation.ui.yc.YcViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class YcViewPagerGalleryFragment extends Fragment {
    private static final String KEY_IMGS = "key_imgs";
    private static final String KEY_INDEX = "key_index";

    private RecyclerView mViewList;
    private LinearLayoutManager mLayoutManager;

    public static YcViewPagerGalleryFragment createFragment(ArrayList<Integer> datas, int index) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_IMGS, datas);
        bundle.putInt(KEY_INDEX, index);

        YcViewPagerGalleryFragment fragment = new YcViewPagerGalleryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yc_viewpager_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewList = view.findViewById(R.id.view_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mViewList.setLayoutManager(mLayoutManager);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mViewList);

        Bundle arguments = getArguments();
        YcViewPagerAdapter adapter = new YcViewPagerAdapter((List<Integer>) arguments.getSerializable(KEY_IMGS));
        mViewList.setAdapter(adapter);

        mViewList.scrollToPosition(arguments.getInt(KEY_INDEX));
    }
}
