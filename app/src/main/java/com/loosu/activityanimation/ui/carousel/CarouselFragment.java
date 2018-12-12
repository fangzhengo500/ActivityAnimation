package com.loosu.activityanimation.ui.carousel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leochuan.CarouselLayoutManager;
import com.leochuan.CenterSnapHelper;
import com.leochuan.ScrollHelper;
import com.loosu.activityanimation.R;
import com.loosu.activityanimation.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.activityanimation.adapter.base.recyclerview.IRecyclerItemClickListener;
import com.loosu.activityanimation.adapter.base.recyclerview.RecyclerHolder;
import com.loosu.activityanimation.utils.DataHelper;
import com.loosu.activityanimation.utils.KLog;

import java.util.List;
import java.util.Map;

public class CarouselFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CarouselFragment";

    private MyAdapter mAdapter;
    private CarouselLayoutManager mLayoutManager;

    private RecyclerView mViewList;
    private View mBtnPre;
    private View mBtnNext;

    private boolean needSetupShareElementCallback = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view, savedInstanceState);
        initView(view, savedInstanceState);
        initListener(view, savedInstanceState);
    }


    protected void init(@Nullable Bundle savedInstanceState) {
        mAdapter = new MyAdapter(DataHelper.getColors());
    }

    private void findView(@NonNull View view, Bundle savedInstanceState) {
        mViewList = view.findViewById(R.id.view_list);
        mBtnPre = view.findViewById(R.id.btn_per);
        mBtnNext = view.findViewById(R.id.btn_next);
    }

    private void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mLayoutManager = new CarouselLayoutManager.Builder(getContext(), 600)
                .build();
        mViewList.setLayoutManager(mLayoutManager);
        mViewList.setAdapter(mAdapter);

        CenterSnapHelper snapHelper = new CenterSnapHelper();
        snapHelper.attachToRecyclerView(mViewList);
    }

    private void initListener(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBtnPre.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mViewList.addOnScrollListener(mScrollListener);
        mAdapter.setItemClickListener(itemClickListener);

        if (needSetupShareElementCallback) {
            setShareElementCallback();
        }
    }

    public void setShareElementCallback() {
        FragmentActivity activity = getActivity();
        KLog.i(TAG, "setShareElementCallback activity = " + activity);

        if (activity != null) {
            activity.setExitSharedElementCallback(mSharedElementCallback);
            needSetupShareElementCallback = false;
        } else {
            needSetupShareElementCallback = true;
        }
    }

    private IRecyclerItemClickListener itemClickListener = new IRecyclerItemClickListener() {
        @Override
        public void onItemClick(RecyclerView parent, int position, RecyclerView.ViewHolder holder, View view) {
            int centerPosition = mLayoutManager.getCurrentPosition();
            if (centerPosition == position) {
                Context context = getContext();

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, String.valueOf(position));
                Intent intent = CarouselDetailActivity.getStartIntent(context, mAdapter.getDatas(), position);
                ActivityCompat.startActivity(context, intent, compat.toBundle());
            } else {
                ScrollHelper.smoothScrollToTargetView(parent, holder.itemView);
            }
        }
    };

    public void onActivityReenter(Activity activity, int resultCode, Intent data) {
        int index = data.getIntExtra("key_index", 0);
        View view = mLayoutManager.findViewByPosition(index);
        if (view == null) {
            ActivityCompat.postponeEnterTransition(getActivity());
        }
        mLayoutManager.scrollToPosition(index);
        KLog.w(TAG, "onActivityReenter: index = " + index + ", view = " + view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_per:
                mViewList.scrollToPosition(mLayoutManager.getCurrentPosition() - 1);
                break;
            case R.id.btn_next:
                mViewList.scrollToPosition(mLayoutManager.getCurrentPosition() + 1);
                break;
        }
    }


    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            KLog.w(TAG, "onScrollStateChanged: recyclerView = " + recyclerView + ", newState = " + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            KLog.w(TAG, "onScrolled: dx = " + dx + ", dy = " + dy);
            ActivityCompat.startPostponedEnterTransition(getActivity());
        }
    };
    private SharedElementCallback mSharedElementCallback = new SharedElementCallback() {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            KLog.w(TAG, "onMapSharedElements: names = " + names + ", sharedElements = " + sharedElements);
            int position = mLayoutManager.getCurrentPosition();
            View view = mLayoutManager.findViewByPosition(position);
            names.clear();
            sharedElements.clear();
            sharedElements.put(String.valueOf(position), view);
        }
    };

    private static class MyAdapter extends ARecyclerAdapter<Integer> {

        public MyAdapter(@Nullable List<Integer> datas) {
            super(datas);
        }

        @Override
        protected void onBindViewData(RecyclerHolder holder, int position, List<Integer> datas) {
            ViewCompat.setTransitionName(holder.itemView, String.valueOf(position));

            CardView cardView = holder.getView(R.id.card);
            cardView.setCardBackgroundColor(getItem(position));

            holder.setText(R.id.tv_index, String.valueOf(position));
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_carousel;
        }

        @Override
        public Integer getItem(int position) {
            return mDatas.get(position);
        }
    }
}
