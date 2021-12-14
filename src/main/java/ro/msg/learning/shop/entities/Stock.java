package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Stock")
@Table(name = "stock")
public class Stock {
    @EmbeddedId
    private StockId idProductLocation;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product")
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("locationId")
    @JoinColumn(name = "location")
    private Location location;
    private int quantity;

}



