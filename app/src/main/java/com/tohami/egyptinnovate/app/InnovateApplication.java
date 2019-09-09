package com.tohami.egyptinnovate.app;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.tohami.egyptinnovate.app.di.ApplicationComponent;
import com.tohami.egyptinnovate.app.di.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class InnovateApplication extends DaggerApplication {


	@Override
	public void onCreate() {
		super.onCreate();
		setupPicasso() ;
	}

	private void setupPicasso() {
		Picasso.Builder picassoBuilder = new Picasso.Builder(this);
		picassoBuilder.downloader(new OkHttp3Downloader(this , Integer.MAX_VALUE));
		picassoBuilder.loggingEnabled(true) ;
		picassoBuilder.indicatorsEnabled(true) ;

		Picasso picasso = picassoBuilder.build();

		Picasso.setSingletonInstance(picasso);

		try {
			Picasso.setSingletonInstance(picasso);
		} catch (IllegalStateException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
		component.inject(this);

		return component;
	}
}
