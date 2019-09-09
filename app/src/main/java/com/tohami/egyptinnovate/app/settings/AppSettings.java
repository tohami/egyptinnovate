package com.tohami.egyptinnovate.app.settings;

import com.tohami.egyptinnovate.app.localization.Language;

public interface AppSettings {
    Language getCurrentLanguage();

    void SetCurrentLanguage(Language language);
}
