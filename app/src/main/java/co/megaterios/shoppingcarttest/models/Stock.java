package co.megaterios.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by yeison on 04/05/17.
 */

public class Stock extends SugarRecord {

    private int quantity;

    public Stock() {
    }

    public Stock(int quantity) {
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;

        Stock stock = (Stock) o;

        return getId().equals(stock.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "product=" + getId() +
                ", quantity=" + quantity +
                '}';
    }
}
