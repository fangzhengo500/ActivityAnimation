package com.loosu.activityanimation.ui.yc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hw.ycshareelement.YcShareElement;
import com.loosu.activityanimation.ui.yc.Yc;
import com.loosu.activityanimation.ui.yc.fragment.YcGridGalleryFragment;
import com.loosu.activityanimation.utils.KLog;

public class YcGridGalleryActivity extends AppCompatActivity {
    private static final String TAG = "GridGalleryActivity";

    private YcGridGalleryFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.enableContentTransition(getApplication());
        super.onCreate(savedInstanceState);
        mFragment = new YcGridGalleryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mFragment)
                .commit();
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        KLog.d(Yc.TAG, "onActivityReenter: resultCode = " + resultCode + ", data " + data);
    }
}
