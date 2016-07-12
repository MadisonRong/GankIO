package com.madisonrong.gankgirls.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Window;

import com.madisonrong.gankgirls.R;
import com.madisonrong.gankgirls.managers.NetworkManager;
import com.madisonrong.gankgirls.views.adapters.GankGirlsRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.gank_girl_recycler_view)
    public RecyclerView recyclerView;
    private GankGirlsRecyclerViewAdapter adapter;
    private NetworkManager manager;
    private RecyclerView.OnScrollListener onScrollListener;
    private int page = 1;
    private boolean canLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG, "It's here.....");
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.slide_explode);
            getWindow().setEnterTransition(explode);
            getWindow().setExitTransition(explode);
            getWindow().setReenterTransition(explode);
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        adapter = new GankGirlsRecyclerViewAdapter(this);
        manager = new NetworkManager(this, adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new GankGirlsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GankGirlsRecyclerViewAdapter.ViewHolder viewHolder, int position) {
                Log.i(TAG, "onItemClick: click item: " + position);
                DetailActivity.actionStart(MainActivity.this, adapter.get(position).getUrl());
            }
        });
        onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisibleItems = layoutManager.findLastCompletelyVisibleItemPositions(null);
                int lastItem = Math.max(lastVisibleItems[0], lastVisibleItems[1]);

                //先判断是否已经到底了
                if (dy > 0 && lastItem > adapter.getItemCount() - 5) {
                    //然后再来判断是否正在加载
                    if (canLoad) {
                        canLoad = false;
                        page += 1;
                        Log.i(TAG, "onScrolled: load page: " + page);
                        manager.getGirlList(page);
                    }
                } else {
                    //加载完成后，上面的条件会判断失败，此时就可以修改canLoad条件
                    canLoad = true;
                }
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);
        manager.getGirlList(page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.removeOnScrollListener(onScrollListener);
    }
}
