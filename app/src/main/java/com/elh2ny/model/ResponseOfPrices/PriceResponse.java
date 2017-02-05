
package com.elh2ny.model.ResponseOfPrices;

import com.elh2ny.model.SpinnerModel;

import java.util.List;

public class PriceResponse {

    private String error;
    private List<SpinnerModel> prices = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SpinnerModel> getPrices() {
        return prices;
    }

    public void setPrices(List<SpinnerModel> prices) {
        this.prices = prices;
    }

}
