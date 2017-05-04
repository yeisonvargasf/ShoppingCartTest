package co.megaterios.shoppingcarttest.models;

import com.orm.SugarRecord;

/**
 * Created by yeison on 04/05/17.
 */

public class Customer extends SugarRecord {

    private String name;

    public Customer(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
