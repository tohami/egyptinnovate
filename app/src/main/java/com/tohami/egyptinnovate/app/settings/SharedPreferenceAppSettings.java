package com.tohami.egyptinnovate.app.settings;

import android.content.SharedPreferences;

import com.tohami.egyptinnovate.app.localization.Language;

import org.jetbrains.annotations.NotNull;

import static com.tohami.egyptinnovate.utilities.Constants.LocaleCode.DEFAULT_LANGUAGE;
import static com.tohami.egyptinnovate.utilities.Constants.Prefs.LANGUAGE_KEY;

public class SharedPreferenceAppSettings implements AppSettings {

    private final SharedPreferences sharedPreferences;
    private Language currentLanguageCache = null;


    public SharedPreferenceAppSettings(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    @NotNull
    @Override
    public Language getCurrentLanguage() {

        if (currentLanguageCache == null) {
            String storedValue = sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);

            Language storedLanguage = Language.fromLocaleCode(storedValue);

            currentLanguageCache = storedLanguage;
            return storedLanguage;
        } else {
            return currentLanguageCache;
        }
    }

    @Override
    public void SetCurrentLanguage(Language language) {
        currentLanguageCache = language;
        sharedPreferences.edit().putString(LANGUAGE_KEY, language.getLanguageCode()).apply();
    }
}
