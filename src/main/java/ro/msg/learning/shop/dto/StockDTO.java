package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@Builder
public class StockDTO {
    private StockId idProductLocation;
    private Product product;
    private Location location;
    private int quantity;

    public Stock toEntity() {
        Stock result = new Stock();
        result.setIdProductLocation(idProductLocation);
        result.setProduct(product);
        result.setLocation(location);
        result.setQuantity(quantity);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Stock stock) {
        stock.setIdProductLocation(idProductLocation);
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(quantity);
    }

    public static StockDTO of(Stock entity) {
        return StockDTO.builder()
                .idProductLocation(entity.getIdProductLocation())
                .product(entity.getProduct())
                .location(entity.getLocation())
                .quantity(entity.getQuantity())
                .build();
    }
}


