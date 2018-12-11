package com.loosu.activityanimation.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.loosu.activityanimation.R;
import com.loosu.adapter.recyclerview.ARecyclerAdapter;
import com.loosu.adapter.recyclerview.RecyclerHolder;
import com.loosu.utils.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewPagerGalleryActivity extends AppCompatActivity {
    private static final String TAG = "ViewPagerGalleryActivity";


    private static final String KEY_IMGS = "key_imgs";
    private static final String KEY_INDEX = "key_index";
    private RecyclerView mViewList;
    private LinearLayoutManager mLayoutManager;

    public static Intent getStartIntent(Context context, List<Integer> imgs, int index) {
        ArrayList<Integer> imgList = new ArrayList<>();
        imgList.addAll(imgs);

        Intent intent = new Intent(context, ViewPagerGalleryActivity.class);
        intent.putExtra(KEY_IMGS, imgList);
        intent.putExtra(KEY_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_gallery);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }

    private void findView(Bundle savedInstanceState) {
        mViewList = findViewById(R.id.view_list);
    }

    private void initView(Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        mViewList.setLayoutManager(mLayoutManager);
        mViewList.setAdapter(new MyAdapter((List<Integer>) getIntent().getSerializableExtra(KEY_IMGS)));
        mViewList.scrollToPosition(getIntent().getIntExtra(KEY_INDEX, 0));

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mViewList);
    }

    private void initListener(final Bundle savedInstanceState) {
        mViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "onScrollStateChanged:" + newState);
            }
        });
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                int position = Integer.valueOf(mLayoutManager.findFirstVisibleItemPosition());
                View view = mLayoutManager.findViewByPosition(position);
                sharedElements.put(String.valueOf(position), view);

                KLog.e(TAG, "onMapSharedElements: " + names + ", views: " + sharedElements);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(KEY_INDEX, mLayoutManager.findFirstVisibleItemPosition());
        setResult(Activity.RESULT_OK, intent);
        finishAfterTransition();
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
    }

    private static class MyAdapter extends ARecyclerAdapter<Integer> {

        public MyAdapter(@Nullable List<Integer> datas) {
            super(datas);
        }

        @Override
        protected void onBindViewData(RecyclerHolder holder, final int position, List<Integer> datas) {
            Glide.with(holder.itemView)
                    .load(getItem(position))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e(TAG, "onResourceReady: position = " + position);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.e(TAG, "onResourceReady: position = " + position);
                            return false;
                        }
                    })
                    .into((ImageView) holder.getView(R.id.iv_image));
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_viewpager_gallery;
        }

        @Override
        public Integer getItem(int position) {
            return mDatas.get(position);
        }
    }
}
