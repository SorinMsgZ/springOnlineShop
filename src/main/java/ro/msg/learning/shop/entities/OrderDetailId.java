package ro.msg.learning.shop.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Data
@Embeddable
public class OrderDetailId implements Serializable {
    @Column(name = "orders")
    private int orderId;
    @Column(name = "product")
    private int productId;
}
