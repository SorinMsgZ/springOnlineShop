package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity(name = "OrderDetail")
@Table(name = "Order_Detail")
public class OrderDetail {
    @EmbeddedId

    private OrderDetailId idOrderProduct;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orders")
    private Order order;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product")
    private Product product;
    private int quantity;


}
