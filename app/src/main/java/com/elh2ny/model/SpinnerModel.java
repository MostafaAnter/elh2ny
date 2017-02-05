package com.elh2ny.model;

/**
 * Created by mostafa_anter on 1/6/17.
 */

public class SpinnerModel {
    private String title;
    private String id;
    private String price;

    public SpinnerModel(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public SpinnerModel(String title, String id, String price) {
        this.title = title;
        this.id = id;
        this.price = price;
    }

    public SpinnerModel() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
