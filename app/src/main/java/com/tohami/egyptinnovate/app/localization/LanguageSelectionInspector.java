package com.tohami.egyptinnovate.app.localization;

import com.tohami.egyptinnovate.BuildConfig;
import com.tohami.egyptinnovate.app.settings.AppSettings;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class LanguageSelectionInspector implements Interceptor {
    private volatile AppSettings appSettings;

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    @NotNull
    @Override
    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        String oldUrl = request.url().toString();

        String newUrl ;
        if(appSettings.getCurrentLanguage() == Language.ARABIC) {
            newUrl = oldUrl.replace(BuildConfig.BASE_EN_URL, BuildConfig.BASE_AR_URL);
        }else {
            newUrl = oldUrl.replace(BuildConfig.BASE_AR_URL, BuildConfig.BASE_EN_URL);
        }

        request = request.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(request);
    }
}
