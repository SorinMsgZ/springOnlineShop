package ro.msg.learning.shop.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StockId implements Serializable {
    @Column(name="product")
    private Product product;
    @Column(name="location")
    private Location location;

    public StockId() {

    }
    public StockId(Product product, Location location) {
        this.product = product;
        this.location = location;
    }

    public Product getProduct() {
        return product;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
