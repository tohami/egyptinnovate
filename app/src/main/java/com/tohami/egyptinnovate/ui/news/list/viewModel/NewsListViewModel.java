package com.tohami.egyptinnovate.ui.news.list.viewModel;

import android.net.ConnectivityManager;

import com.tohami.egyptinnovate.data.model.DataModel;
import com.tohami.egyptinnovate.data.model.NewsItem;
import com.tohami.egyptinnovate.ui.base.BaseViewModel;
import com.tohami.egyptinnovate.ui.news.list.data.NewsRepository;
import com.tohami.egyptinnovate.utilities.Constants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class NewsListViewModel extends BaseViewModel {

    private final BehaviorSubject<DataModel<List<NewsItem>>> newsSubject = BehaviorSubject.create();
    private final NewsRepository newsRepository;

    public NewsListViewModel(NewsRepository newsRepository, ConnectivityManager connectivityManager) {
        super(connectivityManager);
        this.newsRepository = newsRepository;

    }

    public BehaviorSubject<DataModel<List<NewsItem>>> getNewsSubject() {
        return newsSubject;
    }

    public void refreshNewsList() {

        if (!isNetworkAvailable()) {
            DataModel<List<NewsItem>> error = new DataModel<>(Constants.Status.NO_NETWORK, null, null);
            newsSubject.onNext(error);
            return;
        }

        addDisposable(
                newsRepository.getNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getNewsResponse -> {
                                    DataModel<List<NewsItem>> newsListDataModel = new DataModel<>(Constants.Status.SUCCESS, null, getNewsResponse.getNewsItems());
                                    newsSubject.onNext(newsListDataModel);
                                }, throwable -> {
                                    DataModel<List<NewsItem>> error = new DataModel<>(Constants.Status.FAIL, throwable.getMessage(), null);
                                    newsSubject.onNext(error);
                                })
        );

        DataModel<List<NewsItem>> loading = new DataModel<>(Constants.Status.LOADING, null, null);
        newsSubject.onNext(loading);
    }


}
