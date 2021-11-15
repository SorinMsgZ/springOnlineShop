package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class OrderDetail {
    @ManyToOne
    @JoinColumn(name = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "id")
    private  Product product;
    private  int quantity;
}
