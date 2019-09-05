
package com.tohami.egyptinnovate.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNewsResponse {

    @SerializedName("News")
    @Expose
    private List<NewsItem> newsItems = null;

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

}
