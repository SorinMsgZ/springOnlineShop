package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;


@Entity(name = "OrderDetail")
@Table(name = "Order_Detail")
public class OrderDetail {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private OrderDetailId idOrderProduct;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orders")
    private Order orders;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product")
    private Product product;

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
