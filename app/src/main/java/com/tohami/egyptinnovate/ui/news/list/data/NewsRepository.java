package com.tohami.egyptinnovate.ui.news.list.data;

import com.tohami.egyptinnovate.data.model.GetNewsResponse;
import com.tohami.egyptinnovate.data.retrofit.ApiInterface;
import com.tohami.egyptinnovate.ui.base.BaseRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsRepository extends BaseRepository {

    @Inject
    public NewsRepository(ApiInterface newsApi) {
        super(newsApi);
    }

    public Single<GetNewsResponse> getNews() {
        return getNewsApi().getNewsList();
    }
}