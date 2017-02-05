package com.elh2ny.model.roomsResponseModel;

/**
 * Created by mostafa_anter on 2/1/17.
 */

public class RoomModel {
    private String id;
    private String inc;
    private String hospital;
    private String price;
    private String type;

    public RoomModel() {
    }

    public RoomModel(String id, String inc, String hospital, String price, String type) {
        this.id = id;
        this.inc = inc;
        this.hospital = hospital;
        this.price = price;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInc() {
        return inc;
    }

    public void setInc(String inc) {
        this.inc = inc;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
