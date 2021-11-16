package ro.msg.learning.shop.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {
   @Column(name="orders")
    private int orderId;
   @Column(name="product")
    private int productId;
    public OrderDetailId() {

    }

    public int getOrderId() {
        return orderId;
    }

    public OrderDetailId(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
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
