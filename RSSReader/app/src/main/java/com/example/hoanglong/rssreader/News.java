package com.example.hoanglong.rssreader;

import android.os.Parcel;
import android.os.Parcelable;

public class News {
    public String newsname;
    public String title;
    public String link;
    public String image;
    public String description;
    public String pubDate;

    public News(String newsname, String title, String link, String image, String description, String pubDate) {
        this.newsname = newsname;
        this.title = title;
        this.link = link;
        this.image = image;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
