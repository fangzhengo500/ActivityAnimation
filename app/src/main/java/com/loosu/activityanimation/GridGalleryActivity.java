package com.loosu.activityanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.loosu.adapter.recyclerview.ARecyclerAdapter;
import com.loosu.adapter.recyclerview.IRecyclerItemClickListener;
import com.loosu.adapter.recyclerview.RecyclerHolder;
import com.loosu.utils.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridGalleryActivity extends AppCompatActivity implements IRecyclerItemClickListener {
    private static final String TAG = "GridGalleryActivity";

    private RecyclerView mViewList;
    private MyAdapter mAdapter;
    private int mIndex;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_gallery);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }


    @NonNull
    private void init(Bundle savedInstanceState) {
        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.mipmap.pic4);
        imgs.add(R.mipmap.pic5);
        imgs.add(R.mipmap.pic6);
        imgs.add(R.mipmap.pic7);
        imgs.add(R.mipmap.pic8);
        imgs.add(R.mipmap.pic9);
        imgs.add(R.mipmap.pic10);
        imgs.add(R.mipmap.pic11);
        imgs.add(R.mipmap.pic12);
        imgs.add(R.mipmap.pic13);
        imgs.add(R.mipmap.pic4);
        imgs.add(R.mipmap.pic5);
        imgs.add(R.mipmap.pic6);
        imgs.add(R.mipmap.pic7);
        imgs.add(R.mipmap.pic8);
        imgs.add(R.mipmap.pic9);
        imgs.add(R.mipmap.pic10);
        imgs.add(R.mipmap.pic11);
        imgs.add(R.mipmap.pic12);
        imgs.add(R.mipmap.pic13);
        imgs.add(R.mipmap.pic4);
        imgs.add(R.mipmap.pic5);
        imgs.add(R.mipmap.pic6);
        imgs.add(R.mipmap.pic7);
        imgs.add(R.mipmap.pic8);
        imgs.add(R.mipmap.pic9);
        imgs.add(R.mipmap.pic10);
        imgs.add(R.mipmap.pic11);
        imgs.add(R.mipmap.pic12);
        imgs.add(R.mipmap.pic13);
        imgs.add(R.mipmap.pic4);
        imgs.add(R.mipmap.pic5);
        imgs.add(R.mipmap.pic6);
        imgs.add(R.mipmap.pic7);
        imgs.add(R.mipmap.pic8);
        imgs.add(R.mipmap.pic9);
        imgs.add(R.mipmap.pic10);
        imgs.add(R.mipmap.pic11);
        imgs.add(R.mipmap.pic12);
        imgs.add(R.mipmap.pic13);

        mAdapter = new MyAdapter(imgs);
    }

    private void findView(Bundle savedInstanceState) {
        mViewList = findViewById(R.id.view_list);
    }

    private void initView(Bundle savedInstanceState) {
        mLayoutManager = new GridLayoutManager(getBaseContext(), 3);
        mViewList.setLayoutManager(mLayoutManager);
        mViewList.setAdapter(mAdapter);
    }

    private void initListener(Bundle savedInstanceState) {
        mAdapter.setItemClickListener(this);
        mViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KLog.w("onScrollStateChanged: newState = " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    ActivityCompat.startPostponedEnterTransition(GridGalleryActivity.this);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                KLog.w("onScrolled: dy = " + dy);
                ActivityCompat.startPostponedEnterTransition(GridGalleryActivity.this);
            }
        });
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                names.clear();
                sharedElements.clear();
                RecyclerView.ViewHolder holder = mViewList.findViewHolderForAdapterPosition(mIndex);
                if (holder != null) {
                    sharedElements.put(String.valueOf(mIndex), holder.itemView);
                }
                KLog.w("onMapSharedElements: " + names + ", views: " + sharedElements);
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView parent, int position, RecyclerView.ViewHolder holder, View view) {
        Intent intent = ViewPagerGalleryActivity.getStartIntent(getBaseContext(), mAdapter.getDatas(), position);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, String.valueOf(position));
        ActivityCompat.startActivityForResult(this, intent, 666, compat.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        KLog.w("onActivityResult: requestCode = " + requestCode + ", resultCode = " + resultCode);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        KLog.w("onActivityReenter: resultCode = " + resultCode );
        if (data != null) {
            mIndex = data.getIntExtra("key_index", -1);
            View positionView = mLayoutManager.findViewByPosition(mIndex);
            if (positionView == null) {
                ActivityCompat.postponeEnterTransition(this);
                mViewList.scrollToPosition(mIndex);
            }
        }
    }

    private static class MyAdapter extends ARecyclerAdapter<Integer> {

        public MyAdapter(@Nullable List<Integer> datas) {
            super(datas);
        }

        @Override
        protected void onBindViewData(RecyclerHolder holder, int position, List<Integer> datas) {
            ImageView imageView = holder.getView(R.id.iv_image);
            imageView.setImageResource(datas.get(position));
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_gallery;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

    }
}
