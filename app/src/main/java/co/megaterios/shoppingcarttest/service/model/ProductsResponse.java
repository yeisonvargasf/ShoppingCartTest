package co.megaterios.shoppingcarttest.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.megaterios.shoppingcarttest.models.Product;

/**
 * Created by yeison on 04/05/17.
 */

public class ProductsResponse {

    @SerializedName("results")
    private ArrayList<Product> mProducts;

    public ArrayList<Product> getProducts() {
        return mProducts;
    }
}
