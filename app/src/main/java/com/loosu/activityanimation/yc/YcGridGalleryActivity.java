package com.loosu.activityanimation.yc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElementSelector;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.loosu.activityanimation.R;
import com.loosu.adapter.recyclerview.ARecyclerAdapter;
import com.loosu.adapter.recyclerview.IRecyclerItemClickListener;
import com.loosu.adapter.recyclerview.RecyclerHolder;
import com.loosu.utils.KLog;

import java.util.ArrayList;
import java.util.List;

public class YcGridGalleryActivity extends AppCompatActivity implements IRecyclerItemClickListener, IShareElements {
    private static final String TAG = "GridGalleryActivity";

    private RecyclerView mViewList;
    private MyAdapter mAdapter;
    private int mIndex;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.setEnterTransitions(this, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_gallery);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }


    @NonNull
    private void init(Bundle savedInstanceState) {
        YcShareElement.enableContentTransition(getApplication());

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
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                KLog.w("onScrolled: dy = " + dy);
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView parent, int position, RecyclerView.ViewHolder holder, final View view) {
        Intent intent = YcViewPagerGalleryActivity.getStartIntent(getBaseContext(), mAdapter.getDatas(), position);
        Bundle bundle = YcShareElement.buildOptionsBundle(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                ShareElementInfo elementInfo = new ShareElementInfo(view);
                return new ShareElementInfo[]{elementInfo,};
            }
        });
        ActivityCompat.startActivityForResult(this, intent, 666, bundle);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        KLog.e(TAG, "onActivityReenter: resultCode = " + data);
        super.onActivityReenter(resultCode, data);
        YcShareElement.onActivityReenter(this, resultCode, data, new IShareElementSelector() {
            @Override
            public void selectShareElements(List<ShareElementInfo> list) {
                KLog.e(TAG, "selectShareElements: list = " + list);
            }
        });
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        KLog.w(TAG, "getShareElements: position = " );
        return new ShareElementInfo[0];
    }

    @Override
    public void finishAfterTransition() {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }

    private static class MyAdapter extends ARecyclerAdapter<Integer> {

        public MyAdapter(@Nullable List<Integer> datas) {
            super(datas);
        }

        @Override
        protected void onBindViewData(RecyclerHolder holder, int position, List<Integer> datas) {
            ViewCompat.setTransitionName(holder.itemView, String.valueOf(position));
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
