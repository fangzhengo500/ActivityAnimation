package com.loosu.activityanimation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
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
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.loosu.adapter.recyclerview.ARecyclerAdapter;
import com.loosu.adapter.recyclerview.RecyclerHolder;
import com.loosu.utils.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YcViewPagerGalleryActivity extends AppCompatActivity implements IShareElements {
    private static final String TAG = "ViewPagerGalleryActivity";


    private static final String KEY_IMGS = "key_imgs";
    private static final String KEY_INDEX = "key_index";
    private RecyclerView mViewList;
    private LinearLayoutManager mLayoutManager;

    public static Intent getStartIntent(Context context, List<Integer> imgs, int index) {
        ArrayList<Integer> imgList = new ArrayList<>();
        imgList.addAll(imgs);

        Intent intent = new Intent(context, YcViewPagerGalleryActivity.class);
        intent.putExtra(KEY_IMGS, imgList);
        intent.putExtra(KEY_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YcShareElement.setEnterTransitions(this, this, false);
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

    }

    @Override
    public void finishAfterTransition() {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        int position = mLayoutManager.findFirstVisibleItemPosition();
        View view = mLayoutManager.findViewByPosition(position);
        KLog.w(TAG, "getShareElements: position = " + position + ", view = " + view);
        return new ShareElementInfo[]{new ShareElementInfo(view)};
    }

    private static class MyAdapter extends ARecyclerAdapter<Integer> {

        public MyAdapter(@Nullable List<Integer> datas) {
            super(datas);
        }

        @Override
        protected void onBindViewData(RecyclerHolder holder, final int position, List<Integer> datas) {
            ViewCompat.setTransitionName(holder.itemView, String.valueOf(position));
            Glide.with(holder.itemView)
                    .load(getItem(position))
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
