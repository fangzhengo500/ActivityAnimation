package com.loosu.activityanimation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.loosu.activityanimation.R;

public class TestActivity extends AppCompatActivity {
    private static final String KEY_RES_ID = "key_res_id";
    private static final String KEY_TITLE = "key_title";

    public static Intent getStartIntent(Context context, @DrawableRes int resId, String title) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(KEY_RES_ID, resId);
        intent.putExtra(KEY_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView ivCover = findViewById(R.id.iv_cover);
        ivCover.setBackgroundResource(getIntent().getIntExtra(KEY_RES_ID, 0));

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getIntent().getStringExtra(KEY_TITLE));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
