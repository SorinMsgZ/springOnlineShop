package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="OrderDetail")
@Table(name="orderDetail")
public class OrderDetail {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private OrderDetailId idOrderProduct;
    /*@ManyToOne
    @JoinColumn(name = "orders")
    private Order orders;
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;*/
    private int quantity;

    public OrderDetailId getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(OrderDetailId idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
