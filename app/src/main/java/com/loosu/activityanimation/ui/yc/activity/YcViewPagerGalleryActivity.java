package com.loosu.activityanimation.ui.yc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.DefaultShareElementTransitionFactory;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.loosu.activityanimation.ui.yc.Yc;
import com.loosu.activityanimation.ui.yc.fragment.YcViewPagerGalleryFragment;
import com.loosu.activityanimation.utils.KLog;

import java.util.ArrayList;
import java.util.List;

public class YcViewPagerGalleryActivity extends AppCompatActivity implements IShareElements {
    private static final String KEY_IMGS = "key_imgs";
    private static final String KEY_INDEX = "key_index";

    private YcViewPagerGalleryFragment mFragment;

    public static Intent getStartIntent(Context context, List<Integer> imgs, int index) {
        ArrayList<Integer> datas = new ArrayList<>();
        datas.addAll(imgs);

        Intent intent = new Intent(context, YcViewPagerGalleryActivity.class);
        intent.putExtra(KEY_IMGS, datas);
        intent.putExtra(KEY_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.setEnterTransitions(this, this,true);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mFragment = YcViewPagerGalleryFragment.createFragment((ArrayList<Integer>) intent.getSerializableExtra(KEY_IMGS), intent.getIntExtra(KEY_INDEX, 0));
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mFragment)
                .commit();
    }

    @Override
    public void finishAfterTransition() {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return mFragment.getShareElements();
    }
}
