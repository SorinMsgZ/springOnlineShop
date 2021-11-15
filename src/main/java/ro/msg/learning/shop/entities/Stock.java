package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="Stock")
@Table(name="stock")
public class Stock {
  @EmbeddedId
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private StockId idProductLocation;
/* @ManyToOne
    @JoinColumn(name = "product")
    private  Product product;
    @ManyToOne
    @JoinColumn(name = "location")
    private  Location location;*/

    private  int quantity;

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
