
package com.elh2ny.model.typesResponseModel;

import com.elh2ny.model.SpinnerModel;

import java.util.List;

public class TypesResponseModel {

    private String error;
    private List<SpinnerModel> types = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SpinnerModel> getTypes() {
        return types;
    }

    public void setTypes(List<SpinnerModel> types) {
        this.types = types;
    }

}
