package com.tohami.egyptinnovate.app.localization;

import android.content.Context;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by Sherif.ElNady on 10/19/2016.
 */
public class LocalizationHelper {

    public static void changeAppLanguage(String languageToLoad, Context ctx) {

        try {
            if (languageToLoad != null && !"".equals(languageToLoad)) {
                Resources res = ctx.getApplicationContext().getResources();
                android.content.res.Configuration config = res.getConfiguration();

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config.setLocale(locale);
                ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
