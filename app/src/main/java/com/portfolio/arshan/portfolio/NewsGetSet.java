package com.portfolio.arshan.portfolio;

import android.graphics.Bitmap;

/**
 * Created by Arshan on 10-Oct-2016.
 */

public class NewsGetSet {
    private String title, desc, newsUrl, publishedAt;
    private Bitmap bmp;

    public NewsGetSet() {
    }

    public NewsGetSet(String title, String desc, Bitmap bmp, String newsUrl, String publishedAt) {
        this.title = title;
        this.desc = desc;
        this.bmp = bmp;
        this.newsUrl = newsUrl;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
