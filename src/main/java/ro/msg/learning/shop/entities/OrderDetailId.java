package ro.msg.learning.shop.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class OrderDetailId implements Serializable {
   @Column(name="orders")
    private Order orders;
   @Column(name="product")
    private Product product;
    public OrderDetailId() {

    }
    public OrderDetailId(Order orders, Product product) {
        this.orders = orders;
        this.product = product;
    }

    public Order getOrders() {
        return orders;
    }

    public Product getProduct() {
        return product;
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
