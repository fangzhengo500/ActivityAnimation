package com.loosu.activityanimation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.loosu.adapter.recyclerview.ARecyclerAdapter;
import com.loosu.adapter.recyclerview.RecyclerHolder;
import com.loosu.utils.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarouselDetailActivity extends AppCompatActivity {
    private static final String TAG = "CarouselDetailActivity";

    private static final String KEY_DATAS = "key_datas";
    private static final String KEY_INDEX = "key_index";

    private RecyclerView mViewList;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    public static Intent getStartIntent(Context context, List<Integer> datas, int index) {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.addAll(datas);

        Intent intent = new Intent(context, CarouselDetailActivity.class);
        intent.putExtra(KEY_DATAS, colors);
        intent.putExtra(KEY_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.postponeEnterTransition(this);
        setContentView(R.layout.activity_carousel_detail);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }


    private void init(Bundle savedInstanceState) {
        mAdapter = new MyAdapter((List<Integer>) getIntent().getSerializableExtra(KEY_DATAS));
    }

    private void findView(Bundle savedInstanceState) {
        mViewList = findViewById(R.id.view_list);
    }

    private void initView(Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mViewList.setLayoutManager(mLayoutManager);
        mViewList.setAdapter(mAdapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mViewList);

        mViewList.scrollToPosition(getIntent().getIntExtra(KEY_INDEX, 0));

    }

    private void initListener(final Bundle savedInstanceState) {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                KLog.e(TAG, "onMapSharedElements: names = " + names + ", sharedElements = " + sharedElements);
                int position = mLayoutManager.findFirstVisibleItemPosition();
                View view = mLayoutManager.findViewByPosition(position);
                sharedElements.clear();
                sharedElements.put(String.valueOf(position), view);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(KEY_INDEX, mLayoutManager.findFirstVisibleItemPosition());
        setResult(Activity.RESULT_OK, data);
        ActivityCompat.finishAfterTransition(this);
    }

    private static class MyAdapter extends ARecyclerAdapter<Integer> {

        public MyAdapter(@Nullable List<Integer> datas) {
            super(datas);
        }

        @Override
        protected void onBindViewData(RecyclerHolder holder, int position, List<Integer> datas) {
            ActivityCompat.startPostponedEnterTransition((Activity) holder.itemView.getContext());

            ViewCompat.setTransitionName(holder.itemView, String.valueOf(position));

            CardView cardView = holder.getView(R.id.card);
            cardView.setCardBackgroundColor(getItem(position));

            holder.setText(R.id.tv_index, String.valueOf(position));
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_carousel_detail;
        }

        @Override
        public Integer getItem(int position) {
            return mDatas.get(position);
        }
    }
}
