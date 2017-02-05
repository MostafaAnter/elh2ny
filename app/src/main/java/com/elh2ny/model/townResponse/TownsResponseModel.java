
package com.elh2ny.model.townResponse;

import com.elh2ny.model.SpinnerModel;

import java.util.List;

public class TownsResponseModel {

    private String error;
    private List<SpinnerModel> towns = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SpinnerModel> getTowns() {
        return towns;
    }

    public void setTowns(List<SpinnerModel> towns) {
        this.towns = towns;
    }

}
