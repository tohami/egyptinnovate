package com.tohami.egyptinnovate.utilities

class Constants {
    object Prefs {
        val PREF_FILE_NAME = "news_prefs"
        val LANGUAGE_KEY = "language_key"
    }

    enum class Status {
        SUCCESS, FAIL, LOADING, NO_NETWORK
    }

    object News {
        val ARGS_NEWS_ID = "news_id"
        val TYPE_ARTICLE = "84"
        val TYPE_VIDEO = "85"


    }

    object LocaleCode {
        val ENGLISH = "en"
        val ARABIC = "ar"
        val DEFAULT_LANGUAGE = ENGLISH
    }

    object Flutter {
        val ENGINE_ID = "flutter_engine_id"
        val PRAYER_WIDGET = "/prayer"
        val NO_NETWORK_WIDGET = "/game"
    }



}
