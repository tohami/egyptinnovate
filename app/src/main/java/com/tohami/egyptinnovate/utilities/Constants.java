package com.tohami.egyptinnovate.utilities;

public class Constants {
    public class Prefs {
        public static final String PREF_FILE_NAME = "news_prefs";
        public static final String LANGUAGE_KEY = "language_key";
    }

    public enum Status {
        SUCCESS, FAIL, LOADING, NO_NETWORK
    }

    public class News {
        public static final String ARGS_NEWS_ID = "news_id";
        public static final String TYPE_ARTICLE = "84";
        public static final String TYPE_VIDEO = "85";


    }

    public class LocaleCode {
        public static final String ENGLISH = "en";
        public static final String ARABIC = "ar";
        public static final String DEFAULT_LANGUAGE = ENGLISH;
    }

}