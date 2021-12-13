package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@JsonPropertyOrder({"product", "location", "quantity"})
@Getter
public class StockDTO implements Serializable {
    private Product product;
    private Location location;
    private int quantity;

    public StockDTO(Product product, Location location, int quantity) {

        this.product = product;
        this.location = location;
        this.quantity = quantity;
    }

    public Stock toEntity() {
        Stock result = new Stock();
        StockId stockId = new StockId();
        stockId.setProductId(product.getId());
        stockId.setLocationId(location.getId());
        result.setIdProductLocation(stockId);

        result.setProduct(product);
        result.setLocation(location);
        result.setQuantity(quantity);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Stock stock) {

        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(quantity);
    }

    public static StockDTO of(Stock entity) {
        return StockDTO.builder()

                .product(entity.getProduct())
                .location(entity.getLocation())
                .quantity(entity.getQuantity())
                .build();
    }
}


