package com.tohami.egyptinnovate.ui.news.details.viewModel;

import android.net.ConnectivityManager;

import com.tohami.egyptinnovate.data.model.DataModel;
import com.tohami.egyptinnovate.data.model.NewsDetails;
import com.tohami.egyptinnovate.ui.base.BaseViewModel;
import com.tohami.egyptinnovate.ui.news.details.data.NewsDetailsRepository;
import com.tohami.egyptinnovate.utilities.Constants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class NewsDetailsViewModel extends BaseViewModel {

    private final NewsDetailsRepository newsDetailsRepository;
    private BehaviorSubject<DataModel<NewsDetails>> newsSubject = BehaviorSubject.create();

    NewsDetailsViewModel(NewsDetailsRepository newsDetailsRepository, ConnectivityManager connectivityManager) {
        super(connectivityManager);
        this.newsDetailsRepository = newsDetailsRepository;
    }

    public Observable<DataModel<NewsDetails>> getNewsDetails(String id) {

        if (!isNetworkAvailable()) {
            DataModel<NewsDetails> error = new DataModel<>(Constants.Status.NO_NETWORK, null, null);
            newsSubject.onNext(error);
            return newsSubject;
        }

        addDisposable(newsDetailsRepository.getNewsDetails(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(newsDetailsResponse -> {
                    DataModel<NewsDetails> newsDetailsDataModel = new DataModel<>(Constants.Status.SUCCESS, null, newsDetailsResponse.getNewsDetails());
                    newsSubject.onNext(newsDetailsDataModel);
                }, throwable -> {
                    DataModel<NewsDetails> error = new DataModel<>(Constants.Status.FAIL, throwable.getMessage(), null);
                    newsSubject.onNext(error);
                })
        );

        //send loading
        DataModel<NewsDetails> loading = new DataModel<>(Constants.Status.LOADING, null, null);
        newsSubject.onNext(loading);

        return newsSubject;
    }

    public BehaviorSubject<DataModel<NewsDetails>> getNewsSubject() {
        return newsSubject;
    }
}
