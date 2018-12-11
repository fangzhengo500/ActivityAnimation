package com.loosu.activityanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /**
     * ActivityOptionsCompat.makeBasic();
     * ActivityOptionsCompat.makeClipRevealAnimation();
     * ActivityOptionsCompat.makeCustomAnimation();
     * ActivityOptionsCompat.makeScaleUpAnimation();
     * ActivityOptionsCompat.makeSceneTransitionAnimation();
     * ActivityOptionsCompat.makeTaskLaunchBehind();
     * ActivityOptionsCompat.makeThumbnailScaleUpAnimation();
     */
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
    }

    public void onClickCustomAnimation(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.curtom_enter, R.anim.custom_exit);
        Intent intent = TestActivity.getStartIntent(mContext, R.mipmap.pic1, getString(R.string.custom_animation));
        ActivityCompat.startActivity(mContext, intent, compat.toBundle());
    }

    public void onClickScaleUpAnimation(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
        Intent intent = TestActivity.getStartIntent(mContext, R.mipmap.pic1, getString(R.string.scale_up_animation));
        ActivityCompat.startActivity(mContext, intent, compat.toBundle());
    }

    public void onClickThumbnailScaleUpAnimation(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic2);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, bitmap, view.getLeft(), view.getTop());
        Intent intent = TestActivity.getStartIntent(mContext, R.mipmap.pic2, getString(R.string.thumbnail_scale_up_animation));
        ActivityCompat.startActivity(mContext, intent, compat.toBundle());
    }

    public void onClickSceneTransitionAnimation1(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, getString(R.string.card_transition));
        Intent intent = TestActivity.getStartIntent(mContext, R.mipmap.pic3, getString(R.string.scene_transition_animation_1));
        ActivityCompat.startActivity(mContext, intent, compat.toBundle());
    }

    public void onClickSceneTransitionAnimation2(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair.create(findViewById(R.id.iv_cover), getString(R.string.iv_cover_transition)),
                Pair.create(findViewById(R.id.tv_title), getString(R.string.tv_title_transition)));
        Intent intent = TestActivity.getStartIntent(mContext, R.mipmap.pic1, getString(R.string.scene_transition_animation_2));
        ActivityCompat.startActivity(mContext, intent, compat.toBundle());
    }

    public void onClickSceneTransitionAnimationExplode(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(mContext, TransitionActivity.class);
        ActivityCompat.startActivity(mContext, intent, compat.toBundle());
    }

    public void onClickGallery(View view) {
        Intent intent = new Intent(mContext, GridGalleryActivity.class);
        startActivity(intent);
    }

    public void onClickYcGallery(View view) {
        Intent intent = new Intent(mContext, YcGridGalleryActivity.class);
        startActivity(intent);
    }
}
