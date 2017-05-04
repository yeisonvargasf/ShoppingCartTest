package co.megaterios.shoppingcarttest.models;

import android.util.Log;

import com.orm.SugarRecord;

import co.megaterios.shoppingcarttest.ui.activity.ListProductsActivity;

/**
 * Created by yeison on 04/05/17.
 */

public class CartProduct extends SugarRecord {

    private Cart cart;
    private Product myProduct;
    private int quantityCurrentOrder;

    public CartProduct() {
    }

    public CartProduct(Product myProduct, int quantityCurrentOrder) {
        this.myProduct = myProduct;
        this.quantityCurrentOrder =quantityCurrentOrder;
        this.cart = Cart.findById(Cart.class, ListProductsActivity.ORDER_ID);
    }

    public Product getMyProduct() {
        return myProduct;
    }

    public void setMyProduct(Product myProduct) {
        this.myProduct = myProduct;
    }

    public int getQuantityCurrentOrder() {
        return quantityCurrentOrder;
    }

    public void setQuantityCurrentOrder(int quantityCurrentOrder) {
        this.quantityCurrentOrder = quantityCurrentOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartProduct)) return false;

        CartProduct cartProduct = (CartProduct) o;

        return getId().equals(cartProduct.getId());

    }

}
