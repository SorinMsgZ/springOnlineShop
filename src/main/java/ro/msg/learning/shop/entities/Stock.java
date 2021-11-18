package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Stock")
@Table(name = "stock")
public class Stock {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private StockId idProductLocation;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product")
    private Product product;
    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "location")
    private Location location;
    private int quantity;

}



