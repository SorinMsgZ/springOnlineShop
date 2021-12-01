package ro.msg.learning.shop.services;

import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;

public class OrderObject {

    Location loc;
    Product prod;
    int qty;

    public OrderObject(Location loc, Product prod, int qty) {
        this.loc = loc;
        this.prod = prod;
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderObject{" +
                "loc=" + loc +
                ", prodId=" + prod +
                ", qty=" + qty +
                '}';
    }
}
