package com.tohami.egyptinnovate.app.di;

import android.content.Context;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.tohami.egyptinnovate.BuildConfig;
import com.tohami.egyptinnovate.data.retrofit.ApiInterface;
import com.tohami.egyptinnovate.app.settings.AppSettings;
import com.tohami.egyptinnovate.app.localization.LanguageSelectionInspector;
import com.tohami.egyptinnovate.app.settings.SharedPreferenceAppSettings;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(AppSettings appSettings) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        LanguageSelectionInspector languageSelectionInspector = new LanguageSelectionInspector();
        languageSelectionInspector.setAppSettings(appSettings);

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(languageSelectionInspector)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_EN_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static ApiInterface provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Singleton
    @Provides
    static ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    AppSettings provideAppSettings(Context context){
        return  new SharedPreferenceAppSettings(PreferenceManager.getDefaultSharedPreferences(context));
    }
}