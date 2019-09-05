package com.tohami.egyptinnovate.ui.news.details.data;

import com.tohami.egyptinnovate.data.model.GetNewsDetailsResponse;
import com.tohami.egyptinnovate.data.retrofit.ApiInterface;
import com.tohami.egyptinnovate.ui.base.BaseRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsDetailsRepository extends BaseRepository {

    @Inject
    public NewsDetailsRepository(ApiInterface newsApi) {
        super(newsApi);
    }

    public Single<GetNewsDetailsResponse> getNewsDetails(String id) {
        return getNewsApi().getNewsDetails(id);
    }
}