
package com.tohami.egyptinnovate.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNewsDetailsResponse {

    @SerializedName("newsItem")
    @Expose
    private NewsDetails newsDetails;

    public NewsDetails getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(NewsDetails newsDetails) {
        this.newsDetails = newsDetails;
    }

}
