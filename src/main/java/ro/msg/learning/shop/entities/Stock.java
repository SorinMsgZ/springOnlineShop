package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Stock {
    @ManyToOne
    @JoinColumn(name = "id")
    private  Product product;
    @ManyToOne
    @JoinColumn(name = "id")
    private  Location location;
    private  int quantity;
}
