package ro.msg.learning.shop.entities;

import javax.persistence.*;


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

    public StockId getIdProductLocation() {
        return idProductLocation;
    }

    public void setIdProductLocation(StockId idProductLocation) {
        this.idProductLocation = idProductLocation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}



