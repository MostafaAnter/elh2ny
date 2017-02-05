package com.elh2ny.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mostafa_anter on 12/31/16.
 */

public class Article {
    private String id;
    private String title;
    private String description;
    private String content;
    private String img;
    private String active;
    private String views;
    private String user_id;
    private String updated_at;

    public Article() {
    }

    public Article(String id, String title, String description, String content, String img, String active, String views, String user_id, String updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.img = img;
        this.active = active;
        this.views = views;
        this.user_id = user_id;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}