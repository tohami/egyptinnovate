package com.tohami.egyptinnovate.app.localization;

import com.tohami.egyptinnovate.utilities.Constants;

import org.jetbrains.annotations.NotNull;

public enum Language {
    ENGLLSH(Constants.LocaleCode.ENGLISH), ARABIC(Constants.LocaleCode.ARABIC) , DEFAULT(Constants.LocaleCode.DEFAULT_LANGUAGE);

    String languageCode ;

    Language(String code) {
        this.languageCode = code ;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    @NotNull
    public static Language fromLocaleCode(String value) {
        if (value == null) {
            return DEFAULT;
        }
        switch (value) {
            case Constants.LocaleCode.ARABIC:
                return ARABIC;

            case Constants.LocaleCode.ENGLISH:
                return ENGLLSH;

            default:
                return DEFAULT ;
        }

    }
}
