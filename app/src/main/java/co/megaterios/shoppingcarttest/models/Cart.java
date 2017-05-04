package co.megaterios.shoppingcarttest.models;

import com.orm.SugarRecord;

import java.util.ArrayList;

import co.megaterios.shoppingcarttest.ui.activity.ListProductsActivity;

/**
 * Created by yeison on 04/05/17.
 */

public class Cart extends SugarRecord {

    private Customer customer;

    public Cart() {
    }

    public Cart(Long id, Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<CartProduct> getProducts() {
        return (ArrayList) CartProduct.find(CartProduct.class, "cart = ?",
                String.valueOf(ListProductsActivity.ORDER_ID));
    }

    @Override
    public String toString() {
        return "Cart{" +
                '}';
    }

}
