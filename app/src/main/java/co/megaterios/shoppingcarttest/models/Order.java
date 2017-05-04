package co.megaterios.shoppingcarttest.models;

import java.util.ArrayList;

/**
 * Created by yeison on 04/05/17.
 */

public class Order {
    
    private int id;
    private Customer customer;
    private ArrayList<OrderProduct> products;

    public Order() {
    }

    public Order(int id, Customer customer, ArrayList<OrderProduct> products) {
        this.id = id;
        this.customer = customer;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<OrderProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                '}';
    }

}
