package com.elh2ny.model.roomsResponseModel;

import java.util.List;

/**
 * Created by mostafa_anter on 2/5/17.
 */

public class RoomResponse {
    private String error;
    private List<RoomModel> incs = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RoomModel> getIncs() {
        return incs;
    }

    public void setIncs(List<RoomModel> incs) {
        this.incs = incs;
    }

}
