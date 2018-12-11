package com.loosu.activityanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leochuan.CarouselLayoutManager;
import com.leochuan.CenterSnapHelper;
import com.leochuan.ScrollHelper;
import com.loosu.adapter.recyclerview.ARecyclerAdapter;
import com.loosu.adapter.recyclerview.IRecyclerItemClickListener;
import com.loosu.adapter.recyclerview.RecyclerHolder;
import com.loosu.utils.ColorData;
import com.loosu.utils.KLog;

import java.util.List;
import java.util.Map;

public class CarouselActivity extends AppCompatActivity {
    private static final String TAG = "CarouselActivity";

    private RecyclerView mViewList;
    private CarouselLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        intiListener(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mAdapter = new MyAdapter(ColorData.getColors());
    }

    private void findView(Bundle savedInstanceState) {
        mViewList = findViewById(R.id.view_list);
    }

    private void initView(Bundle savedInstanceState) {
        mLayoutManager = new CarouselLayoutManager.Builder(getApplicationContext(), 600)
                .build();
        mViewList.setLayoutManager(mLayoutManager);
        mViewList.setAdapter(mAdapter);

        CenterSnapHelper snapHelper = new CenterSnapHelper();
        snapHelper.attachToRecyclerView(mViewList);
    }

    private void intiListener(Bundle savedInstanceState) {
        mViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                KLog.w(TAG, "onScrollStateChanged: recyclerView = " + recyclerView +", newState = " + newState );
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ActivityCompat.startPostponedEnterTransition(CarouselActivity.this);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                KLog.w(TAG, "onScrolled: dx = " + dx + ", dy = " + dy);
            }
        });
        mAdapter.setItemClickListener(itemClickListener);
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                KLog.w(TAG, "onMapSharedElements: names = " + names + ", sharedElements = " + sharedElements);
                int position = mLayoutManager.getCurrentPosition();
                View view = mLayoutManager.findViewByPosition(position);
                names.clear();
                sharedElements.clear();
                sharedElements.put(String.valueOf(position), view);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        int index = data.getIntExtra("key_index", 0);

        View view = mLayoutManager.findViewByPosition(index);
        if (view == null){
            ActivityCompat.postponeEnterTransition(this);
            mViewList.smoothScrollToPosition(index);
        }
        KLog.w(TAG, "onActivityReenter: index = " + index);
    }

    private IRecyclerItemClickListener itemClickListener = new IRecyclerItemClickListener() {
        @Override
        public void onItemClick(RecyclerView parent, int position, RecyclerView.ViewHolder holder, View view) {
            int centerPosition = mLayoutManager.getCurrentPosition();
            if (centerPosition == position) {
                Context context = CarouselActivity.this;

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(CarouselActivity.this, view, String.valueOf(position));
                Intent intent = CarouselDetailActivity.getStartIntent(context, mAdapter.getDatas(), position);
                ActivityCompat.startActivity(context, intent, compat.toBundle());
            } else {
                ScrollHelper.smoothScrollToTargetView(parent, holder.itemView);
            }
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
