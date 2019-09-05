package com.tohami.egyptinnovate.ui.base;

import com.tohami.egyptinnovate.data.retrofit.ApiInterface;

public abstract class BaseRepository {

    private final ApiInterface newsApi;

    public BaseRepository(ApiInterface newsApi ) {
        this.newsApi = newsApi;
    }

    protected ApiInterface getNewsApi() {
        return newsApi;
    }

}
