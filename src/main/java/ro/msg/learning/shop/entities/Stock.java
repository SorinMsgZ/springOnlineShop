package ro.msg.learning.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity(name = "Stock")
@Table(name = "stock")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Getter
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



