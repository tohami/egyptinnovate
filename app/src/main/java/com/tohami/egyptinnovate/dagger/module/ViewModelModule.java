package com.tohami.egyptinnovate.dagger.module;

import androidx.lifecycle.ViewModelProvider;

import com.tohami.egyptinnovate.ui.news.details.viewModel.NewsDetailsViewModelFactory;
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindNewsListViewModelFactory(NewsListViewModelFactory factory);

    @Binds
    abstract ViewModelProvider.Factory bindNewsDetailsViewModelFactory(NewsDetailsViewModelFactory factory);
}