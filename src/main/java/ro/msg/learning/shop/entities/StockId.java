package ro.msg.learning.shop.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@Getter
public class StockId implements Serializable {
    @Column(name = "product")
    private int productId;
    @Column(name = "location")
    private int locationId;

    public StockId() {
    }

    public StockId(int productID, int locationId) {
        this.productId = productID;
        this.locationId = locationId;
    }

    public int getProductId() {
        return productId;
    }

    public int getLocationId() {
        return locationId;
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


