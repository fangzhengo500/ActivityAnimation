package com.loosu.activityanimation.carousel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loosu.activityanimation.R;
import com.loosu.utils.KLog;

import java.util.ArrayList;
import java.util.List;

public class CarouselActivity extends AppCompatActivity {
    private static final String TAG = "CarouselActivity";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyPagerAdapter mAdapter;


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
        List<CarouselFragment> fragments = new ArrayList<>();
        fragments.add(new CarouselFragment());
        fragments.add(new CarouselFragment());
        fragments.add(new CarouselFragment());

        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
    }

    private void findView(Bundle savedInstanceState) {
        mTabLayout = findViewById(R.id.layout_tab);
        mViewPager = findViewById(R.id.view_pager);
    }

    private void initView(Bundle savedInstanceState) {
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void intiListener(Bundle savedInstanceState) {
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        CarouselFragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
        fragment.onActivityReenter(this, resultCode, data);
    }


    private static class MyPagerAdapter extends FragmentPagerAdapter {
        private List<CarouselFragment> mFragments;

        public MyPagerAdapter(FragmentManager fm, List<CarouselFragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public CarouselFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }
    }
}
