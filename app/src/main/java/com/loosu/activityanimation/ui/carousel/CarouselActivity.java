package com.loosu.activityanimation.ui.carousel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.loosu.activityanimation.R;
import com.loosu.activityanimation.utils.KLog;

import java.util.ArrayList;
import java.util.List;

public class CarouselActivity extends AppCompatActivity {
    private static final String TAG = "CarouselActivity";

    private List<CarouselFragment> mFragments;

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
        mFragments = new ArrayList<>();
        mFragments.add(new CarouselFragment());
//        mFragments.add(new CarouselFragment());
//        mFragments.add(new CarouselFragment());

        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);
    }

    private void findView(Bundle savedInstanceState) {
        mTabLayout = findViewById(R.id.layout_tab);
        mViewPager = findViewById(R.id.view_pager);
    }

    private void initView(Bundle savedInstanceState) {
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.layout_container, mFragments.get(0))
//                .commit();
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void intiListener(Bundle savedInstanceState) {
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        //mFragments.get(0).onActivityReenter(this,resultCode,data);
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
