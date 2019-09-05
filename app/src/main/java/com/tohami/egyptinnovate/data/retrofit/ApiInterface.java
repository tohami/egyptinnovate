package com.tohami.egyptinnovate.data.retrofit;

import com.tohami.egyptinnovate.data.model.GetNewsDetailsResponse;
import com.tohami.egyptinnovate.data.model.GetNewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("safe/GetNews")
    Single<GetNewsResponse> getNewsList();

    @GET("safe/GetNewsDetails")
    Single<GetNewsDetailsResponse> getNewsDetails(@Query("nid") String newsId);
}
