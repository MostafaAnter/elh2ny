
package com.elh2ny.model.cityResponce;

import com.elh2ny.model.SpinnerModel;

import java.util.List;

public class CityResponseModel {

    private String error;
    private List<SpinnerModel> cities = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SpinnerModel> getCities() {
        return cities;
    }

    public void setCities(List<SpinnerModel> cities) {
        this.cities = cities;
    }

}
