package com.tohami.egyptinnovate.ui.news.details.viewModel;


import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tohami.egyptinnovate.ui.news.details.data.NewsDetailsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class NewsDetailsViewModelFactory implements ViewModelProvider.Factory {

    ConnectivityManager connectivityManager;
    NewsDetailsRepository repository;

    @Inject
    public NewsDetailsViewModelFactory(NewsDetailsRepository repository , ConnectivityManager connectivityManager) {
        this.repository = repository ;
        this.connectivityManager = connectivityManager ;
    }


    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return (T) new NewsDetailsViewModel(repository, connectivityManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}