package com.tohami.egyptinnovate.dagger.module;

import com.tohami.egyptinnovate.ui.news.details.view.NewsDetailsFragment;
import com.tohami.egyptinnovate.ui.news.list.view.NewsListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = ViewModelModule.class)
public abstract class MainActivityBindingModule {

    @ContributesAndroidInjector
    abstract NewsListFragment provideNewsListFragment();

    @ContributesAndroidInjector
    abstract NewsDetailsFragment provideNewsDetailsFragment();
}
