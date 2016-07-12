package com.madisonrong.gankgirls.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madisonrong.gankgirls.R;
import com.madisonrong.gankgirls.models.DataModel;
import com.squareup.picasso.Picasso;

import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MadisonRong on 6/28/16.
 */
public class GankGirlsRecyclerViewAdapter extends BaseRecyclerViewAdapter<DataModel, GankGirlsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "GankGirlsAdapter";

    private Activity activity;

    public GankGirlsRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
    }

    public GankGirlsRecyclerViewAdapter(int capacity, Activity activity) {
        super(capacity);
        this.activity = activity;
    }

    public GankGirlsRecyclerViewAdapter(Collection<? extends DataModel> collection, Activity activity) {
        super(collection);
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_girls, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String url = this.get(position).getUrl();
        Log.i(TAG, String.format("pos: %d, url: %s", position, url));
        final ImageView imageView = holder.imageView;
        Picasso.with(activity)
                .load(url)
                .placeholder(R.mipmap.drawer_loading)
                .error(R.mipmap.drawer_error)
                .resize(200, 200)
                .centerCrop()
                .into(imageView);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder, position);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_girls_image)
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, int position);
    }
}
