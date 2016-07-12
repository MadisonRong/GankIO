package com.madisonrong.gankgirls.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.madisonrong.gankgirls.models.ResponseModel;
import com.madisonrong.gankgirls.network.GankGirlsClient;
import com.madisonrong.gankgirls.network.RetrofitGenerator;
import com.madisonrong.gankgirls.views.adapters.GankGirlsRecyclerViewAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MadisonRong on 6/28/16.
 */
public class NetworkManager {


    private static final String TAG = "NetworkManager";

    private Context ctx;
    private GankGirlsRecyclerViewAdapter adapter;
    public NetworkManager(Context ctx, GankGirlsRecyclerViewAdapter adapter) {
        this.ctx = ctx;
        this.adapter = adapter;
    }

    public void getGirlList(int page) {
        GankGirlsClient client = RetrofitGenerator.getService(GankGirlsClient.class);
        client.getPage("福利", page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called with: " + "");
                        Toast.makeText(ctx, "getting list completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                        e.printStackTrace();
                        Toast.makeText(ctx, "getting list occur an error: " + e, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.d(TAG, "onNext() called with: " + "responseBody = [" + responseBody + "]");
                        try {
                            Gson gson = new Gson();
                            ResponseModel responseModel = gson.fromJson(responseBody.string(), ResponseModel.class);
                            if (!responseModel.isError() && responseModel.getResults() != null) {
                                adapter.addAll(responseModel.getResults());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
