package com.madisonrong.gankgirls.network;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by MadisonRong on 6/27/16.
 */
public interface GankGirlsClient {

    @GET("api/data/{type}/{rows}/{page}")
    Observable<ResponseBody> getPage(@Path("type") String type,
                                     @Path("page") int page,
                                     @Path("rows") int rows);

//    @GET("/a/")
}
