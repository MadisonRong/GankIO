package com.madisonrong.gankgirls.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bm.library.PhotoView;
import com.madisonrong.gankgirls.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MadisonRong on 6/28/16.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String PARAM_IMAGE_URL = "image_url";

    @Bind(R.id.gank_girl_photo_view)
    public PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        photoView.enable();
        photoView.setMaxScale(1f);

        String imageUrl = getIntent().getStringExtra(PARAM_IMAGE_URL);
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.mipmap.drawer_loading)
                .error(R.mipmap.drawer_error)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static void actionStart(Activity activity, String imageUrl) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(PARAM_IMAGE_URL, imageUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }
}
