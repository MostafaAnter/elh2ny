package com.elh2ny.model;

/**
 * Created by mostafa_anter on 2/1/17.
 */

public class RoomModel {
    private String avatarURL;
    private String roomName;
    private String breathOption;
    private String price;

    public RoomModel(String avatarURL, String roomName, String breathOption, String price) {
        this.avatarURL = avatarURL;
        this.roomName = roomName;
        this.breathOption = breathOption;
        this.price = price;
    }

    public RoomModel() {
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBreathOption() {
        return breathOption;
    }

    public void setBreathOption(String breathOption) {
        this.breathOption = breathOption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
