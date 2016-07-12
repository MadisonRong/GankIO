package com.madisonrong.gankgirls.network;

import com.madisonrong.gankgirls.config.GankGirlsConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MadisonRong on 15/7/29.
 */
public class RetrofitGenerator {

    private RetrofitGenerator() {
    }

    public static <T> T getService(Class<T> clazz) {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GankGirlsConfig.BASE_URL)
                .client(new OkHttpClient())
                .build();
        return retrofit.create(clazz);
    }
}
