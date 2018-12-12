package com.loosu.activityanimation.ui.yc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.loosu.activityanimation.ui.yc.fragment.YcGridGalleryFragment;

public class YcGridGalleryActivity extends AppCompatActivity {
    private static final String TAG = "GridGalleryActivity";

    private YcGridGalleryFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = new YcGridGalleryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mFragment)
                .commit();
    }
}
